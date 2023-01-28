package dev.farjana.e_commerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;

import dev.farjana.e_commerceapp.adapters.CategoryAdapter;
import dev.farjana.e_commerceapp.adapters.ProductAdapter;
import dev.farjana.e_commerceapp.databinding.ActivityMainBinding;
import dev.farjana.e_commerceapp.models.Category;
import dev.farjana.e_commerceapp.models.Product;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;

    ProductAdapter productAdapter;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init_categories();
        init_products();
        init_slider();

    }

    private void init_slider() {
        binding.carousel.addData(new CarouselItem("https://img.freepik.com/free-photo/cropped-image-woman-inputting-card-information-key-phone-laptop-while-shopping-online_1423-68.jpg?w=740&t=st=1674913112~exp=1674913712~hmac=1004a08691d5752e828c29784ae6dade925b326e31b0963694252b8b466b7316","Buy 1 Get 1 Free"));
        binding.carousel.addData(new CarouselItem("https://img.freepik.com/premium-photo/shopping-concept-empty-shopping-cart-laptop-rustic-wooden-background_532405-2683.jpg?w=740","Get 50% Discount"));
        binding.carousel.addData(new CarouselItem("https://img.freepik.com/free-photo/cropped-image-woman-inputting-card-information-key-phone-laptop-while-shopping-online_1423-68.jpg?w=740&t=st=1674913112~exp=1674913712~hmac=1004a08691d5752e828c29784ae6dade925b326e31b0963694252b8b466b7316","Buy 1 Get 1 Free"));
        binding.carousel.addData(new CarouselItem("https://img.freepik.com/premium-photo/shopping-concept-empty-shopping-cart-laptop-rustic-wooden-background_532405-2683.jpg?w=740","Get 50% Discount"));

    }

    private void init_categories() {
        categories = new ArrayList<>();
        categories.add(new Category("Soft drink","https://th.bing.com/th/id/OIP.uf7t4hQV2kLHsSrB0QjaBQAAAA?w=131&h=196&c=7&r=0&o=5&dpr=1.5&pid=1.7", "#ffaf49","Lemonade",1));
        categories.add(new Category("Soft drink","https://th.bing.com/th/id/OIP.uf7t4hQV2kLHsSrB0QjaBQAAAA?w=131&h=196&c=7&r=0&o=5&dpr=1.5&pid=1.7", "#ffcf49","Lemonade",2));
        categories.add(new Category("Soft drink","https://th.bing.com/th/id/OIP.uf7t4hQV2kLHsSrB0QjaBQAAAA?w=131&h=196&c=7&r=0&o=5&dpr=1.5&pid=1.7", "#ffa649","Lemonade",3));
        categories.add(new Category("Soft drink","https://th.bing.com/th/id/OIP.uf7t4hQV2kLHsSrB0QjaBQAAAA?w=131&h=196&c=7&r=0&o=5&dpr=1.5&pid=1.7", "#ffaa49","Lemonade",4));
        categories.add(new Category("Soft drink","https://th.bing.com/th/id/OIP.uf7t4hQV2kLHsSrB0QjaBQAAAA?w=131&h=196&c=7&r=0&o=5&dpr=1.5&pid=1.7", "#ffaf58","Lemonade",5));
        categoryAdapter = new CategoryAdapter(this,categories);
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        binding.categoriesList.setLayoutManager(layoutManager);

        binding.categoriesList.setAdapter(categoryAdapter);
    }

    private void init_products() {

        products = new ArrayList<>();
        products.add(new Product("T-shirt","https://th.bing.com/th/id/R.2dff5b3a9a41e416f60e2013ff657942?rik=2V8xzX8QffKhuA&pid=ImgRaw&r=0","In stock",250.00,25.0,12,1));
        products.add(new Product("Jeans","https://th.bing.com/th/id/R.c6ec3a48247dd6f10b5202b6e0abb7a7?rik=h8pBfR2jOFGUXw&pid=ImgRaw&r=0","In stock",230.00,20.0,12,2));
        products.add(new Product("Shoes","https://th.bing.com/th/id/R.d087bbfc94327056a99ebd247129d7b6?rik=ytl9j5pdCx4oyA&pid=ImgRaw&r=0","In stock",220.00,15.0,12,3));
        products.add(new Product("Jewelry","https://th.bing.com/th/id/R.4a404e1920a85969037f7410368c92a3?rik=ymmHMqHAXUUaaw&pid=ImgRaw&r=0","In stock",210.00,50.0,12,4));

        products.add(new Product("T-shirt","https://th.bing.com/th/id/R.2dff5b3a9a41e416f60e2013ff657942?rik=2V8xzX8QffKhuA&pid=ImgRaw&r=0","In stock",250.00,25.0,12,1));
        products.add(new Product("Jeans","https://th.bing.com/th/id/R.c6ec3a48247dd6f10b5202b6e0abb7a7?rik=h8pBfR2jOFGUXw&pid=ImgRaw&r=0","In stock",230.00,20.0,12,2));
        products.add(new Product("Shoes","https://th.bing.com/th/id/R.d087bbfc94327056a99ebd247129d7b6?rik=ytl9j5pdCx4oyA&pid=ImgRaw&r=0","In stock",220.00,15.0,12,3));
        products.add(new Product("Jewelry","https://th.bing.com/th/id/R.4a404e1920a85969037f7410368c92a3?rik=ymmHMqHAXUUaaw&pid=ImgRaw&r=0","In stock",210.00,50.0,12,4));

        productAdapter = new ProductAdapter(this,products);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        binding.productsList.setLayoutManager(gridLayoutManager);
        binding.productsList.setAdapter(productAdapter);

    }
}