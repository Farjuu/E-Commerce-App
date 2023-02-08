package dev.farjana.e_commerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dev.farjana.e_commerceapp.adapters.CategoryAdapter;
import dev.farjana.e_commerceapp.adapters.ProductAdapter;
import dev.farjana.e_commerceapp.databinding.ActivityMainBinding;
import dev.farjana.e_commerceapp.models.Category;
import dev.farjana.e_commerceapp.models.Product;
import dev.farjana.e_commerceapp.utils.Constants;

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

        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("query",text.toString());
                startActivity(intent);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
        init_categories();
        init_products();
        init_slider();

    }

    private void getCategories(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_CATEGORIES_URL, response -> {

            try {
                JSONObject mainObj = new JSONObject(response);
                if(mainObj.getString("status").equals("success")){
                    JSONArray categoryArray = mainObj.getJSONArray("categories");
                    for(int i = 0; i< categoryArray.length(); i++){
                        JSONObject object = categoryArray.getJSONObject(i);
                        Category category = new Category(
                                object.getString("name"),
                                Constants.CATEGORIES_IMAGE_URL+object.getString("icon"),
                                object.getString("color"),
                                object.getString("brief"),
                                object.getInt("id")

                        );
                        categories.add(category);
                    }
                    categoryAdapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        requestQueue.add(request);
    }

    private void init_slider() {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET,Constants.GET_OFFERS_URL,response-> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.getString("status").equals("success")){
                    JSONArray offerArray = jsonObject.getJSONArray("news_infos");
                    for(int i = 0; i < offerArray.length(); i++){
                        JSONObject childObj = offerArray.getJSONObject(i);
                        binding.carousel.addData(
                                new CarouselItem(
                                        Constants.NEWS_IMAGE_URL+ childObj.getString("image"),
                                        childObj.getString("title")));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {});

        queue.add(request);

       /* binding.carousel.addData(new CarouselItem("https://img.freepik.com/free-photo/cropped-image-woman-inputting-card-information-key-phone-laptop-while-shopping-online_1423-68.jpg?w=740&t=st=1674913112~exp=1674913712~hmac=1004a08691d5752e828c29784ae6dade925b326e31b0963694252b8b466b7316","Buy 1 Get 1 Free"));
        binding.carousel.addData(new CarouselItem("https://img.freepik.com/premium-photo/shopping-concept-empty-shopping-cart-laptop-rustic-wooden-background_532405-2683.jpg?w=740","Get 50% Discount"));
        binding.carousel.addData(new CarouselItem("https://img.freepik.com/free-photo/cropped-image-woman-inputting-card-information-key-phone-laptop-while-shopping-online_1423-68.jpg?w=740&t=st=1674913112~exp=1674913712~hmac=1004a08691d5752e828c29784ae6dade925b326e31b0963694252b8b466b7316","Buy 1 Get 1 Free"));
        binding.carousel.addData(new CarouselItem("https://img.freepik.com/premium-photo/shopping-concept-empty-shopping-cart-laptop-rustic-wooden-background_532405-2683.jpg?w=740","Get 50% Discount"));
*/
    }

    private void init_categories() {
        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this,categories);

        getCategories();

        GridLayoutManager layoutManager = new GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL, false);

        binding.categoriesList.setLayoutManager(layoutManager);

        binding.categoriesList.setAdapter(categoryAdapter);
    }

    private void init_products() {

        products = new ArrayList<>();

        productAdapter = new ProductAdapter(this,products);
        getProducts();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        binding.productsList.setLayoutManager(gridLayoutManager);
        binding.productsList.setAdapter(productAdapter);

    }

    private void getProducts(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Constants.GET_PRODUCTS_URL+ "?count=4";
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {

            try {
                JSONObject object = new JSONObject(response);
                if(object.getString("status").equals("success")){
                    JSONArray array = object.getJSONArray("products");
                    for(int i = 0; i < array.length(); i++){
                        JSONObject child = array.getJSONObject(i);
                        Product product = new Product(
                                child.getString("name"),
                                Constants.PRODUCTS_IMAGE_URL+child.getString("image"),
                                child.getString("status"),
                                child.getDouble("price"),
                                child.getDouble("price_discount"),
                                child.getInt("stock"),
                                child.getInt("id")
                        );
                        products.add(product);
                    }
                    productAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {

        });
        requestQueue.add(request);
    }
}