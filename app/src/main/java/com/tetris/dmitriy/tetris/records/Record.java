package com.tetris.dmitriy.tetris.records;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dmitriy on 28.09.2016.
 */

public class Record implements Parcelable {
    private long mId;
    private int mLevel;
    private int mScore;
    private long mTime;

    public Record(long id, int level, int score, long time) {
        mId = id;
        mLevel = level;
        mScore = score;
        mTime = time;
    }

    public long getId() {
        return mId;
    }

    public int getLevel() {
        return mLevel;
    }

    public int getScore() {
        return mScore;
    }

    public long getTime() {
        return mTime;
    }

    /** implement Parcelable interface */

    protected Record(Parcel in) {
        mId = in.readLong();
        mLevel = in.readInt();
        mScore = in.readInt();
        mTime = in.readLong();
    }

    public static final Creator<Record> CREATOR = new Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
        parcel.writeInt(mLevel);
        parcel.writeInt(mScore);
        parcel.writeLong(mTime);
    }

}
