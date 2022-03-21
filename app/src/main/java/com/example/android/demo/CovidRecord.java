package com.example.android.demo;


public class CovidRecord {

    private int deaths;
    private int confirmedCases;
    private String date;

    private int active;


    public CovidRecord(String date, int confirmedCases, int deaths, int active){
        this.date = date;
        this.confirmedCases = confirmedCases;
        this.deaths = deaths;
        this.active = active;
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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
