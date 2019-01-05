package com.example.oanac.recipes;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.net.URL;

public class RecipesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);
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
            TextView tvResult = (TextView) findViewById(R.id.mainTextView);
            tvResult.setText(result);
        }
    }
}


