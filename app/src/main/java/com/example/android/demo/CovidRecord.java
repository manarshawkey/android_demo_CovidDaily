package com.example.android.demo;


public class CovidRecord {

    private int deaths;
    private int confirmedCases;
    private String date;


    CovidRecord(String date, int confirmedCases, int deaths){
        this.date = date;
        this.confirmedCases = confirmedCases;
        this.deaths = deaths;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getConfirmedCases() {
        return confirmedCases;
    }

    public void setConfirmedCases(int confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }
}
