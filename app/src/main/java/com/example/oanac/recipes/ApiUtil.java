package com.example.oanac.recipes;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtil {
    private ApiUtil(){}

    public static final String API_URL =
            "https://www.googleapis.com/books/v1/volumes/";
    public static final String QUERY_PARAMETER =
            "q";
    public static final String KEY = "key";
    public static final String API_KEY = "AIzaSyALb868_0nisPN3F8RFi669zGbT6X58clc";


    public static URL buildApiUrl(String title) {
        URL url = null;
        Uri uri = Uri.parse(API_URL)
                .buildUpon()
                .appendQueryParameter(QUERY_PARAMETER, title)
                .build();

        try {
            url = new URL(uri.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getJson(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");

            boolean hasData = scanner.hasNext();
            if (hasData) {
                return scanner.next();
            } else {
                return null;
            }
        }
        catch (Exception e) {
            Log.d("Error",
                    e.toString());
            return null;
        }
        finally {
            connection.disconnect();
        }

    }

    public static ArrayList<Recipe> getRecipesFromJson(String json) {
        final String ID = "id";
        final String TITLE = "title";
        final String SUB_TITLE = "subtitle";
        final String AUTHORS = "authors";
        final String PUBLISHER = "publisher";
        final String PUBLISH_DATE = "publishedDate";
        final String ITEMS = "items";
        final String VOLUME_INFO = "volumeInfo";
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        try {
            JSONObject jsonRecipe = new JSONObject((json));
            JSONArray arrayRecipes = jsonRecipe.getJSONArray(ITEMS);
            int numberOfRecipies = arrayRecipes.length();
            for (int i = 0; i < numberOfRecipies; i++) {
                JSONObject recipeJson = arrayRecipes.getJSONObject(i);
                JSONObject volumeInfoJson = recipeJson.getJSONObject(VOLUME_INFO);
                int numberOfAuthors = volumeInfoJson.getJSONArray(AUTHORS).length();
                String[] authors = new String[numberOfAuthors];
                for (int j = 0; j < numberOfAuthors; j++) {
                    authors[j] = volumeInfoJson.getJSONArray(AUTHORS).get(j).toString();
                }

                Recipe recipe = new Recipe(
                        recipeJson.getString(ID),
                        volumeInfoJson.getString(TITLE),
                        (volumeInfoJson.isNull(SUB_TITLE) ? "" : volumeInfoJson.getString(SUB_TITLE)),
                        volumeInfoJson.getString(PUBLISHER),
                        volumeInfoJson.getString(PUBLISH_DATE),
                        authors);
                recipes.add(recipe);
            }
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

        return recipes;
    }
}
