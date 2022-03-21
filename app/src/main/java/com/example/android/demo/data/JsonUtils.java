package com.example.android.demo.data;

import android.util.Log;

import com.example.android.demo.CovidRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    private static final String LOG_TAG = JsonUtils.class.getSimpleName();
    public static ArrayList<CovidRecord> extractCovidRecords(String jsonResponse, int numOfRecords){
        if(numOfRecords < 1 || jsonResponse == null || jsonResponse.equals(""))
            return null;
        ArrayList<CovidRecord> records = new ArrayList<>(numOfRecords);
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            JSONArray dataTimeline = dataObject.getJSONArray("timeline");
            for(int i = 0; i < numOfRecords; i++){
                String date = dataTimeline.getJSONObject(i).getString("date");
                int confirmedCases = dataTimeline.getJSONObject(i).getInt("new_confirmed");
                int deaths = dataTimeline.getJSONObject(i).getInt("new_deaths");
                int active = dataTimeline.getJSONObject(i).getInt("active");
                CovidRecord covidRecord = new CovidRecord(
                        date,
                        confirmedCases,
                        deaths,
                        active
                );
                Log.d(LOG_TAG, i + ", active cases: " + covidRecord.getActive());
                records.add(covidRecord);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return records;
    }
}
