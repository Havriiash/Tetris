package com.tetris.dmitriy.tetris.records;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Dmitriy on 24.09.2016.
 */

public class UpdateService extends IntentService {
    private static final String SERVICE_NAME = "com.tetris.dmitriy.tetris.database.UpdateService";

    public static final String PARAM_RECORD = SERVICE_NAME + ".Param.Record";

    private DBHelper mDbHelper;

    public UpdateService() {
        super(SERVICE_NAME);
        mDbHelper = new DBHelper(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Record newRecord = intent.getParcelableExtra(PARAM_RECORD);
        sortRecords(newRecord);
    }

    private void sortRecords(final Record newRecord) {
        ArrayList<Record> recordsLst = RecordsService.getRecords(mDbHelper.getReadableDatabase());
        recordsLst.add(newRecord);
        /** sort by ascending */
        Collections.sort(recordsLst, new Comparator<Record>() {
            @Override
            public int compare(Record record, Record t1) {
                if (record.getScore() == t1.getScore()) {
                    return 0;
                } else if (record.getScore() < t1.getScore()) {
                    return 1;
                }
                return -1;
            }
        });
        recordsLst.remove(recordsLst.size() - 1);
        updateRecords(recordsLst);
    }

    private void updateRecords(final ArrayList<Record> recordsLst) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(DBHelper.RECORDS, null, null); // clear records table
        db.execSQL("UPDATE sqlite_sequence SET seq = 0 WHERE name='" + DBHelper.RECORDS + "'"); // drop database sequence

        for (Record rec : recordsLst) {
            ContentValues cv = new ContentValues();
            cv.put(DBHelper.RECORDS_GAME_LEVEL, rec.getLevel());
            cv.put(DBHelper.RECORDS_GAME_SCORES, rec.getScore());
            cv.put(DBHelper.RECORDS_GAME_TIME, rec.getTime());
            db.insert(DBHelper.RECORDS, null, cv);
        }

        db.close();
    }

}
