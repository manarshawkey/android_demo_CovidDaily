package com.example.android.demo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class DetailActivity extends AppCompatActivity {

    private final String LOG_TAG = DetailActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setupActivityTitle();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null && bundle.containsKey("Record")) {
            CovidRecord currentRecord = (CovidRecord) bundle.getSerializable("Record");
            Log.d(LOG_TAG, currentRecord.getDate());
        }
    }

    private void setupActivityTitle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_detailActivity);
        }

    }
}