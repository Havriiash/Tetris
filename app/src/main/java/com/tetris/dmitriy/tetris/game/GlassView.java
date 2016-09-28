package com.tetris.dmitriy.tetris.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.tetris.dmitriy.tetris.R;
import com.tetris.dmitriy.tetris.game.figures.Figure;

/**
 * Created by Dmitriy on 14.09.2016.
 */

public class GlassView extends ImageView {
    private float mMarginWidth;
    private float mMarginHeight;

    /** Elements for painting */
    private Paint mGlassBorderPaint;
    private Paint mGlassGridPaint;
    private Figure mCurrentFigure;

    private Bitmap mImageView;

    private int mPrevOrientation = 0;
    private Point mPrevCoordinates;

    public GlassView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mGlassBorderPaint = new Paint();
        mGlassBorderPaint.setStyle(Paint.Style.FILL);
        mGlassBorderPaint.setColor(ContextCompat.getColor(getContext(), R.color.glass_background));

        mGlassGridPaint = new Paint();
        mGlassGridPaint.setStyle(Paint.Style.STROKE);
        mGlassGridPaint.setColor(ContextCompat.getColor(getContext(), R.color.glass_grid));
    }

    public void setCurrentFigure(Figure mCurrentFigure) {
        this.mCurrentFigure = mCurrentFigure;
        mPrevCoordinates = new Point(mCurrentFigure.getX(), mCurrentFigure.getY());
        mPrevOrientation = mCurrentFigure.getCurrentOrientation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mMarginWidth = getRight() / PlayField.WIDTH;
        mMarginHeight = getBottom() / PlayField.HEIGHT;

        if (mImageView == null) {
            mImageView = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas1 = new Canvas(mImageView);
            drawGlass(canvas1);
            drawGrid(canvas1);
        }
        Canvas canvas1 = new Canvas(mImageView);
        drawFigure(canvas1, mCurrentFigure);
        setImageBitmap(mImageView);
    }

    private void drawGlass(Canvas canvas) {
        canvas.drawRect(new Rect(0, 0, getRight(), getBottom()), mGlassBorderPaint);
    }

    private void drawGrid(Canvas canvas) {
        for (int i = 0; i < PlayField.HEIGHT; i++) {
            for (int j = 0; j < PlayField.WIDTH; j++) {
                canvas.drawLine(i * mMarginWidth, 0, i * mMarginWidth, getBottom(), mGlassGridPaint);
            }
            canvas.drawLine(0, i * mMarginHeight, getRight(), i * mMarginHeight, mGlassGridPaint);
        }
    }

    public void drawFigure(Canvas canvas, Figure figure) {
        Paint figurePaint = figure.getPaint();
        final int currentOrientation = figure.getCurrentOrientation();
        final int color = figurePaint.getColor();

//        TODO: bug with clear figure sometimes

        figure.setOrientation(mPrevOrientation);
        figurePaint.setColor(Color.argb(255, 0, 0, 0));
        Bitmap clearFigure = figure.draw((int) mMarginWidth, (int) mMarginHeight);
        canvas.drawBitmap(clearFigure, (mPrevCoordinates.x - 1) * mMarginWidth, mPrevCoordinates.y * mMarginHeight, figurePaint);
        clearFigure.recycle();

        drawGrid(canvas);

        figure.setOrientation(currentOrientation);
        figurePaint.setColor(color);
        Bitmap figureBitmap = figure.draw((int) mMarginWidth, (int) mMarginHeight);
        canvas.drawBitmap(figureBitmap, (figure.getX() - 1) * mMarginWidth, figure.getY() * mMarginHeight, figurePaint);
        figureBitmap.recycle();

        mPrevCoordinates.x = figure.getX();
        mPrevCoordinates.y = figure.getY();
        mPrevOrientation = currentOrientation;
    }

    public void clearLines(int yLine, int clearLinesCount) {
        Bitmap currentBitmap = ((BitmapDrawable)getDrawable()).getBitmap();

        Bitmap beforeClearLine = Bitmap.createBitmap(currentBitmap, 0, 0, getWidth(), (int)(yLine * mMarginHeight));
        Bitmap afterClearLine = Bitmap.createBitmap(currentBitmap, 0, (int)((yLine + clearLinesCount) * mMarginHeight), getWidth(), (int)(getHeight() - ((yLine + clearLinesCount) * mMarginHeight)) );

        mImageView.recycle();
        mImageView = Bitmap.createBitmap(currentBitmap.getWidth(), currentBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mImageView);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        drawGlass(canvas);
        drawGrid(canvas);
        canvas.drawBitmap(beforeClearLine, 0, (clearLinesCount * mMarginHeight), paint);
        canvas.drawBitmap(afterClearLine, 0, ((yLine + clearLinesCount) * mMarginHeight), paint);
        setImageBitmap(mImageView);

        currentBitmap.recycle();
        beforeClearLine.recycle();
        afterClearLine.recycle();
    }

}
