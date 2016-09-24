package com.tetris.dmitriy.tetris;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.tetris.dmitriy.tetris.game.Events;
import com.tetris.dmitriy.tetris.game.GlassController;
import com.tetris.dmitriy.tetris.game.GlassModel;
import com.tetris.dmitriy.tetris.game.GlassView;
import com.tetris.dmitriy.tetris.game.figures.Figure;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;


public class GameActivity extends Activity {

    /** UI elements */
    private TextView mScoreTxt;
    private TextView mLevelTxt;
    private ImageView mNextFigureImg;
    private Button mPauseBtn;

    private FrameLayout mMessageBoxLyt;
    private TextView mMessageTxt;
    private TextView mAddInfoTxt;

    private View mBtnLeft;
    private View mBtnRight;
    private View mBtnDown;
    private View mBtnFallDown;
    private View mBtnRotate;

    private Switch mMusicSwitch;
    private Switch mSoundSwitch;

    private GlassView mGlassView;

    /** Logic elements */
    private GlassModel mGlassModel;

    private final int[] mMusicFiles = {R.raw.music1, R.raw.music0, R.raw.music2, R.raw.music3};
    private final int[] sounds = {R.raw.fall, R.raw.rotate, R.raw.clear_line};
    private int mMusicIndex = 0;

    private MediaPlayer mMusicPlayer;
    private MediaPlayer mSoundPlayer;

    private boolean mMusic = true;
    private boolean mSound = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_game);

        mScoreTxt = (TextView) findViewById(R.id.ActivityGame_ScoreTxt);
        mLevelTxt = (TextView) findViewById(R.id.ActivityGame_LevelTxt);
        mNextFigureImg = (ImageView) findViewById(R.id.ActivityGame_NextFigureImg);
        mPauseBtn = (Button) findViewById(R.id.ActivityGame_PauseBtn);
        mPauseBtn.setOnClickListener(onPauseBtnClick);

        mMessageBoxLyt = (FrameLayout) findViewById(R.id.ActivityGame_MessageBox);
        mMessageTxt = (TextView) findViewById(R.id.ActivityGame_MessageTxt);
        mAddInfoTxt = (TextView) findViewById(R.id.ActivityGame_additionalInfoTxt);

        mBtnLeft = findViewById(R.id.ActivityGame_LeftBtn);
        mBtnLeft.setOnClickListener(onLeftBtnClick);
        mBtnRight = findViewById(R.id.ActivityGame_RightBtn);
        mBtnRight.setOnClickListener(onRightBtnClick);
        mBtnDown = findViewById(R.id.ActivityGame_DownBtn);
        mBtnDown.setOnClickListener(onDownBtnClick);
        mBtnFallDown = findViewById(R.id.ActivityGame_FallDownBtn);
        mBtnFallDown.setOnClickListener(onFallDownBtnClick);
        mBtnRotate = findViewById(R.id.ActivityGame_RotateBtn);
        mBtnRotate.setOnClickListener(onRotateBtnClick);

        mMusicSwitch = (Switch) findViewById(R.id.ActivityGame_MusicSwitch);
        mMusicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mMusic = !mMusic;
                playMusic();
            }
        });
        mSoundSwitch = (Switch) findViewById(R.id.ActivityGame_SoundSwitch);
        mSoundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mSound = !mSound;
            }
        });

        mGlassModel = new GlassModel(mGlassController);
        mGlassModel.onStart();
        playMusic();

        mGlassView = (GlassView) findViewById(R.id.ActivityGame_GlassView);

        setControlButtons(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mGlassModel.onEndGame();
    }

    private void setControlButtons(boolean enabled) {
        mBtnLeft.setEnabled(enabled);
        mBtnRight.setEnabled(enabled);
        mBtnDown.setEnabled(enabled);
        mBtnFallDown.setEnabled(enabled);
        mBtnRotate.setEnabled(enabled);
        mPauseBtn.setEnabled(enabled);
    }

    private View.OnClickListener onPauseBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!mGlassModel.isPause()) {
                mGlassModel.onPause();
                mPauseBtn.setText(R.string.game_activity_resume);
            } else {
                mGlassModel.onResume();
                mPauseBtn.setText(R.string.game_activity_pause);
            }
        }
    };

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
            playSound(0);
        }
    };

    private View.OnClickListener onRotateBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mGlassModel.onAction(Events.ROTATE);
            playSound(1);
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
            playSound(2);
        }

        @Override
        public void onRefresh() {
            mGlassView.invalidate();
        }

        @Override
        public void onGameOver() {
            mMessageBoxLyt.setVisibility(View.VISIBLE);
            mMessageTxt.setText(R.string.game_message_gameOver);
            mAddInfoTxt.setText(null);

            setControlButtons(false);
            releaseMediaPlayer(mMusicPlayer);
        }
    };

    private void playMusic() {
        releaseMediaPlayer(mMusicPlayer);
        if (!mMusic) {
            return;
        }
        mMusicPlayer = MediaPlayer.create(GameActivity.this, mMusicFiles[mMusicIndex]);
        mMusicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMusicPlayer.start();
        mMusicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (mMusicIndex == mMusicFiles.length) {
                    mMusicIndex = 0;
                } else {
                    mMusicIndex++;
                }
                playMusic();
            }
        });
    }

    private void playSound(int type) {
        releaseMediaPlayer(mSoundPlayer);
        if (!mSound) {
            return;
        }
        mSoundPlayer = MediaPlayer.create(GameActivity.this, sounds[type]);
        mSoundPlayer.start();
    }

    private void releaseMediaPlayer(MediaPlayer mp) {
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }

}
