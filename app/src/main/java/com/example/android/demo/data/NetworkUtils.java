package com.example.android.demo.data;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    public static void loadData(){
        Log.d("test#", "NetworkUtils::loadData()");
        AsyncTask<Void, Void, String> loadDataTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                Log.d("test#", "NetworkUtils::AsyncTask::doInBackground()");
                return makeHttpRequest("https://corona-api.com/countries/EG");
            }
        };
        loadDataTask.execute();
    }

    private static String makeHttpRequest(String url){
        Log.d("test#", "NetworkUtils::makeHttpRequest()");
        String jsonResponse = null;
        if(url == null || url.equals(""))
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            URL urlObject = new URL(url);
            urlConnection = (HttpURLConnection) urlObject.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();
            Log.d("test#", urlConnection.getResponseCode() + "");
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = jsonFromInputStream(inputStream);
                Log.d("response data: ", jsonResponse);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    private static String jsonFromInputStream(InputStream inputStream) {
        Log.d("test#", "NetworkUtils::jsonFromInputStream()");
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            line = bufferedReader.readLine();
            while(line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
