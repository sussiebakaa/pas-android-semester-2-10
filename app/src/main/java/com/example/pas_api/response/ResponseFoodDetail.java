package com.example.pas_api.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseFoodDetail {

	@SerializedName("meals")
	private List<FoodDetail> meals;

	public List<FoodDetail> getMeals(){
		return meals;
	}
}
