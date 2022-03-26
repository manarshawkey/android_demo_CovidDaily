package com.example.android.demo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    private final String LOG_TAG = DetailActivity.class.getSimpleName();
    private TextView mDate;
    private TextView mNewConfirmed;
    private TextView mTotalConfirmed;
    private TextView mNewDeaths;
    private TextView mTotalDeaths;
    private TextView mTotalActive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setupActivityTitle();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null && bundle.containsKey("Record")) {
            CovidRecord currentRecord = (CovidRecord) bundle.getSerializable("Record");
            mDate = findViewById(R.id.textView_date);
            mDate.setText(String.valueOf(currentRecord.getDate()));
            mNewConfirmed = findViewById(R.id.textView_newConfirmedCases);
            mNewConfirmed.setText(String.valueOf(currentRecord.getConfirmedCases()));
            mTotalConfirmed = findViewById(R.id.textView_totalConfirmedCases);
            mTotalConfirmed.setText(String.valueOf(currentRecord.getTotalConfirmed()));
            mNewDeaths = findViewById(R.id.textView_newDeaths);
            mNewDeaths.setText(String.valueOf(currentRecord.getDeaths()));
            mTotalDeaths = findViewById(R.id.textView_totalDeaths);
            mTotalDeaths.setText(String.valueOf(currentRecord.getTotalDeaths()));
            mTotalActive = findViewById(R.id.textView_totalActiveCases);
            mTotalActive.setText(String.valueOf(currentRecord.getActive()));
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