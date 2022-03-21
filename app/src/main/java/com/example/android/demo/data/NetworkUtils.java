package com.example.android.demo.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String API_ENDPOINT = "https://corona-api.com/";
    private static final String PATH = "countries";
    private static final String COUNTRY_CODE = "EG";


    /**
     * This class should not be instantiated,
     * so provide a private constructor
     */
    private NetworkUtils(){}

    public static void testDataLoading(Context context){
        Log.d(LOG_TAG, "testDataLoading()");
        AsyncTask<Void, Void, Void> loadTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                //extractCovidRecords();
                extractCovidRecords(context);
                return null;
            }
        };
        loadTask.execute();
    }
    public static void extractCovidRecords(Context context){
        String url = buildURL();
        String jsonResponse = makeHttpRequest(url);

        if(jsonResponse == null){
            Log.d(LOG_TAG, "response is null");
        }else {
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                Log.d(LOG_TAG, jsonResponse.length() + "");
                Log.d(LOG_TAG, jsonObject.has("data") ? "true" : "false");
                JSONObject dataObject = jsonObject.getJSONObject("data");
                Log.d(LOG_TAG, dataObject.has("timeline") ? "has timeline" : "doesn't have timeline");
                JSONArray timeline = dataObject.getJSONArray("timeline");
                Log.d(LOG_TAG, timeline.length() + " " + "timeline");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    private static String buildURL(){
        return API_ENDPOINT + PATH + "/" + COUNTRY_CODE;
    }

    private static String makeHttpRequest(String url){
        Log.d(LOG_TAG, "NetworkUtils::makeHttpRequest()");
        String jsonResponse = null;
        if(url == null || url.equals(""))
            return null;

        HttpURLConnection urlConnection;
        InputStream inputStream;
        try {
            URL urlObject = new URL(url);
            urlConnection = (HttpURLConnection) urlObject.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();
            Log.d(LOG_TAG, "Status code: " + urlConnection.getResponseCode());
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = jsonFromInputStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    private static String jsonFromInputStream(InputStream inputStream) {
        Log.d(LOG_TAG, "NetworkUtils::jsonFromInputStream()");
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
