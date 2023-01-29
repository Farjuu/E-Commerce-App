package dev.farjana.e_commerceapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dev.farjana.e_commerceapp.R;
import dev.farjana.e_commerceapp.activities.ProductDetailsActivity;
import dev.farjana.e_commerceapp.databinding.ItemProductsBinding;
import dev.farjana.e_commerceapp.models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    ArrayList<Product> products;

    public ProductAdapter(Context context,ArrayList<Product> products){
        this.context = context;
        this.products = products;

    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.item_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        Glide.with(context)
                .load(product.getProduct_img())
                .into(holder.binding.productImage);
        holder.binding.productName.setText(product.getProduct_name());
        holder.binding.productPrice.setText("TK "+product.getProduct_price());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("name",product.getProduct_name());
            intent.putExtra("price",product.getProduct_price());
            intent.putExtra("image",product.getProduct_img());
            intent.putExtra("id",product.getId());

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        ItemProductsBinding binding;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemProductsBinding.bind(itemView);
        }
    }

}
