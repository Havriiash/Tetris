package com.tetris.dmitriy.tetris.game.figures;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Dmitriy on 01.09.2016.
 */
public abstract class Figure {
    public static final int WIDTH = 4;
    public static final int HEIGHT = 4;

    /**
     * currentOrientation - I mean a rotate angle of current figure
     * 0 - 0 deg, default
     * 1 - 90 deg
     * 2 - 180 deg
     * 3 - 270 deg
     * */
    protected int mCurrentOrientation = 0;
    protected int[][] mFigure;

    protected Point mCurrentCords;
    protected Paint mPaint;

    /** Color, which figure can have */
    private List<Integer> mColors = Arrays.asList(
//            TODO: need move values for color into an application constants
            new Integer(Color.parseColor("#339933")),
            new Integer(Color.parseColor("#0099cc")),
            new Integer(Color.parseColor("#cc33ff")),
            new Integer(Color.parseColor("#cc0099")),
            new Integer(Color.parseColor("#ff5050")),
            new Integer(Color.parseColor("#ffcc66"))
    );

    public Figure() {
        mFigure = new int[WIDTH][HEIGHT];
        mCurrentCords = new Point(3, 0);

        setOrientation(mCurrentOrientation);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(randColor());
    }

    protected void resetFigure() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                mFigure[i][j] = 0;
            }
        }
    }

    protected abstract void setOrientation(int position);

    public abstract int getOrientation();

    public abstract Bitmap draw(int marginHorizontal, int marginVertical);

    public void rotate() {
        if (mCurrentOrientation == getOrientation() - 1) {
            mCurrentOrientation = 0;
        } else {
            mCurrentOrientation++;
        }
        resetFigure();
        setOrientation(mCurrentOrientation);
    }

    public int randColor() {
        Random rnd = new Random();
        return mColors.get(rnd.nextInt(mColors.size()));
    }

    public int[][] getFigure() {
        return mFigure;
    }

    public Paint getPaint() { return mPaint; }
}
