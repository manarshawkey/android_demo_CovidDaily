package com.example.android.demo;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.android.demo.data.NetworkUtils;

import java.util.List;

public class CovidLoader extends AsyncTaskLoader<List<CovidRecord>> {

    private final String mCountryCode;
    public CovidLoader(Context context, String countryCode){
        super(context);
        mCountryCode = countryCode;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public List<CovidRecord> loadInBackground() {
        return NetworkUtils.extractCovidRecords(mCountryCode);
    }
}
