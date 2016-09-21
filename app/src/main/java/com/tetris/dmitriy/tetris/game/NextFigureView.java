package com.tetris.dmitriy.tetris.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.tetris.dmitriy.tetris.R;

/**
 * Created by Dmitriy on 21.09.2016.
 */

public class NextFigureView extends ImageView {

    private Paint mGridPaint;

    public NextFigureView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mGridPaint = new Paint();
        mGridPaint.setStyle(Paint.Style.STROKE);
        mGridPaint.setColor(ContextCompat.getColor(getContext(), R.color.glass_grid));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGrid(canvas);
    }

    private void drawGrid(Canvas canvas) {
        final float mMarginWidth = getWidth() / 2;
        final float mMarginHeight = getHeight() / 2 ;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                canvas.drawLine(i * mMarginWidth, 0, i * mMarginWidth, getBottom(), mGridPaint);
            }
            canvas.drawLine(0, i * mMarginHeight, getRight(), i * mMarginHeight, mGridPaint);
        }
    }

}
