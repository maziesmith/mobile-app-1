package com.example.oanac.recipes;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiUtil {
    private ApiUtil(){}

    public static final String API_URL =
            "https://www.googleapis.com/books/v1/volumes/";

    public static URL buildApiUrl(String title) {
        String fullApiUrl = API_URL + "?q=" + title;
        URL url = null;
        try {
            url = new URL(fullApiUrl);
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

}
