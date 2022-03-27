package com.example.android.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;


import com.example.android.demo.Notifications.NotificationsWorker;
import com.example.android.demo.data.NetworkUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<List<CovidRecord>>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private CovidRecordAdapter mCovidAdapter;
    private ProgressBar mProgressBar;
    private ListView mListView;
    private static final String WORK_TAG_NOTIFICATIONS = "work-tag-notifications";
    private final int REPEAT_INTERVAL = 1;
    private final int FLEX_INTERVAL = 2;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupOnSharedPreferencesChangeListener();

        schedulePeriodicNotifications();

        showProgressBar();

        setupListView();

        checkNetworkStatus();

        LoaderManager.getInstance(this).initLoader(0, null, this);
    }

    private void checkNetworkStatus(){
        if(!NetworkUtils.hasNetworkConnection(this)){
            View emptyView = findViewById(R.id.textView_noConnection);
            emptyView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void setupListView() {
        mListView = findViewById(R.id.list_covidRecords);
        mCovidAdapter = new CovidRecordAdapter(this, 0);
        mListView.setAdapter(mCovidAdapter);
        mListView.setOnItemClickListener((adapterView, view, position, l) -> {
            CovidRecord record = (CovidRecord) adapterView.getItemAtPosition(position);
            Log.d(LOG_TAG, record.getDate());
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Record", record);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void showProgressBar() {
        mProgressBar = findViewById(R.id.progressBar_loading);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void setupOnSharedPreferencesChangeListener(){
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
        return new CovidLoader(this, getCountryCode(MainActivity.this));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<CovidRecord>> loader, List<CovidRecord> data) {
        mProgressBar.setVisibility(View.INVISIBLE);
        mCovidAdapter.clear();
        if(data != null)
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
        if(item.getItemId() == R.id.menuOption_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getResources().getString(R.string.key_country))) {
            LoaderManager.getInstance(this).restartLoader(0, null, this);
        } else if(key.equals(getResources().getString(R.string.key_notifications))){
            if(!areNotificationsEnabled())
                WorkManager.getInstance(this).cancelAllWorkByTag(WORK_TAG_NOTIFICATIONS);
            else{
                schedulePeriodicNotifications();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void schedulePeriodicNotifications(){
        if(areNotificationsEnabled()) {
            WorkRequest fireNotification =
                    new PeriodicWorkRequest.Builder(NotificationsWorker.class,
                            REPEAT_INTERVAL, TimeUnit.DAYS,
                            FLEX_INTERVAL, TimeUnit.HOURS)
                            .addTag(WORK_TAG_NOTIFICATIONS)
                            .setInitialDelay(10, TimeUnit.MINUTES) //for testing
                            .build();
            WorkManager.getInstance(this).enqueue(fireNotification);
        }
    }
    private boolean areNotificationsEnabled(){
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences
                .getBoolean(getResources().getString(R.string.key_notifications), false);
    }

}