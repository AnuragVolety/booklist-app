package com.example.vanur.booklistapp;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class  QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getName();

    private QueryUtils(){
    }

    private static List<Book> extractFeatureFromJson(String jsonResponse) {
        if(TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        List<Book> books = new ArrayList<>();

        try {
            JSONObject baseJSONResponse = new JSONObject(jsonResponse);
            JSONArray bookArray =baseJSONResponse.getJSONArray("items");
            Log.e(LOG_TAG,"Anurag value of bookArray is "+bookArray.length());
            for(int i=0;i<bookArray.length();i++){
                JSONObject currentBook = bookArray.getJSONObject(i);
                Log.e(LOG_TAG,"Anurag value of current book is "+ i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");
                String bookName = volumeInfo.getString("title");
                String subtitle;
                try{
                    subtitle = volumeInfo.getString("subtitle");
                }
                catch (JSONException e){
                    Log.e("QueryUtils", "Anurag Subtitle info absent", e);
                    subtitle="";
                }
                JSONArray authorsList = volumeInfo.getJSONArray("authors");
                String authors = "By ";
                for(int j=0; j<authorsList.length();j++){

                    authors += authorsList.getString(j);

                    if(authors.length()!=1){
                        if(j<authorsList.length()-2){
                            authors += ", ";
                        }
                        else if(j==authorsList.length()-2){
                            authors += " and ";
                        }
                    }
                }
                String publisher="";
                try {
                    publisher = volumeInfo.getString("publisher");
                }
                catch (JSONException e){
                    Log.e("QueryUtils", "Anurag Publisher info absent", e);
                }
                String link;
                Intent browserIntent = null;
                try{
                    link = volumeInfo.getString("canonicalVolumeLink");
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                }
                catch (JSONException e){
                    Log.e("QueryUtils", "Anurag link absent", e);
                }

                int pageCount;
                try{
                    pageCount = volumeInfo.getInt("pageCount");
                }
                catch (JSONException e){
                    Log.e("QueryUtils", "Anurag Page Count Absent",e);
                    pageCount=0;
                }

                Book book = new Book(bookName, subtitle, authors, publisher, Integer.toString(pageCount), browserIntent);

                books.add(book);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }

        return books;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    public static List<Book> fetchBookData(String mUrl) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        URL url = createUrl(mUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<Book> books = extractFeatureFromJson(jsonResponse);
        return books;
    }


}
