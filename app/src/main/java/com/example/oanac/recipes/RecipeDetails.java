package com.example.oanac.recipes;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.oanac.recipes.databinding.ActivityRecipeDetailsBinding;

public class RecipeDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Recipe recipe = getIntent().getParcelableExtra("Recipe");
        ActivityRecipeDetailsBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_recipe_details);
        binding.setRecipe(recipe);
    }
}
