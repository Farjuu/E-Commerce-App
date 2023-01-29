package dev.farjana.e_commerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dev.farjana.e_commerceapp.databinding.ActivityShoppingCartBinding;

public class Shopping_Cart_Activity extends AppCompatActivity {

    ActivityShoppingCartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShoppingCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}