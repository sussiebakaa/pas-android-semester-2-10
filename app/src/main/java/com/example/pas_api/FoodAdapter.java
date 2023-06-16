package com.example.pas_api;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pas_api.response.FoodDetail;
import com.example.pas_api.response.MealsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private List<MealsItem> localDataSet;

    public FoodAdapter(List<MealsItem> dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvFoodName;
        private final ImageView imgFoodPoster;

        public ViewHolder(View view) {
            super(view);

            tvFoodName = view.findViewById(R.id.name_food);
            imgFoodPoster = view.findViewById(R.id.image_food);

        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        MealsItem foodDetail = localDataSet.get(position);
        String titleAifan = foodDetail.getStrMeal();

        String urlPoster = foodDetail.getStrMealThumb();

        viewHolder.tvFoodName.setText(titleAifan);

        Picasso.get()
                .load(urlPoster)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder_error)
                .into(viewHolder.imgFoodPoster);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("DIDIII", foodDetail.getIdMeal());
                Intent intent = new Intent(viewHolder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("food_id",String.valueOf(foodDetail.getIdMeal()));
                viewHolder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}




