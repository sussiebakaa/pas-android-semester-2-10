package com.example.pas_api.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSearchFood{

	@SerializedName("meals")
	private List<MealsItem> meals;

	public List<MealsItem> getMeals(){
		return meals;
	}
}