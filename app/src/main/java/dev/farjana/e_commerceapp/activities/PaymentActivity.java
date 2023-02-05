package dev.farjana.e_commerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dev.farjana.e_commerceapp.databinding.ActivityPaymentBinding;
import dev.farjana.e_commerceapp.utils.Constants;

public class PaymentActivity extends AppCompatActivity {

    ActivityPaymentBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String orderCode = getIntent().getStringExtra("orderCode");


        binding.webview.setMixedContentAllowed(false);
        binding.webview.loadUrl(Constants.PAYMENT_URL + orderCode);
        getSupportActionBar().setTitle("Payment ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}