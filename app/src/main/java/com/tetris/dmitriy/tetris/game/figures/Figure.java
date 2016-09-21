package com.tetris.dmitriy.tetris.game.figures;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Dmitriy on 01.09.2016.
 */

public abstract class Figure {
    public static final int WIDTH = 4;
    public static final int HEIGHT = 4;

    private Point mCurrentCords;
    public static final ArrayList<Integer> sColors = new ArrayList<>(
            Arrays.asList(
                    new Integer(Color.rgb(200, 0 ,0)),
                    new Integer(Color.rgb(0, 200, 0)),
                    new Integer(Color.rgb(0, 0, 200)),
                    new Integer(Color.rgb(200, 200, 0)),
                    new Integer(Color.rgb(205, 0, 150)),
                    new Integer(Color.rgb(250, 100, 0))
                         )
    );

    public enum FigureTypes {
        I, J, L, O, S, Z, T
    }

    /** create figure by type, if type is null, method will create random figure */
    public static Figure createFigure(FigureTypes type) {
        if (type == null) {
            Random rnd = new Random();
            int index = rnd.nextInt(FigureTypes.values().length);
            type = FigureTypes.values()[index];
        }
        switch (type) {
            case I:
                return new FigureI();
            case J:
                return new FigureJ();
            case L:
                return new FigureL();
            case O:
                return new FigureO();
            case S:
                return new FigureS();
            case Z:
                return new FigureZ();
            case T:
                return new FigureT();
            default:
                return null;
        }
    }

    public static FigureTypes getRandomType() {
        Random rnd = new Random();
        int index = rnd.nextInt(FigureTypes.values().length);
        return FigureTypes.values()[index];
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
        int index = rnd.nextInt(sColors.size());
        return sColors.get(index);
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
