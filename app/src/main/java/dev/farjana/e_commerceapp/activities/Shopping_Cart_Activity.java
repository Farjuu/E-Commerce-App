package dev.farjana.e_commerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;
import java.util.Map;

import dev.farjana.e_commerceapp.adapters.CartAdapter;
import dev.farjana.e_commerceapp.databinding.ActivityShoppingCartBinding;
import dev.farjana.e_commerceapp.models.Product;

public class Shopping_Cart_Activity extends AppCompatActivity {

    ActivityShoppingCartBinding binding;
    CartAdapter adapter;
    ArrayList<Product> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShoppingCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        products = new ArrayList<>();
        Cart cart = TinyCartHelper.getCart();

        for(Map.Entry<Item,Integer>item : cart.getAllItemsWithQty().entrySet()){

            Product product = (Product) item.getKey();
            int quantity = item.getValue();
            product.setQuantity(quantity);

            products.add(product);
        }


        adapter = new CartAdapter(this, products, new CartAdapter.CartListener() {
            @Override
            public void onQuantityChanged() {
                binding.subtotal.setText(String.format("TK %.2f",cart.getTotalPrice()));
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.cartlist.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        binding.cartlist.addItemDecoration(itemDecoration);
        binding.cartlist.setAdapter(adapter);

        binding.subtotal.setText(String.format("TK %.2f",cart.getTotalPrice()));
        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Shopping_Cart_Activity.this,CheckOutActivity.class));
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}