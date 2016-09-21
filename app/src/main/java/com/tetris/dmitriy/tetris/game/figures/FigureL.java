package com.tetris.dmitriy.tetris.game.figures;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Dmitriy on 01.09.2016.
 */
public class FigureL extends Figure {

    /**
     *  0 - 1 0 0 0
     *      1 0 0 0
     *      1 1 0 0
     *      0 0 0 0
     *
     *  1 - 1 1 1 0
     *      1 0 0 0
     *      0 0 0 0
     *      0 0 0 0
     *
     *  2 - 1 1 0 0
     *      0 1 0 0
     *      0 1 0 0
     *      0 0 0 0
     *
     *  3 - 0 0 1 0
     *      1 1 1 0
     *      0 0 0 0
     *      0 0 0 0
     */

    @Override
    public int getOrientation() { return 4; }

    public void setOrientation(int position) {
        super.setOrientation(position);
        switch (mCurrentOrientation) {
            case 0:
                mFigure[0][0] = 1;
                mFigure[1][0] = 1;
                mFigure[2][0] = 1;
                mFigure[2][1] = 1;
                break;
            case 1:
                mFigure[0][0] = 1;
                mFigure[0][1] = 1;
                mFigure[0][2] = 1;
                mFigure[1][0] = 1;
                break;
            case 2:
                mFigure[0][0] = 1;
                mFigure[0][1] = 1;
                mFigure[1][1] = 1;
                mFigure[2][1] = 1;
                break;
            case 3:
                mFigure[0][2] = 1;
                mFigure[1][0] = 1;
                mFigure[1][1] = 1;
                mFigure[1][2] = 1;
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
                final int bmpWidth = 2 * marginHorizontal;
                final int bmpHeight = 3 * marginVertical;
                result = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(result);
                canvas.drawRect(new Rect(0, 3 * marginVertical, 2 * marginHorizontal, 2 * marginVertical), mPaint);
                canvas.drawRect(new Rect(0, 0, 1 * marginHorizontal, 3 * marginVertical), mPaint);
            } break;
            case 1: {
                final int bmpWidth = 3 * marginHorizontal;
                final int bmpHeight = 2 * marginVertical;
                result = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(result);
                canvas.drawRect(new Rect(0, 1 * marginVertical, 1 * marginHorizontal, 2 * marginVertical), mPaint);
                canvas.drawRect(new Rect(0, 0, 3 * marginHorizontal, 1 * marginVertical), mPaint);
            } break;
            case 2: {
                final int bmpWidth = 2 * marginHorizontal;
                final int bmpHeight = 3 * marginVertical;
                result = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(result);
                canvas.drawRect(new Rect(0, 0, 2 * marginHorizontal, 1 * marginVertical), mPaint);
                canvas.drawRect(new Rect(1 * marginHorizontal, 0, 2 * marginHorizontal, 3 * marginVertical), mPaint);
            } break;
            case 3: {
                final int bmpWidth = 3 * marginHorizontal;
                final int bmpHeight = 2 * marginVertical;
                result = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(result);
                canvas.drawRect(new Rect(3 * marginHorizontal, 0, 2 * marginHorizontal, 2 * marginVertical), mPaint);
                canvas.drawRect(new Rect(0, 1 * marginVertical, 3 * marginHorizontal, 2 * marginVertical), mPaint);
            } break;
            default:
                return null;
        }
        return result;
    }

}
