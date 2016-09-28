package com.tetris.dmitriy.tetris;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.tetris.dmitriy.tetris.records.Record;
import com.tetris.dmitriy.tetris.records.RecordAdapter;
import com.tetris.dmitriy.tetris.records.RecordsService;

import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        mProgress = (ProgressBar) findViewById(R.id.ActivityRecords_progress);
        mRecyclerView = (RecyclerView) findViewById(R.id.ActivityRecords_recyclerView);
        mRecyclerView.setAdapter(null);
        switchVisibility(true);

        LocalBroadcastManager.getInstance(this).registerReceiver(mRecordsReceiver, new IntentFilter(RecordsService.RESULT_ACTION));
        Intent getRecordsIntent = new Intent(this, RecordsService.class);
        startService(getRecordsIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRecordsReceiver);
    }

    private void fillRecyclerView(RecordAdapter adapter) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    private void switchVisibility(boolean showProgress) {
        if (showProgress) {
            mProgress.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mProgress.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private BroadcastReceiver mRecordsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Record> recordsLst = intent.getParcelableArrayListExtra(RecordsService.PARAM_RESULT);
            fillRecyclerView(new RecordAdapter(recordsLst));
            switchVisibility(false);
        }
    };

}
