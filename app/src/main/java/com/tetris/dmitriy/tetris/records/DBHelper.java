package com.tetris.dmitriy.tetris.records;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

/**
 * Created by Dmitriy on 24.09.2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "tetris_records.db";
    private static final int MAX_RECORDS = 5;

    public static final String RECORDS = "records";
    public static final String RECORDS_ID = "id";
    public static final String RECORDS_GAME_LEVEL = "game_level";
    public static final String RECORDS_GAME_SCORES = "game_score";
    public static final String RECORDS_GAME_TIME = "game_time";

    private static final String mDBPath = "/data/data/com.tetris.dmitriy.tetris/databases/tetris_records.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
//        File dbFile = new File(mDBPath);
//        dbFile.delete();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String createQuery = "CREATE TABLE `"+ RECORDS +"` (" +
                            "`"+ RECORDS_ID +"`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                            "`"+ RECORDS_GAME_LEVEL +"` INTEGER NOT NULL," +
                            "`"+ RECORDS_GAME_SCORES +"` INTEGER NOT NULL," +
                            "`" + RECORDS_GAME_TIME + "` INTEGER NOT NULL);";
        sqLiteDatabase.execSQL(createQuery);
        for (int i = 1; i < MAX_RECORDS + 1; i++) {
            final String recordQuery = "INSERT INTO `" + RECORDS + "` (`" + RECORDS_ID + "`,`" + RECORDS_GAME_LEVEL + "`,`" + RECORDS_GAME_SCORES + "`,`" + RECORDS_GAME_TIME + "`) VALUES (" + i +",0,0,0);";
            sqLiteDatabase.execSQL(recordQuery);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
