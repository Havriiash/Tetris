package com.tetris.dmitriy.tetris.game.figures;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Dmitriy on 01.09.2016.
 */
public class FigureZ extends Figure {

    /**
     * 0 - 1 1 0 0
     *     0 1 1 0
     *     0 0 0 0
     *     0 0 0 0
     *
     * 1 - 0 1 0 0
     *     1 1 0 0
     *     1 0 0 0
     *     0 0 0 0
     */

    @Override
    public int getOrientation() {
        return 2;
    }

    @Override
    public void setOrientation(int position) {
        super.setOrientation(position);
        switch (mCurrentOrientation) {
            case 0:
                mFigure[0][0] = 1;
                mFigure[0][1] = 1;
                mFigure[1][1] = 1;
                mFigure[1][2] = 1;
                break;
            case 1:
                mFigure[0][1] = 1;
                mFigure[1][0] = 1;
                mFigure[1][1] = 1;
                mFigure[2][0] = 1;
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
                final int bmpWidth = 3 * marginHorizontal;
                final int bmpHeight = 2 * marginVertical;
                result = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(result);
                canvas.drawRect(new Rect(0, 0, 2 * marginHorizontal, 1 * marginVertical), mPaint);
                canvas.drawRect(new Rect(1 * marginHorizontal, 1 * marginVertical, bmpWidth, bmpHeight), mPaint);
            } break;
            case 1: {
                final int bmpWidth = 2 * marginHorizontal;
                final int bmpHeight = 3 * marginVertical;
                result = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(result);
                canvas.drawRect(new Rect(0, 1 * marginVertical, 1 * marginHorizontal, bmpHeight), mPaint);
                canvas.drawRect(new Rect(1 * marginHorizontal, 0, bmpWidth, 2 * marginVertical), mPaint);
            } break;
            default:
                return null;
        }
        return result;
    }

}
