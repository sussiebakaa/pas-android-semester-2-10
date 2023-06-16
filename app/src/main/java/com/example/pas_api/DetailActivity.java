package com.example.pas_api ;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pas_api.databinding.ActivityDetailBinding;
import com.example.pas_api.response.FoodDetail;
import com.example.pas_api.response.ResponseFoodDetail;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        String foodId = intent.getStringExtra("food_id"); //menerima data dari movie adapter

        getDetailMovie(foodId);
    }

    private void getDetailMovie(String foodId){
        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        //TO DO
        ApiService service = ApiClient.getRetrofitInstance().create(ApiService.class);

        Call<ResponseFoodDetail> call = service.getResepFood(foodId);
        call.enqueue(new Callback<ResponseFoodDetail>() {
            @Override
            public void onResponse(Call<ResponseFoodDetail> call, Response<ResponseFoodDetail> response) {
                ResponseFoodDetail responseFood = response.body();
                FoodDetail foodDetail = responseFood.getMeals().get(0);
                progressDialog.dismiss();
                Log.e("DIDIII", foodDetail.getIdMeal());
                setDataUI(foodDetail);
            }

            @Override
            public void onFailure (Call<ResponseFoodDetail> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setDataUI(FoodDetail food) {

        binding.nameFood.setText(food.getStrMeal());

        binding.resepFood1.setText(food.getStrIngredient1());
        binding.resepFood2.setText(food.getStrIngredient2());
        binding.resepFood3.setText(food.getStrIngredient3());
        binding.resepFood4.setText(food.getStrIngredient4());
        binding.resepFood5.setText(food.getStrIngredient5());
        binding.resepFood6.setText(food.getStrIngredient6());
        binding.resepFood7.setText(food.getStrIngredient7());
        String urlPoster = food.getStrMealThumb();

        Picasso.get()
                .load(urlPoster)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder_error)
                .into(binding.imageFood);
    }

            }

