package com.tetris.dmitriy.tetris.records;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

/**
 * Created by Dmitriy on 28.09.2016.
 */

public class RecordsService extends IntentService {
    private static final String SERVICE_NAME = "";

    public static final String RESULT_ACTION = "com.tetris.dmitriy.tetris.records.RecordsService.Result";

    public static final String PARAM_RESULT = "com.tetris.dmitriy.tetris.records.RecordsService.Param.Result";

    public RecordsService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final ArrayList<Record> recordsLst = getRecords();
        Intent resultIntent = new Intent(RESULT_ACTION);
        resultIntent.putParcelableArrayListExtra(PARAM_RESULT, recordsLst);
        LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
    }

    private ArrayList<Record> getRecords() {
        ArrayList<Record> resultLst = new ArrayList<>();
        SQLiteDatabase db = new DBHelper(this).getWritableDatabase();
        Cursor cursor = db.query(DBHelper.RECORDS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                final long id = cursor.getLong(cursor.getColumnIndex(DBHelper.RECORDS_ID));
                final int level = cursor.getInt(cursor.getColumnIndex(DBHelper.RECORDS_GAME_LEVEL));
                final int score = cursor.getInt(cursor.getColumnIndex(DBHelper.RECORDS_GAME_SCORES));
                final long time = cursor.getLong(cursor.getColumnIndex(DBHelper.RECORDS_GAME_TIME));
                resultLst.add(new Record(id, level, score, time));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return resultLst;
    }

}
