package com.tetris.dmitriy.tetris.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.tetris.dmitriy.tetris.R;
import com.tetris.dmitriy.tetris.game.figures.Figure;
import com.tetris.dmitriy.tetris.game.figures.FigureI;

/**
 * Created by Dmitriy on 14.09.2016.
 */

public class Glass extends View {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;

    /** Elements for painting */
    private Paint mGlassBorderPaint;
    private Paint mGlassGridPaint;

    public Glass(Context context, AttributeSet attrs) {
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
        drawGlass(canvas);
    }

    private void drawGlass(Canvas canvas) {
        canvas.drawRect(new Rect(0, 0, getRight(), getBottom()), mGlassBorderPaint);
        float marginH = getRight() / WIDTH;
        float marginV = getBottom() / HEIGHT;
//        TODO: need to optimize
        for (int i = 0; i < WIDTH; i++) { // draw vertical grid
            canvas.drawLine(i * marginH, 0, i * marginH, getBottom(), mGlassGridPaint);
        }
        for (int i = 0; i < HEIGHT; i++) { // draw horizontal grid
            canvas.drawLine(0, i * marginV, getRight(), i * marginV, mGlassGridPaint);
        }
        drawFigure(canvas, 5, 8);
    }

    private void drawFigure(Canvas canvas, int x, int y) {
        int marginH = getRight() / WIDTH;
        int marginV = getBottom() / HEIGHT;

        int marginRight = WIDTH - x - 1; // WIDTH - x - figureWidth
        int marginBottom = HEIGHT - y - 4; // HEIGHT - y - figureHeight

        Paint figurePaint = new Paint();
        figurePaint.setStyle(Paint.Style.FILL);
        figurePaint.setColor(Color.RED);

        Figure figure = new FigureI();
        figure.rotate();
        Bitmap bmp = figure.draw(marginH, marginV);
        canvas.drawBitmap(bmp, x * marginH, y * marginV, figurePaint);

        x -= 2;
        y -= 2;
        Bitmap bmp2 = figure.draw(marginH, marginV);
        canvas.drawBitmap(bmp, x * marginH, y * marginV, figurePaint);

//        Paint figurePaint = new Paint();
//        figurePaint.setStyle(Paint.Style.FILL);
//        figurePaint.setColor(Color.RED);
//
//        canvas.drawRect(new RectF(x * marginH, y * marginV, getRight() - (marginRight * marginH), getBottom() - (marginBottom * marginV)), figurePaint);

    }

}
