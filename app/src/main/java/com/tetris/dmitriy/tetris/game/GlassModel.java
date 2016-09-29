package com.tetris.dmitriy.tetris.game;

import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Pair;

import com.tetris.dmitriy.tetris.game.figures.Figure;
import com.tetris.dmitriy.tetris.records.Record;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Dmitriy on 15.09.2016.
 */
public class GlassModel
        implements GameEvents {

    private static final int LINE_PRICE = 100; // scores for one line collection
    private static final int LEVEL_UP_FACTOR = 1000;

    private Timer mTimer;
    private int mTimerPeriod = 1000; // 1 sec
    private GlassController mGlassController;

    private Figure.FigureTypes mNextFigureType;
    private Point mNextCoordinates;
    private Events mEvent;

    private boolean mPause = false;
    private boolean mTop = false;
    private boolean mBottom = false;
    private boolean mGame = false;

    private Date mDateStartGame;
    private Date mDateFinishGame;

    private Figure mCurrentFigure;

    /** Scores */
    private int mScores = 0;
    private int mLevel = 0;

    private PlayField mPlayField;

    public GlassModel(GlassController glassController) {
        mPlayField = new PlayField();
        mGlassController = glassController;
        mNextFigureType = Figure.getRandomType();
    }

    /** method to refresh model after GameEvents */
    private void invalidate() {
        final Point currentCoordinates = new Point(mCurrentFigure.getX(), mCurrentFigure.getY());
        clearCoordinates(currentCoordinates);

        setFigure();

        mPlayField.print();
        handleMessage(MessageTypes.REFRESH, null);
        Log.d("PlayField", "end of line");
    }

    private void setFigure() {
        mBottom = false;
        final Collision state = hasCollision();
        if (state == Collision.EMPTY) {
            fillPlayField(mNextCoordinates);
            changeCoordinates();
        } else if (state == Collision.BORDER || state == Collision.FIGURE_BORDER) {
            mNextCoordinates.x = mCurrentFigure.getX();
            fillPlayField(new Point(mCurrentFigure.getX(), mCurrentFigure.getY()));
            changeCoordinates();
        } else {
            mBottom = true;
            if (mCurrentFigure.getY() == 0) {
                mTop = true;
            }
            fixedFigure();
            clearLines();
        }
        mTop = false;
    }

    private Collision hasCollision() {
        Point checkPoint = new Point();
        int[][] f = mCurrentFigure.getFigure();
        for (int i = 0; i < Figure.HEIGHT; i++) {
            for (int j = 0; j < Figure.WIDTH; j++) {
                checkPoint.x = mNextCoordinates.x + j;
                checkPoint.y = mNextCoordinates.y + i;
                if (f[i][j] == 1) {
                    if (mPlayField.getState(checkPoint) == - 1
                            && (mEvent == Events.MOVE_LEFT || mEvent == Events.MOVE_RIGHT)) {
                        return Collision.BORDER;
                    } else if (mPlayField.getState(checkPoint) == - 1
                            && (mEvent == Events.MOVE_DOWN)) {
                        return Collision.BOTTOM;
                    } else if (mPlayField.getState(checkPoint) == 1
                            && (mEvent == Events.MOVE_LEFT || mEvent == Events.MOVE_RIGHT)) {
                        return Collision.FIGURE_BORDER;
                    } else if (mPlayField.getState(checkPoint) == 1
                            && (mEvent == Events.MOVE_DOWN)) {
                        return Collision.FIGURE_BOTTOM;
                    }
                }
            }
        }
        return Collision.EMPTY;
    }

    private boolean canRotate() {
        clearCoordinates(new Point(mCurrentFigure.getX(), mCurrentFigure.getY()));
        return !isRotateCollision();
    }

    private boolean isRotateCollision() {
        final int fOrientation = mCurrentFigure.getCurrentOrientation();
        mCurrentFigure.rotate();

        Point checkPoint = new Point();
        int[][] f = mCurrentFigure.getFigure();
        for (int i = 0; i < Figure.HEIGHT; i++) {
            for (int j = 0; j < Figure.WIDTH; j++) {
                checkPoint.x = mNextCoordinates.x + j;
                checkPoint.y = mNextCoordinates.y + i;
                if (f[i][j] == 1) {
                    if (mPlayField.getState(checkPoint) == 1
                            || mPlayField.getState(checkPoint) == -1) {
                        mCurrentFigure.setOrientation(fOrientation);
                        return true;
                    }
                }
            }
        }
        mCurrentFigure.setOrientation(fOrientation);
        return false;
    }

    private void clearCoordinates(Point start) {
        fill(mCurrentFigure, start, 0);
    }

    private void fillPlayField(Point start) {
        fill(mCurrentFigure, start, 1);
    }

    private void fill(Figure figure, Point start, int state) {
        Point checkPoint = new Point();
        int[][] f = figure.getFigure();
        for (int i = 0; i < Figure.HEIGHT; i++) {
            for (int j = 0; j < Figure.WIDTH; j++) {
                checkPoint.x = start.x + j;
                checkPoint.y = start.y + i;
                if (f[i][j] == 1) {
                    mPlayField.setState(checkPoint, state);
                }
            }
        }
    }

    private void nextFigure() {
        if (mTop) {
            onEndGame();
        }
        mCurrentFigure = Figure.createFigure(mNextFigureType);
        mNextFigureType = Figure.getRandomType();
        mNextCoordinates = new Point(mCurrentFigure.getX(), mCurrentFigure.getY());

        handleMessage(MessageTypes.NEXT_FIGURE, Figure.createFigure(mNextFigureType));
    }

    private void changeCoordinates() {
        mCurrentFigure.setCurrentCords(mNextCoordinates);
    }

    private void fixedFigure() {
        final Point currentCoordinates = new Point(mCurrentFigure.getX(), mCurrentFigure.getY());
        fillPlayField(currentCoordinates);
        nextFigure();
    }

    private void clearLines() {
        Integer[] fullLines = getFullLines();
        if (fullLines.length > 0) {
            for (Integer line : fullLines) {
                mPlayField.clearLine(line);
            }
            final int yLine = fullLines[fullLines.length - 1];
            shiftToBottom(yLine, fullLines.length);
            calculateScores(fullLines.length);

            handleMessage(MessageTypes.CLEAR_LINE, new Pair<>(yLine, fullLines.length));
        }
    }

    private void shiftToBottom(int yStartLine, int clearLineCount) {
        for (int i = yStartLine - 1; i > 0; i--) {
            int emptyFieldsCount = 0;
            for (int j = 1; j < PlayField.WIDTH + 1; j++) {
                int fieldValue = mPlayField.getState(new Point(j, i));
                if (fieldValue == 0) {
                    emptyFieldsCount++;
                }
                mPlayField.setState(new Point(j, i), 0);
                mPlayField.setState(new Point(j, i + clearLineCount), fieldValue);
            }
            if (emptyFieldsCount == PlayField.WIDTH) { // empty line detected
                return;
            }
        }
    }

    private Integer[] getFullLines() {
        ArrayList<Integer> lst = new ArrayList<>();
        for (int i = PlayField.HEIGHT - 1; i > 0; i--) {
            int fullFieldsCount = 0;
            int emptyFieldsCount = 0;
            for (int j = 1; j < PlayField.WIDTH + 1; j++) {
                if (mPlayField.getState(new Point(j, i)) == 1) {
                    fullFieldsCount++;
                } else {
                    emptyFieldsCount++;
                }
            }
            if (emptyFieldsCount == PlayField.WIDTH) { // empty line
                return lst.toArray(new Integer[lst.size()]);
            }
            if (fullFieldsCount == PlayField.WIDTH) {
                lst.add(i);
            }
        }
        return lst.toArray(new Integer[lst.size()]);
    }

    private void calculateScores(int clearLinesCount) {
        mScores += clearLinesCount * LINE_PRICE;
        int temp = mScores / LEVEL_UP_FACTOR;
        if (temp > mLevel) {
            mLevel = temp;
            mTimerPeriod -= LINE_PRICE;
            if (mTimerPeriod <= 0) {
                mTimerPeriod = LINE_PRICE;
            }
            createTimer();
            handleMessage(MessageTypes.LEVEL, mLevel);
        }
        handleMessage(MessageTypes.SCORES, mScores);
    }

    private void handleMessage(int msgType, Object data) {
        Message msg = new Message();
        msg.what = msgType;
        msg.obj = data;
        msg.setTarget(mHandler);
        msg.sendToTarget();
    }

    private void createTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
        }
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                onAction(Events.MOVE_DOWN);
            }
        }, 0, mTimerPeriod);
    }

    public boolean isPause() {
        return mPause;
    }

    public synchronized Figure getCurrentFigure() {
        return mCurrentFigure;
    }

    /** GameEvents interface implementation */
    @Override
    public void onStart() {
        mGame = true;
        nextFigure();
        createTimer();
        mDateStartGame = new Date();
    }

    @Override
    public void onPause() {
        mPause = true;
    }

    @Override
    public void onResume() {
        mPause = false;
    }

    @Override
    public void onEndGame() {
        if (mGame) {
            mTimer.cancel();
            mDateFinishGame = new Date();
            final Record newRecord = new Record(0, mLevel, mScores, mDateFinishGame.getTime() - mDateStartGame.getTime());
            handleMessage(MessageTypes.GAME_OVER, newRecord);
            mGame = false;
            mDateStartGame = null;
        }
    }

    /** all GameEvents from different threads must be synchronized */
    @Override
    public synchronized void onAction(Events event) {
        mEvent = event;
        switch (event) {
            case MOVE_LEFT:
                mFigureEvents.onMoveLeft();
                break;
            case MOVE_RIGHT:
                mFigureEvents.onMoveRight();
                break;
            case MOVE_DOWN:
                mFigureEvents.onMoveDown();
                break;
            case ROTATE:
                mFigureEvents.onRotate();
                break;
            case FALL_DOWN:
                mFigureEvents.onFallDown();
                break;
        }
        invalidate();
    }

   /** FigureEvents interface implementation */
    private FigureEvents mFigureEvents = new FigureEvents() {
        @Override
        public void onMoveLeft() {
            mNextCoordinates.x--;
        }

        @Override
        public void onMoveRight() {
            mNextCoordinates.x++;
        }

        @Override
        public void onMoveDown() {
            mNextCoordinates.y++;
        }

        @Override
        public void onRotate() {
            if (canRotate()) {
                mCurrentFigure.rotate();
            }
        }

        @Override
        public void onFallDown() {
            while (!mBottom) {
                onAction(Events.MOVE_DOWN);
            }
        }
    };

    /** Handler message in UI thread, callbacks GameController interface methods */
    private Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case MessageTypes.SCORES:
                    mGlassController.onChangeScores((Integer) message.obj);
                    break;
                case MessageTypes.LEVEL:
                    mGlassController.onChangeLevel((Integer) message.obj);
                    break;
                case MessageTypes.NEXT_FIGURE:
                    mGlassController.onChangeNextFigure((Figure) message.obj);
                    break;
                case MessageTypes.REFRESH:
                    mGlassController.onRefresh();
                    break;
                case MessageTypes.CLEAR_LINE:
                    int yLine = (Integer)((Pair)message.obj).first;
                    int linesCount = (Integer)((Pair)message.obj).second;
                    mGlassController.onClearLines(yLine, linesCount);
                    break;
                case MessageTypes.GAME_OVER:
                    Record newRecord = (Record) message.obj;
                    mGlassController.onGameOver(newRecord);
                    break;
            }
            return false;
        }
    });
}

enum Collision {
    BORDER,
    BOTTOM,
    FIGURE_BORDER,
    FIGURE_BOTTOM,
    EMPTY
}

interface FigureEvents {
    void onMoveLeft();
    void onMoveRight();
    void onMoveDown();
    void onRotate();
    void onFallDown();
}

interface GameEvents {
    void onStart();
    void onPause();
    void onResume();
    void onEndGame();

    void onAction(Events event);
}