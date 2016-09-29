package com.tetris.dmitriy.tetris.records;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tetris.dmitriy.tetris.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Dmitriy on 28.09.2016.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    private ArrayList<Record> mRecordsLst;

    public RecordAdapter(ArrayList<Record> recordsLst) {
        mRecordsLst = recordsLst;
    }

    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Record item = mRecordsLst.get(position);
        holder.txtId.setText(Long.toString(item.getId()));
        holder.txtLevel.setText(Integer.toString(item.getLevel()));
        holder.txtScore.setText(Integer.toString(item.getScore()));
        holder.txtTime.setText((new SimpleDateFormat("mm:ss")).format(item.getTime()));
    }

    @Override
    public int getItemCount() {
        return mRecordsLst.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtId;
        public TextView txtLevel;
        public TextView txtScore;
        public TextView txtTime;

        public ViewHolder(View itemView) {
            super(itemView);
            txtId = (TextView) itemView.findViewById(R.id.ItemRecord_Id);
            txtLevel = (TextView) itemView.findViewById(R.id.ItemRecord_Level);
            txtScore = (TextView) itemView.findViewById(R.id.ItemRecord_Score);
            txtTime = (TextView) itemView.findViewById(R.id.ItemRecord_Time);
        }
    }

}
