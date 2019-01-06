package com.example.oanac.recipes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class RecipesListActivity extends AppCompatActivity {
    private ProgressBar dataLoadingProgress;
    private RecyclerView rvRecipies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);
        dataLoadingProgress = (ProgressBar)findViewById(R.id.pbLoading);
        rvRecipies = (RecyclerView)findViewById(R.id.rvRecipes);
        LinearLayoutManager recipesLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
    rvRecipies.setLayoutManager(recipesLayoutManager);
        try {
            URL bookUrl = ApiUtil.buildApiUrl("cooking");
            new QueryTask().execute(bookUrl);
        }
        catch (Exception e) {
            Log.d("Error", e.getMessage());
        }


    }

    public class QueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String result = null;
            try {
                result = ApiUtil.getJson(searchUrl);
            }
            catch (Exception e) {
                Log.e("Error", e.getMessage());
            }

            return result;
        }

        protected void onPostExecute(String result) {
            TextView tvError = (TextView) findViewById(R.id.errorTextView);
            dataLoadingProgress.setVisibility(View.INVISIBLE);
            if (result == null) {
                rvRecipies.setVisibility(View.INVISIBLE);
                tvError.setVisibility(View.VISIBLE);
            } else {
                tvError.setVisibility(View.INVISIBLE);
                rvRecipies.setVisibility(View.VISIBLE);
            }

            ArrayList<Recipe> recipes = ApiUtil.getRecipesFromJson(result);
            String resultString = "";

            RecipeAdapter adapter = new RecipeAdapter(recipes);
            rvRecipies.setAdapter(adapter);
        }

        protected void onPreExecute() {
            super.onPreExecute();
            dataLoadingProgress.setVisibility(View.VISIBLE);
        }
    }
}


