package com.mglabs.whowroteit_mio;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    public static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    public static final String QUERY_STRING = "q";
    public static final String MAX_RESULTS = "maxResults";
    public static final String PRINT_TYPE = "printType";
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName() ;

    public static String getBookInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String result = "";

        try {
            Uri uri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_STRING, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();

            // Convert uri to URL object
            URL url = new URL(uri.toString());

            // Open connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Manage the response. Get the InputStreamer
            InputStream inputStream = urlConnection.getInputStream();
            //Create a BufferReader from the inputStream
            reader = new BufferedReader(new InputStreamReader(inputStream));
            // Create a StringBuilder to hold the incoming response
            StringBuilder builder = new StringBuilder();

            // Read the input line by line while there is still input
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");  // for debugging purpose
            }
            //If stream is empty no point in parsing
            if (builder.length() == 0) {
                return null;
            }

            result = builder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG, result);
        return result;
    }
}
