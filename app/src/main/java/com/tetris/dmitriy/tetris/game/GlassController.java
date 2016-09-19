package com.tetris.dmitriy.tetris.game;

import android.graphics.Bitmap;

/**
 * Created by Dmitriy on 20.09.2016.
 */

public interface GlassController {

    void onChangeScores(int scores);
    void onChangeLevel(int level);
    void onChangeNextFigure(Bitmap figure);

}
