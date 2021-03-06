package com.example.android.demo.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.demo.CovidRecord;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Provider;
import java.util.ArrayList;

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String API_ENDPOINT = "https://corona-api.com/";
    private static final String PATH = "countries";
    private static final String COUNTRY_CODE =  "DE";//"EG"; //use Germany instead of Egypt for now


    /**
     * This class should not be instantiated,
     * so provide a private constructor
     */
    private NetworkUtils(){}

    /*public static void testDataLoading(Context context){
        Log.d(LOG_TAG, "testDataLoading()");
        AsyncTask<Void, Void, Void> loadTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                //extractCovidRecords();
                //extractCovidRecords(context);
                return null;
            }
        };
        loadTask.execute();
    }*/

    public static ArrayList<CovidRecord> extractCovidRecords(String countryCode){
        String url = buildURL(countryCode);
        String jsonResponse = makeHttpRequest(url);
        return JsonUtils.extractCovidRecords(jsonResponse, 10);

    }
    private static String buildURL(String countryCode){
        return API_ENDPOINT + PATH + "/" + countryCode;//COUNTRY_CODE;
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

    public static boolean hasNetworkConnection(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if(activeNetwork != null){
            int networkType = activeNetwork.getType();
            return networkType == ConnectivityManager.TYPE_WIFI
                    || networkType == ConnectivityManager.TYPE_MOBILE;
        }
        return false;
    }
}
