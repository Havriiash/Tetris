package com.tetris.dmitriy.tetris.game.figures;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Dmitriy on 31.08.2016.
 */
public class FigureI extends Figure {

    /**
     * 0 -  0 1 0 0
     *      0 1 0 0
     *      0 1 0 0
     *      0 1 0 0
     *
    /* 1 -  0 0 0 0
     *      1 1 1 1
     *      0 0 0 0
     *      0 0 0 0
     */

    @Override
    public int getOrientation() {
        return 2;
    }

    @Override
    protected void setOrientation(int position) {
        switch (position) {
            case 0:
                for (int i = 0; i < HEIGHT; i++) {
                    mFigure[i][1] = 1;
                }
                break;
            case 1:
                for (int i = 0; i < WIDTH; i++) {
                    mFigure[1][i] = 1;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public Bitmap draw(int marginHorizontal, int marginVertical) {
        Bitmap result;
        switch (mCurrentOrientation) {
            case 0: {
                final int bmpWidth = 1 * marginHorizontal;
                final int bmpHeight = 4 * marginVertical;
                result = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(result);
                canvas.drawRect(new Rect(0, 0, bmpWidth, bmpHeight), mPaint);
            } break;
            case 1: {
                final int bmpWidth = 4 * marginHorizontal;
                final int bmpHeight = 1 * marginVertical;
                result = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(result);
                canvas.drawRect(new Rect(0, 0, bmpWidth, bmpHeight), mPaint);
            } break;
            default:
                return null;
        }
        return result;
    }

}
