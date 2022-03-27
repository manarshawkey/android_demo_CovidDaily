package com.example.android.demo;


//Not used
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter <CustomRecyclerViewAdapter.ListItemHolder>{

    private Context mContext;
    private ArrayList<String> dummyData;

    CustomRecyclerViewAdapter(Context context){
        mContext = context;
        initDummyData();
    }

    private void initDummyData(){
        dummyData = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            dummyData.add("" + i);
        }
    }
    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new ListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        holder.listItemName.setText(dummyData.get(position));
    }

    @Override
    public int getItemCount() {
        return dummyData.size();
    }

    static class ListItemHolder extends RecyclerView.ViewHolder{

        TextView listItemName;
        public ListItemHolder(@NonNull View itemView) {
            super(itemView);
            listItemName = itemView.findViewById(R.id.textView_listItemName);
        }
    }
}
