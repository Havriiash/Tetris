package com.tetris.dmitriy.tetris.game.figures;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

/**
 * Created by Dmitriy on 01.09.2016.
 */

public abstract class Figure {
    public static final int WIDTH = 4;
    public static final int HEIGHT = 4;

    private Point mCurrentCords;

    private enum FigureTypes {
        I, J
    }

    public static Figure createRandomFigure() {
        Random rnd = new Random();
        int index = rnd.nextInt(FigureTypes.values().length);
        FigureTypes type = FigureTypes.values()[index];
        switch (type) {
            case I:
                return new FigureI();
            case J:
                return new FigureJ();
                default:
                    return null;
        }
    }

    /**
     * currentOrientation - I mean a rotate angle of current figure
     * 0 - 0 deg, default
     * 1 - 90 deg
     * 2 - 180 deg
     * 3 - 270 deg
     * */
    protected int mCurrentOrientation = 0;
    protected int[][] mFigure;

    protected int mPrevOrientation = 0;
    protected Paint mPaint;

    public Figure() {
        mFigure = new int[HEIGHT][WIDTH];
        mCurrentCords = new Point(4, 0);

        setOrientation(mCurrentOrientation);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(randColor());
    }

    private void resetFigure() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                mFigure[i][j] = 0;
            }
        }
    }

    public void setOrientation(int position) {
        resetFigure();
        mCurrentOrientation = position;
    }

    public abstract int getOrientation();

    public abstract Bitmap draw(int marginHorizontal, int marginVertical);

    public int randColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(250), rnd.nextInt(250), rnd.nextInt(250));
    }

    public int[][] getFigure() {
        return mFigure;
    }

    public Paint getPaint() { return mPaint; }

    public void setCurrentCords(Point p) {
        mCurrentCords.x = p.x;
        mCurrentCords.y = p.y;
    }

    public int getX() { return mCurrentCords.x; }

    public int getY() { return mCurrentCords.y; }

    public void rotate() {
        mPrevOrientation = mCurrentOrientation;
        if (mCurrentOrientation == getOrientation() - 1) {
            mCurrentOrientation = 0;
        } else {
            mCurrentOrientation++;
        }
        resetFigure();
        setOrientation(mCurrentOrientation);
    }

    public int getCurrentOrientation() {
        return mCurrentOrientation;
    }

}
