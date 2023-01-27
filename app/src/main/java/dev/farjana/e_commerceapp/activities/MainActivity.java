package dev.farjana.e_commerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;

import dev.farjana.e_commerceapp.adapters.CategoryAdapter;
import dev.farjana.e_commerceapp.databinding.ActivityMainBinding;
import dev.farjana.e_commerceapp.models.Category;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
}