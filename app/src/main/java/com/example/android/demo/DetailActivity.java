package com.example.android.demo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    private final String LOG_TAG = DetailActivity.class.getSimpleName();
    private TextView mDate;
    private TextView mNewConfirmed;
    private TextView mTotalConfirmed;
    private TextView mNewDeaths;
    private TextView mTotalDeaths;
    private TextView mTotalActive;
    private FloatingActionButton mShareFAB;
    private CovidRecord mCurrentRecord = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setupActivityTitle();
        getCurrentRecord();
        initViews();
        displayData();

    }

    private void getCurrentRecord() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null && bundle.containsKey("Record")) {
            mCurrentRecord = (CovidRecord) bundle.getSerializable("Record");
        }
    }


    private void displayData() {
        if(mCurrentRecord != null) {
            mDate.setText(String.valueOf(mCurrentRecord.getDate()));
            mNewConfirmed.setText(String.valueOf(mCurrentRecord.getConfirmedCases()));
            mTotalConfirmed.setText(String.valueOf(mCurrentRecord.getTotalConfirmed()));
            mNewDeaths.setText(String.valueOf(mCurrentRecord.getDeaths()));
            mTotalDeaths.setText(String.valueOf(mCurrentRecord.getTotalDeaths()));
            mTotalActive.setText(String.valueOf(mCurrentRecord.getActive()));
            Log.d(LOG_TAG, mCurrentRecord.getDate());
        }
    }

    private void initViews() {
        mDate = findViewById(R.id.textView_date);
        mNewConfirmed = findViewById(R.id.textView_newConfirmedCases);
        mTotalConfirmed = findViewById(R.id.textView_totalConfirmedCases);
        mNewDeaths = findViewById(R.id.textView_newDeaths);
        mTotalDeaths = findViewById(R.id.textView_totalDeaths);
        mTotalActive = findViewById(R.id.textView_totalActiveCases);
        mShareFAB = findViewById(R.id.fab_share);
    }

    private void setupActivityTitle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_detailActivity);
        }

    }
}