package com.example.pas_api;

import static com.example.pas_api.ApiClient.getRetrofitInstance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.pas_api.databinding.ActivityMainBinding;
import com.example.pas_api.login.LoginActivity;
import com.example.pas_api.login.Preference;
import com.example.pas_api.response.FoodDetail;
import com.example.pas_api.response.MealsItem;
import com.example.pas_api.response.ResponseFoodDetail;
import com.example.pas_api.response.ResponseMealsItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ProgressDialog progressDialog;
    private SearchView searchView;
    private Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        preference = new Preference(this);

        binding.ivProfile.setOnClickListener( v -> {
            preference.setSessionLogin(false);
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
        Toast.makeText(this, "Kekuatan pertemanan", Toast.LENGTH_SHORT).show();

        binding.rvEureka.setLayoutManager(new GridLayoutManager(this,2));
        getListFood();
    }

    private void getListFood(){
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        ApiService service = getRetrofitInstance().create(ApiService.class);
        Call<ResponseMealsItem> call = service.getListFood();

        call.enqueue(new Callback<ResponseMealsItem>() {
            @Override
            public void onResponse(Call<ResponseMealsItem> call, Response<ResponseMealsItem> response) {
                progressDialog.dismiss();
                List<MealsItem> moviesAifan = response.body().getMeals();
                FoodAdapter adapter = new FoodAdapter(moviesAifan);
                binding.rvEureka.setAdapter(adapter);
            }



            @Override
            public void onFailure(Call<ResponseMealsItem> call, Throwable t) {
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getSearchFood(){
        searchView = new SearchView(MainActivity.this);

        getRetrofitInstance().create(ApiService.class);
}}