package com.tetris.dmitriy.tetris.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.tetris.dmitriy.tetris.R;
import com.tetris.dmitriy.tetris.game.figures.Figure;

/**
 * Created by Dmitriy on 14.09.2016.
 */

public class GlassView extends View {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;

    private float mMarginH;
    private float mMarginV;

    /** Elements for painting */
    private Paint mGlassBorderPaint;
    private Paint mGlassGridPaint;
    private Figure mCurrentFigure;

    public GlassView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mGlassBorderPaint = new Paint();
        mGlassBorderPaint.setStyle(Paint.Style.FILL);
        mGlassBorderPaint.setColor(ContextCompat.getColor(getContext(), R.color.glass_background));

        mGlassGridPaint = new Paint();
        mGlassGridPaint.setStyle(Paint.Style.STROKE);
        mGlassGridPaint.setColor(ContextCompat.getColor(getContext(), R.color.glass_grid));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mMarginH = getRight() / WIDTH;
        mMarginV = getBottom() / HEIGHT;
        drawGlass(canvas);
    }

    private void drawGlass(Canvas canvas) {
        canvas.drawRect(new Rect(0, 0, getRight(), getBottom()), mGlassBorderPaint);
//        TODO: need to optimize
        for (int i = 0; i < WIDTH; i++) { // draw vertical grid
            canvas.drawLine(i * mMarginH, 0, i * mMarginH, getBottom(), mGlassGridPaint);
        }
        for (int i = 0; i < HEIGHT; i++) { // draw horizontal grid
            canvas.drawLine(0, i * mMarginV, getRight(), i * mMarginV, mGlassGridPaint);
        }

        drawFigure(canvas, mCurrentFigure);
    }

    public void drawFigure(Canvas canvas, Figure figure) {
        if (figure == null) {
            return;
        }

//        int marginRight = WIDTH - x - 1; // WIDTH - x - figureWidth
//        int marginBottom = HEIGHT - y - 4; // HEIGHT - y - figureHeight

//        Paint figurePaint = figure.getPaint();;
//        Bitmap bmp = figure.draw((int)mMarginH, (int)mMarginV);
//        canvas.drawBitmap(bmp, figure.getCurrentCords().x * mMarginH, figure.getCurrentCords().y * mMarginV, figurePaint);

//        bmp.recycle();
    }

    public void setCurrentFigure(Figure mCurrentFigure) {
        this.mCurrentFigure = mCurrentFigure;
    }

}
