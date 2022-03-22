package com.example.android.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class MainActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<List<CovidRecord>>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private CustomRecyclerViewAdapter mAdapter;
    private CovidRecordAdapter mCovidAdapter;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_demo);
        mAdapter = new CustomRecyclerViewAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        NetworkUtils.testDataLoading(this); */


        setupOnSharedPreferencesChangeListener();
        ListView listView = findViewById(R.id.list_covidRecords);
        mProgressBar = findViewById(R.id.progressBar_loading);
        mProgressBar.setVisibility(View.VISIBLE);
        mCovidAdapter = new CovidRecordAdapter(this, 0);
        listView.setAdapter(mCovidAdapter);
        LoaderManager.getInstance(this).initLoader(0, null, this);
    }
    private void setupOnSharedPreferencesChangeListener(){
        Log.d(LOG_TAG, "setupPreferenceChangeListener()");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private String getCountryCode(Context context){
        String countryCode;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        countryCode = sharedPreferences.getString(
                context.getResources().getString(R.string.key_country), "EG");
        return countryCode;
    }

    @NonNull
    @Override
    public Loader<List<CovidRecord>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(LOG_TAG, "loader is being created.");
        return new CovidLoader(this, getCountryCode(MainActivity.this));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<CovidRecord>> loader, List<CovidRecord> data) {
        mProgressBar.setVisibility(View.INVISIBLE);
        mCovidAdapter.clear();
        mCovidAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<CovidRecord>> loader) {
        mProgressBar.setVisibility(View.VISIBLE);
        mCovidAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_options){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(LOG_TAG, "preference changed: " + key);
        LoaderManager.getInstance(this).restartLoader(0, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}