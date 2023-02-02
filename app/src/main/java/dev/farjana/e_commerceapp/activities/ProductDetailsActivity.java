package dev.farjana.e_commerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dev.farjana.e_commerceapp.R;
import dev.farjana.e_commerceapp.databinding.ActivityProductDetailsBinding;
import dev.farjana.e_commerceapp.models.Product;
import dev.farjana.e_commerceapp.utils.Constants;

public class ProductDetailsActivity extends AppCompatActivity {

    ActivityProductDetailsBinding binding;
    Product crntProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String name = getIntent().getStringExtra("name");
        String image = getIntent().getStringExtra("image");
        int id = getIntent().getIntExtra("id",0);
        double price = getIntent().getDoubleExtra("price",0.0);

        Glide.with(this )
                .load(image)
                .into(binding.productImg);

        getProductDetails(id);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Cart cart = TinyCartHelper.getCart();

        binding.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cart.addItem(crntProduct,1);
                binding.addToCart.setEnabled(false);
                binding.addToCart.setText("Added to Cart");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.cart){
            startActivity(new Intent(this,Shopping_Cart_Activity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    void getProductDetails(int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Constants.GET_PRODUCT_DETAILS_URL+ id;
        StringRequest request = new StringRequest(Request.Method.GET,
                url, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject detailObject = null;
                if (jsonObject.getString("status").equals("success")) {
                    detailObject = jsonObject.getJSONObject("product");
                    String description = detailObject.getString("description");
                    binding.productDetails.setText(
                            Html.fromHtml(description)
                    );
                }
                crntProduct = new Product(
                        detailObject.getString("name"),
                        Constants.PRODUCTS_IMAGE_URL + detailObject.getString("image"),
                        detailObject.getString("status"),
                        detailObject.getDouble("price"),
                        detailObject.getDouble("price_discount"),
                        detailObject.getInt("stock"),
                        detailObject.getInt("id")
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

                });

        requestQueue.add(request);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}