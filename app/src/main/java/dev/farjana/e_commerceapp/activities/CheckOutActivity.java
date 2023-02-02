package dev.farjana.e_commerceapp.activities;

import static dev.farjana.e_commerceapp.utils.Constants.POST_ORDER_URL;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import dev.farjana.e_commerceapp.adapters.CartAdapter;
import dev.farjana.e_commerceapp.databinding.ActivityCheckOutBinding;
import dev.farjana.e_commerceapp.models.Product;

public class CheckOutActivity extends AppCompatActivity {

    ActivityCheckOutBinding binding;
    CartAdapter adapter;
    ArrayList<Product> products;

    double totalPrice = 0;
    final int tax = 11;

    Cart cart;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Processing");
        products = new ArrayList<>();
        cart = TinyCartHelper.getCart();

        for(Map.Entry<Item,Integer>item : cart.getAllItemsWithQty().entrySet()){

            Product product = (Product) item.getKey();
            int quantity = item.getValue();
            product.setQuantity(quantity);

            products.add(product);
        }


        adapter = new CartAdapter(this, products, new CartAdapter.CartListener() {
            @Override
            public void onQuantityChanged() {
                binding.checkoutSubtotal.setText(String.format("TK %.2f",cart.getTotalPrice()));
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.cartlist.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        binding.cartlist.addItemDecoration(itemDecoration);
        binding.cartlist.setAdapter(adapter);

        binding.checkoutSubtotal.setText(String.format("TK %.2f",cart.getTotalPrice()));

        totalPrice = (cart.getTotalPrice().doubleValue()* tax/100) + cart.getTotalPrice().doubleValue();
        binding.totalCost.setText("TK "+totalPrice);

        binding.checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                processPayment();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void processPayment(){
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject productOrder = new JSONObject();

        JSONObject dataobject = new JSONObject();

        try {
            productOrder.put("address",binding.address.getText().toString());
            productOrder.put("buyer",binding.fullname.getText().toString());
            productOrder.put("comment",binding.comment.getText().toString());
            productOrder.put("created_at", Calendar.getInstance().getTimeInMillis());
            productOrder.put("date_ship",Calendar.getInstance().getTimeInMillis());
            productOrder.put("email",binding.email.getText().toString());
            productOrder.put("phone",binding.phoneNum.getText().toString());
            productOrder.put("serial",binding.fullname.getText().toString());
            productOrder.put("shipping","");
            productOrder.put("shipping_location","");
            productOrder.put("shipping_rate","0.0");
            productOrder.put("status","WAITING");
            productOrder.put("tax",tax);
            productOrder.put("total_fees",totalPrice);


            JSONArray product_detail_array = new JSONArray();
            for(Map.Entry<Item,Integer>item : cart.getAllItemsWithQty().entrySet()){

                Product product = (Product) item.getKey();
                int quantity = item.getValue();
                product.setQuantity(quantity);

                JSONObject productObj = new JSONObject();
                productObj.put("amount",quantity);
                productObj.put("price_item",product.getProduct_price());
                productObj.put("product_id",product.getId());
                productObj.put("product_name",product.getProduct_name());

                product_detail_array.put(productObj);
            }

            dataobject.put("product_order", productOrder);
            dataobject.put("product_order_detail", product_detail_array);



        }catch(JSONException e){

        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, POST_ORDER_URL, dataobject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("success")){
                        Toast.makeText(CheckOutActivity.this,"Success order", Toast.LENGTH_SHORT).show();
                        String orderNumber = response.getJSONObject("data").getString("code");
                        new AlertDialog.Builder(CheckOutActivity.this)
                                .setTitle("Order Successful")
                                .setMessage("Order number "+ orderNumber)
                                .setCancelable(false)
                                .setPositiveButton("Pay Now", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(CheckOutActivity.this,PaymentActivity.class);

                                        intent.putExtra("orderCode",orderNumber);
                                        startActivity(intent);
                                    }
                                }).show();
                    }else{
                        Toast.makeText(CheckOutActivity.this,"Failed order", Toast.LENGTH_SHORT).show();

                        new AlertDialog.Builder(CheckOutActivity.this)
                                .setTitle("Order failed")
                                .setMessage("Something wrong ")
                                .setCancelable(false)
                                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Security","secure_code");
                return headers;
            }
        };
        requestQueue.add(request);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}