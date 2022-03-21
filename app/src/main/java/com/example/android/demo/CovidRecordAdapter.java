package com.example.android.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CovidRecordAdapter extends ArrayAdapter<CovidRecord> {
    public CovidRecordAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.record_list_item, parent, false);
        }
        CovidRecord currentRecord = getItem(position);
        TextView date = listItemView.findViewById(R.id.textView_date);
        date.setText(currentRecord.getDate());
        
        TextView confirmedCases = listItemView.findViewById(R.id.textView_confirmed);
        confirmedCases.setText(String.valueOf(currentRecord.getConfirmedCases()));
        
        TextView deaths = listItemView.findViewById(R.id.textView_deaths);
        deaths.setText(String.valueOf(currentRecord.getDeaths()));
        
        return listItemView;
    }
}
