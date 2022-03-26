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
                JSONObject timelineRecord = dataTimeline.getJSONObject(i);
                String date = timelineRecord.getString("date");
                int confirmedCases = timelineRecord.getInt("new_confirmed");
                int deaths = timelineRecord.getInt("new_deaths");
                int active = timelineRecord.getInt("active");
                int totalDeaths = timelineRecord.getInt("deaths");
                int totalConfirmed = timelineRecord.getInt("confirmed");
                CovidRecord covidRecord = new CovidRecord(
                        date,
                        confirmedCases,
                        deaths,
                        active
                );
                covidRecord.setTotalConfirmed(totalConfirmed);
                covidRecord.setTotalDeaths(totalDeaths);

                Log.d(LOG_TAG, i + ", active cases: " + covidRecord.getActive());
                records.add(covidRecord);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return records;
    }
}
