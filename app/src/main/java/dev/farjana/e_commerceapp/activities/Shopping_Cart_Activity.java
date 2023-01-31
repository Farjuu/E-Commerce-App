package dev.farjana.e_commerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;

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


        products.add(new Product("ice","","status",330.0,30.0,20,3));
        products.add(new Product("ice","","status",330.0,30.0,20,4));
        products.add(new Product("ice","","status",330.0,30.0,20,5));
        adapter = new CartAdapter(this, products);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.cartlist.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        binding.cartlist.addItemDecoration(itemDecoration);
        binding.cartlist.setAdapter(adapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}