package dev.farjana.e_commerceapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;

import dev.farjana.e_commerceapp.R;
import dev.farjana.e_commerceapp.databinding.CartItemsBinding;
import dev.farjana.e_commerceapp.databinding.QuantityDialogBinding;
import dev.farjana.e_commerceapp.models.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    ArrayList<Product> products;
    CartListener cartListener;
    Cart cart;

    public interface CartListener{
        public void onQuantityChanged();
    }

    public CartAdapter(Context context, ArrayList<Product> products, CartListener cartListener) {
        this.context = context;
        this.products = products;
        this.cartListener = cartListener;
        cart = TinyCartHelper.getCart();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        Product product = products.get(position);

        Glide.with(context)
                .load(product.getProduct_img())
                .into(holder.binding.prductImg);
        holder.binding.name.setText(product.getProduct_name());
        holder.binding.prdQntity.setText(String.valueOf(product.getQuantity()));
        holder.binding.price.setText("TK "+ product.getProduct_price());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuantityDialogBinding quantityDialogBinding = QuantityDialogBinding.inflate(LayoutInflater.from(context));

                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setView(quantityDialogBinding.getRoot())
                        .create();
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
               quantityDialogBinding.productItemName.setText(product.getProduct_name());
               quantityDialogBinding.stock.setText("Stock: " +product.getStock());

               quantityDialogBinding.quantity.setText(""+product.getQuantity());
               int stock = product.getStock();
                quantityDialogBinding.plusBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       int quantity = product.getQuantity();
                       quantity++;
                       if(quantity > product.getStock()){
                           Toast.makeText(context,
                                   "Max stock available is : "+product.getStock()
                           ,Toast.LENGTH_SHORT).show();
                           return;
                       }else{
                           product.setQuantity(quantity);
                           quantityDialogBinding.quantity.setText(""+product.getQuantity());

                       }

                       notifyDataSetChanged();

                       cart.updateItem(product,product.getQuantity());
                       cartListener.onQuantityChanged();
                   }
               });

                quantityDialogBinding.minusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int quantity = product.getQuantity();
                        if(quantity > 1){
                            quantity--;
                        }
                        product.setQuantity(quantity);
                        quantityDialogBinding.quantity.setText(""+product.getQuantity());

                        notifyDataSetChanged();

                        cart.updateItem(product,product.getQuantity());
                        cartListener.onQuantityChanged();
                    }
                });

                quantityDialogBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });


                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        CartItemsBinding binding;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CartItemsBinding.bind(itemView);
        }
    }
}
