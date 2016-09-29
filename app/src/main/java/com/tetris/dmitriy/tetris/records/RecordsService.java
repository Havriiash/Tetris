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
    private static final String SERVICE_NAME = "com.tetris.dmitriy.tetris.records.RecordsService";

    public static final String SERVICE_ACTION = SERVICE_NAME + ".Action";
    public static final String PARAM_RESULT = SERVICE_NAME + ".Param.Result";

    public RecordsService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final ArrayList<Record> recordsLst = getRecords(new DBHelper(this).getReadableDatabase());
        Intent resultIntent = new Intent(SERVICE_ACTION);
        resultIntent.putParcelableArrayListExtra(PARAM_RESULT, recordsLst);
        LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
    }

    public static ArrayList<Record> getRecords(final SQLiteDatabase db) {
        ArrayList<Record> resultLst = new ArrayList<>();
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

    /** this method need to call not from main thread */
    public static boolean hasNewRecord(final SQLiteDatabase db, final Record newRecord) {
        boolean result = false;
        String[] columns = new String[] {DBHelper.RECORDS_GAME_SCORES};
        String selection = DBHelper.RECORDS_GAME_SCORES + " <  ? ";
        String limit = "1";
        String[] args = new String[] { Integer.toString(newRecord.getScore()) };

        Cursor cursor = db.query(DBHelper.RECORDS ,columns, selection, args, null, null, null, limit);
        if (cursor.moveToFirst()) {
            result = true;
        }

        cursor.close();
        db.close();
        return result;
    }

}
