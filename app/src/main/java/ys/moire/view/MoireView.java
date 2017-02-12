package ys.moire.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ys.moire.BuildConfig;
import ys.moire.config.Config;
import ys.moire.type.Circles;
import ys.moire.type.CustomLines;
import ys.moire.type.Lines;
import ys.moire.type.Rectangles;

public class MoireView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "MoireView";

    /** draw type */
    public int mType;

    private static final int LINE_A = 0;
    private static final int LINE_B = 1;

    private int mLayoutWidth;
    private int mLayoutHeight;

    /** Line A */
    private Lines mALines;
    /** Line B */
    private Lines mBLines;
    /** Circle A */
    private Circles mACircles;
    /** Circle B */
    private Circles mBCircles;
    /** Rectangle A */
    private Rectangles mARectangles;
    /** Rectangle B */
    private Rectangles mBRectangles;
    /** CustomLines A */
    private CustomLines mACustomLines;
    /** CustomLines B */
    private CustomLines mBCustomLines;
    /** Background Color */
    private int colorOfBg = Color.WHITE;

    private boolean mIsPause = false;

    private boolean mIsOnBackground = false;

    /** Handler */
    private Handler mHandler;
    /** Thread */
    private final Runnable drawRunnable = new Runnable() {public void run() {drawFrame();}};

    /** Context */
    private Context mContext;

    public MoireView(Context context, int width, int height, int type) {
        super(context);
        mContext = context;
        getHolder().addCallback(this);
        mLayoutWidth = width;
        mLayoutHeight = height;
        mType = type;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        mHandler = new Handler();

        // Do on SurfaceChanged for height is changed.
//        linesLoad();
//        drawFrame();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "surfaceChanged height : " + height);
        }
        if(mLayoutHeight != height) {
            mLayoutWidth = width;
            mLayoutHeight = height;
            loadLines();
        }
        drawFrame();
    }

    public void drawFrame() {
        Canvas canvas = getHolder().lockCanvas();
        if(canvas != null){
            canvas.drawColor(colorOfBg);
            switch(mType) {
                case Config.TYPE_LINE:
                    // check line for loop.
                    mALines.checkOutOfRange(mLayoutWidth);
                    mBLines.checkOutOfRange(mLayoutWidth);
                    if(!mIsPause) {
                        mALines.move(LINE_A);
                        mBLines.move(LINE_B);
                    }
                    mALines.draw(canvas);
                    mBLines.draw(canvas);

                    break;
                case Config.TYPE_CIRCLE:
                    mACircles.checkOutOfRange(mLayoutWidth);
                    mBCircles.checkOutOfRange(mLayoutWidth);
                    if(!mIsPause) {
                        mACircles.move(LINE_A);
                        mBCircles.move(LINE_B);
                    }
                    mACircles.draw(canvas);
                    mBCircles.draw(canvas);
                    break;
                case Config.TYPE_RECT:
                    mARectangles.checkOutOfRange(mLayoutWidth);
                    mBRectangles.checkOutOfRange(mLayoutWidth);
                    if(!mIsPause) {
                        mARectangles.move(LINE_A);
                        mBRectangles.move(LINE_B);
                    }
                    mARectangles.draw(canvas);
                    mBRectangles.draw(canvas);
                    break;
                case Config.TYPE_ORIGINAL:
                    mACustomLines.checkOutOfRange(LINE_A, mLayoutWidth);
                    mBCustomLines.checkOutOfRange(LINE_B, mLayoutWidth);
                    if(!mIsPause) {
                        mACustomLines.move(LINE_A);
                        mBCustomLines.move(LINE_B);
                    }
                    mACustomLines.draw(canvas);
                    mBCustomLines.draw(canvas);
                    break;
                default:
                    break;
            }
            getHolder().unlockCanvasAndPost(canvas);
            mHandler.removeCallbacks(drawRunnable);
            if(!mIsPause && !mIsOnBackground) {
                mHandler.postDelayed(drawRunnable, 100);
            }
        }
    }

    public void captureCanvas(final Canvas canvas) {
        canvas.drawColor(colorOfBg);
        switch(mType) {
            case Config.TYPE_LINE:
                mALines.draw(canvas);
                mBLines.draw(canvas);
                break;
            case Config.TYPE_CIRCLE:
                mACircles.draw(canvas);
                mBCircles.draw(canvas);
                break;
            case Config.TYPE_RECT:
                mARectangles.draw(canvas);
                mBRectangles.draw(canvas);
                break;
            case Config.TYPE_ORIGINAL:
                mACustomLines.draw(canvas);
                mBCustomLines.draw(canvas);
                break;
            default:
                break;
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        mHandler.removeCallbacks(drawRunnable);
    }

    public void setBgColor(int color) {
        colorOfBg = color;
    }

    public void setMoireType(final int t) {
        mType = t;
    }

    /** load line status */
    public void loadLines() {
        switch(mType) {
            case Config.TYPE_LINE:
                mALines = new Lines();
                mALines.loadData(mContext, Config.LINE_A);
                mBLines = new Lines();
                mBLines.loadData(mContext, Config.LINE_B);
                mALines.init(LINE_A, mLayoutWidth, mLayoutHeight);
                mBLines.init(LINE_B, mLayoutWidth, mLayoutHeight);
                break;
            case Config.TYPE_CIRCLE:
                mACircles = new Circles();
                mACircles.loadData(mContext, Config.LINE_A);
                mBCircles = new Circles();
                mBCircles.loadData(mContext, Config.LINE_B);
                mACircles.init(LINE_A, mLayoutWidth, mLayoutHeight);
                mBCircles.init(LINE_B, mLayoutWidth, mLayoutHeight);
                break;
            case Config.TYPE_RECT:
                mARectangles = new Rectangles();
                mARectangles.loadData(mContext, Config.LINE_A);
                mBRectangles = new Rectangles();
                mBRectangles.loadData(mContext, Config.LINE_B);
                // TODO change dynamic
                float maxTopLength = mLayoutHeight/3f*2;
                float maxBottomLength = mLayoutHeight/3f*2;
                mARectangles.init(LINE_A, mLayoutWidth, mLayoutHeight, maxTopLength, maxBottomLength);
                mBRectangles.init(LINE_B, mLayoutWidth, mLayoutHeight, maxTopLength, maxBottomLength);
                break;
            case Config.TYPE_ORIGINAL:
                mACustomLines = new CustomLines();
                mACustomLines.loadData(mContext, Config.LINE_A);
                mBCustomLines = new CustomLines();
                mBCustomLines.loadData(mContext, Config.LINE_B);
                mACustomLines.init(LINE_A, mLayoutWidth, mLayoutHeight);
                mBCustomLines.init(LINE_B, mLayoutWidth, mLayoutHeight);
                break;
            default:
                break;
        }
    }

    /**
     * add touch value.
     */
    public void addTouchValue(final int which, final int valX, final int valY) {
        switch(mType) {
            case Config.TYPE_LINE:
                if(which == LINE_A) {
                    mALines.addTouchVal(valX, valY);
                }
                else if(which == LINE_B) {
                    mBLines.addTouchVal(valX, valY);
                }
                break;
            case Config.TYPE_CIRCLE:
                if(which == LINE_A) {
                    mACircles.addTouchVal(valX, valY);
                }
                else if(which == LINE_B) {
                    mBCircles.addTouchVal(valX, valY);
                }
                break;
            case Config.TYPE_RECT:
                if(which == LINE_A) {
                    mARectangles.addTouchVal(valX, valY);
                }
                else if(which == LINE_B) {
                    mBRectangles.addTouchVal(valX, valY);
                }
                break;
            case Config.TYPE_ORIGINAL:
                if(which == LINE_A) {
                    // add nothing for customLine
                }
                else if(which == LINE_B) {
                    // add nothing for customLine
                }
                break;
            default:
                break;
        }
    }

    public void drawOriginalLine(final int which, final int layoutWidth, final float valX, final float valY, final int moveCount) {
        if(which == LINE_A) {
            mACustomLines.drawOriginalLine(layoutWidth, valX, valY, moveCount);
        }
        else if(which == LINE_B) {
            mBCustomLines.drawOriginalLine(layoutWidth, valX, valY, moveCount);
        }
    }

    /**
     * set touch mode.
     * @param isOnTouch is on touch
     */
    public void setOnTouchMode(final int which, final boolean isOnTouch) {
        switch(mType) {
            case Config.TYPE_LINE:
                if(which == LINE_A) {
                    mALines.setOnTouchMode(isOnTouch);
                }
                else if(which == LINE_B) {
                    mBLines.setOnTouchMode(isOnTouch);
                }
                break;
            case Config.TYPE_CIRCLE:
                if(which == LINE_A) {
                    mACircles.setOnTouchMode(isOnTouch);
                }
                else if(which == LINE_B) {
                    mBCircles.setOnTouchMode(isOnTouch);
                }
                break;
            case Config.TYPE_RECT:
                if(which == LINE_A) {
                    mARectangles.setOnTouchMode(isOnTouch);
                }
                else if(which == LINE_B) {
                    mBRectangles.setOnTouchMode(isOnTouch);
                }
                break;
            case Config.TYPE_ORIGINAL:
                if(which == LINE_A) {
                    mACustomLines.setOnTouchMode(isOnTouch);
                }
                else if(which == LINE_B) {
                    mBCustomLines.setOnTouchMode(isOnTouch);
                }
                break;
            default:
                break;
        }
    }

    /**
     * return is pause.
     * @return boolean is on pause
     */
    public boolean isPause() {
        return mIsPause;
    }

    /**
     * pause.
     * @param pause
     */
    public void pause(final boolean pause) {
        mIsPause = pause;
        if(!pause) {
            if(mHandler != null) {
                mHandler.postDelayed(drawRunnable, 100);
            }
        }
    }

    /**
     * is on Background.
     * @param onBack is on Background
     */
    public void setOnBackground(final boolean onBack) {
        mIsOnBackground = onBack;
        if(!onBack) {
            if(mHandler != null) {
                mHandler.postDelayed(drawRunnable, 100);
            }
        }
    }

}
