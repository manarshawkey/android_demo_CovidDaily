package com.example.android.demo.data;

import android.util.Log;

import com.example.android.demo.CovidRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

                String formattedDate = formatDate(date);

                int confirmedCases = timelineRecord.getInt("new_confirmed");
                int deaths = timelineRecord.getInt("new_deaths");
                int active = timelineRecord.getInt("active");
                int totalDeaths = timelineRecord.getInt("deaths");
                int totalConfirmed = timelineRecord.getInt("confirmed");
                CovidRecord covidRecord = new CovidRecord(
                        formattedDate,
                        confirmedCases,
                        deaths,
                        active
                );
                covidRecord.setTotalConfirmed(totalConfirmed);
                covidRecord.setTotalDeaths(totalDeaths);
                covidRecord.setWeekday(getWeekday(date));
                Log.d(LOG_TAG, i + ", active cases: " + covidRecord.getActive());
                records.add(covidRecord);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return records;
    }
    private static String formatDate(String d){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date;
        String stringDate = null;
        try {
            date = simpleDateFormat.parse(d);
            if(date != null)
                stringDate = new SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return stringDate;
    }
    private static String getWeekday(String d){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date;
        String stringDate = null;
        try {
            date = simpleDateFormat.parse(d);
            if(date != null)
                stringDate = new SimpleDateFormat("EEE", Locale.US).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return stringDate;//d.substring(0, 3).toUpperCase(Locale.ROOT);
    }
}
