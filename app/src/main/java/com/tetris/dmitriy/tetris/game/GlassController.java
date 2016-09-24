package com.tetris.dmitriy.tetris.game;


import com.tetris.dmitriy.tetris.game.figures.Figure;

/**
 * Created by Dmitriy on 20.09.2016.
 */

public interface GlassController {
    void onChangeScores(int scores);
    void onChangeLevel(int level);
    void onChangeNextFigure(Figure figure);
    void onClearLines(int yLine, int clearLinesCount);

    void onRefresh();
    void onGameOver();
}
