package com.tetris.dmitriy.tetris.game;

import android.graphics.Point;

/**
 * Created by Dmitriy on 15.09.2016.
 */
public class PlayField {
    private static final int REAL_WIDTH = 12;
    private static final int REAL_HEIGHT = 21;

    public static final int WIDTH = 10;
    public static final int HEIGHT = 20;

    private int[][] mField;

    public PlayField() {
        mField = new int[REAL_HEIGHT][REAL_WIDTH];
        init();
    }

    private void init() {
        for (int i = 0; i < REAL_HEIGHT; i++) {
            mField[i][0] = -1;
            mField[i][11] = -1;
        }
        for (int j = 0; j < REAL_WIDTH; j++) {
            mField[20][j] = -1;
        }
    }

    public void setState(Point point, int state) {
        if (!checkRange(point)) {
            return;
        }
        mField[point.y][point.x] = state;
    }

    public int getState(Point point) {
        if (!checkRange(point)) {
            return -1;
        }
        return mField[point.y][point.x];
    }

    public void clearLine(int yLine) {
        for (int i = 1; i < WIDTH + 1; i++) {
            mField[yLine][i] = 0;
        }
    }

    public void print() {
        for (int i = 0; i < REAL_HEIGHT; i++) {
            for (int j = 0; j < REAL_WIDTH; j++) {
                System.out.print(mField[i][j] + " ");
            }
            System.out.println();
        }
    }

    /** check coordinates only for game space */
    private boolean checkRange(Point p) {
        if (p.x < 1 || p.x > REAL_WIDTH - 2
                || p.y < 0 || p.y > REAL_HEIGHT - 1) {
            return false;
        }
        return true;
    }

}
