package com.example.oanac.recipes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

//import android.widget.SearchView;

//import android.support.v4.view.MenuItemCompat;
//import android.widget.SearchView;

public class RecipesListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipies_list_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.actionSearch);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            URL bookUrl = ApiUtil.buildApiUrl(query);
            new QueryTask().execute(bookUrl);
        }
        catch(Exception e) {
            Log.d("Error", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (query.equals("")) {
            query = "cooking";
        }

        try {
            URL bookUrl = ApiUtil.buildApiUrl(query);
            new QueryTask().execute(bookUrl);
        }
        catch(Exception e) {
            Log.d("Error", e.getMessage());
        }
        return false;
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

                ArrayList<Recipe> recipes = ApiUtil.getRecipesFromJson(result);
                RecipeAdapter adapter = new RecipeAdapter(recipes);
                rvRecipies.setAdapter(adapter);
            }
        }

        protected void onPreExecute() {
            super.onPreExecute();
            dataLoadingProgress.setVisibility(View.VISIBLE);
        }
    }
}


