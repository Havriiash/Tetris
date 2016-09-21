package com.tetris.dmitriy.tetris.game.figures;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Dmitriy on 01.09.2016.
 */
public class FigureO extends Figure {

    /**
     *  1 1 0 0
     *  1 1 0 0
     *  0 0 0 0
     *  0 0 0 0
     */

    public void setOrientation(int position) {
        mFigure[0][0] = 1;
        mFigure[0][1] = 1;
        mFigure[1][0] = 1;
        mFigure[1][1] = 1;
    }

    @Override
    public Bitmap draw(int marginHorizontal, int marginVertical) {
        Bitmap result;
        final int bmpWidth = 2 * marginHorizontal;
        final int bmpHeight = 2 * marginVertical;
        result = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawRect(new Rect(0, 0, bmpWidth, bmpHeight), mPaint);
        return result;
    }

    @Override
    public int getOrientation() {
        return 1;
    }

    @Override
    public void rotate() {
        return; // this figure is not rotating
    }
}
