package com.example.android.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.demo.data.NetworkUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<List<CovidRecord>> {

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


        ListView listView = findViewById(R.id.list_covidRecords);
         mProgressBar = findViewById(R.id.progressBar_loading);
         mProgressBar.setVisibility(View.VISIBLE);
         mCovidAdapter = new CovidRecordAdapter(this, 0);
         listView.setAdapter(mCovidAdapter);
         LoaderManager.getInstance(this).initLoader(0, null, this);
    }

    @NonNull
    @Override
    public Loader<List<CovidRecord>> onCreateLoader(int id, @Nullable Bundle args) {
        return new CovidLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<CovidRecord>> loader, List<CovidRecord> data) {
        mProgressBar.setVisibility(View.INVISIBLE);
        mCovidAdapter.addAll(data);
        //mRecyclerView.setAdapter(mCovidAdapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<CovidRecord>> loader) {
        mCovidAdapter.clear();
    }
}