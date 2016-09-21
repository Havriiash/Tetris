package com.tetris.dmitriy.tetris;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tetris.dmitriy.tetris.game.Events;
import com.tetris.dmitriy.tetris.game.GlassController;
import com.tetris.dmitriy.tetris.game.GlassModel;
import com.tetris.dmitriy.tetris.game.GlassView;
import com.tetris.dmitriy.tetris.game.figures.Figure;


public class GameActivity extends AppCompatActivity {

    /** UI elements */
    private TextView mScoreTxt;
    private TextView mLevelTxt;
    private ImageView mNextFigureImg;

    private GlassView mGlassView;

    /** Logic elements */
    private GlassModel mGlassModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mScoreTxt = (TextView) findViewById(R.id.ActivityGame_ScoreTxt);
        mLevelTxt = (TextView) findViewById(R.id.ActivityGame_LevelTxt);
        mNextFigureImg = (ImageView) findViewById(R.id.ActivityGame_NextFigureImg);

        findViewById(R.id.ActivityGame_LeftBtn).setOnClickListener(onLeftBtnClick);
        findViewById(R.id.ActivityGame_RightBtn).setOnClickListener(onRightBtnClick);
        findViewById(R.id.ActivityGame_DownBtn).setOnClickListener(onDownBtnClick);
        findViewById(R.id.ActivityGame_FallDownBtn).setOnClickListener(onFallDownBtnClick);
        findViewById(R.id.ActivityGame_RotateBtn).setOnClickListener(onRotateBtnClick);

        mGlassModel = new GlassModel(mGlassController);
        mGlassModel.onStart();

        mGlassView = (GlassView) findViewById(R.id.ActivityGame_GlassView);
    }

    private View.OnClickListener onLeftBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mGlassModel.onAction(Events.MOVE_LEFT);
        }
    };

    private View.OnClickListener onRightBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mGlassModel.onAction(Events.MOVE_RIGHT);
        }
    };

    private View.OnClickListener onDownBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mGlassModel.onAction(Events.MOVE_DOWN);
        }
    };

    private View.OnClickListener onFallDownBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mGlassModel.onAction(Events.FALL_DOWN);
        }
    };

    private View.OnClickListener onRotateBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mGlassModel.onAction(Events.ROTATE);
        }
    };

    /** GlassController interface implementation */
    private GlassController mGlassController = new GlassController() {

        @Override
        public void onChangeScores(int scores) {
            mScoreTxt.setText(Integer.toString(scores));
        }

        @Override
        public void onChangeLevel(int level) {
            mLevelTxt.setText(Integer.toString(level));
        }

        @Override
        public void onChangeNextFigure(Figure figure) {
            mNextFigureImg.setImageBitmap(figure.draw(mNextFigureImg.getWidth(), mNextFigureImg.getHeight()));
            mGlassView.setCurrentFigure(mGlassModel.currentFigure);
        }

        @Override
        public void onClearLines(int yLine,int clearLinesCount) {
            mGlassView.clearLines(yLine, clearLinesCount);
        }

        @Override
        public void onRefresh() {
            mGlassView.invalidate();
        }

    };

}
