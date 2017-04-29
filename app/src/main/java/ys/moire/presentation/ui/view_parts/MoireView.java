package ys.moire.presentation.ui.view_parts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ys.moire.BuildConfig;
import ys.moire.common.config.Config;
import ys.moire.presentation.ui.view_parts.type.BaseTypes;
import ys.moire.presentation.ui.view_parts.type.Circles;
import ys.moire.presentation.ui.view_parts.type.CustomLines;
import ys.moire.presentation.ui.view_parts.type.Lines;
import ys.moire.presentation.ui.view_parts.type.Rectangles;

public class MoireView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "MoireView";

    /** draw type */
    public int mType;

    private static final int LINE_A = 0;
    private static final int LINE_B = 1;

    private int layoutWidth;
    private int layoutHeight;

    /** Line A */
    private Lines aLines;
    /** Line B */
    private Lines bLines;
    /** Circle A */
    private Circles aCircles;
    /** Circle B */
    private Circles bCircles;
    /** Rectangle A */
    private Rectangles aRectangles;
    /** Rectangle B */
    private Rectangles bRectangles;
    /** CustomLines A */
    private CustomLines aCustomLines;
    /** CustomLines B */
    private CustomLines bCustomLines;
    /** Background Color */
    private int colorOfBg = Color.WHITE;

    private boolean isPause = false;

    private boolean isOnBackground = false;

    /** Handler */
    private Handler handler;
    /** Thread */
    private final Runnable drawRunnable = new Runnable() {public void run() {drawFrame();}};

    /** Context */
    private Context context;

    public interface OnSurfaceChange {
        void onSurfaceChange();
    }

    private OnSurfaceChange listener;

    public MoireView(Context context, int width, int height, int type) {
        super(context);
        this.context = context;
        getHolder().addCallback(this);
        layoutWidth = width;
        layoutHeight = height;
        mType = type;

        if (context instanceof OnSurfaceChange) {
            listener = (OnSurfaceChange)context;
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        handler = new Handler();

        // Do on SurfaceChanged for height is changed.
//        linesLoad();
//        drawFrame();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "surfaceChanged height : " + height);
        }
        if(layoutHeight != height) {
            layoutWidth = width;
            layoutHeight = height;

            if (listener != null) {
                listener.onSurfaceChange();
            }
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
                    aLines.checkOutOfRange(layoutWidth);
                    bLines.checkOutOfRange(layoutWidth);
                    if(!isPause) {
                        aLines.move(LINE_A);
                        bLines.move(LINE_B);
                    }
                    aLines.draw(canvas);
                    bLines.draw(canvas);

                    break;
                case Config.TYPE_CIRCLE:
                    aCircles.checkOutOfRange(layoutWidth);
                    bCircles.checkOutOfRange(layoutWidth);
                    if(!isPause) {
                        aCircles.move(LINE_A);
                        bCircles.move(LINE_B);
                    }
                    aCircles.draw(canvas);
                    bCircles.draw(canvas);
                    break;
                case Config.TYPE_RECT:
                    aRectangles.checkOutOfRange(layoutWidth);
                    bRectangles.checkOutOfRange(layoutWidth);
                    if(!isPause) {
                        aRectangles.move(LINE_A);
                        bRectangles.move(LINE_B);
                    }
                    aRectangles.draw(canvas);
                    bRectangles.draw(canvas);
                    break;
                case Config.TYPE_ORIGINAL:
                    aCustomLines.checkOutOfRange(LINE_A, layoutWidth);
                    bCustomLines.checkOutOfRange(LINE_B, layoutWidth);
                    if(!isPause) {
                        aCustomLines.move(LINE_A);
                        bCustomLines.move(LINE_B);
                    }
                    aCustomLines.draw(canvas);
                    bCustomLines.draw(canvas);
                    break;
                default:
                    break;
            }
            getHolder().unlockCanvasAndPost(canvas);
            handler.removeCallbacks(drawRunnable);
            if(!isPause && !isOnBackground) {
                handler.postDelayed(drawRunnable, 100);
            }
        }
    }

    public void captureCanvas(final Canvas canvas) {
        canvas.drawColor(colorOfBg);
        switch(mType) {
            case Config.TYPE_LINE:
                aLines.draw(canvas);
                bLines.draw(canvas);
                break;
            case Config.TYPE_CIRCLE:
                aCircles.draw(canvas);
                bCircles.draw(canvas);
                break;
            case Config.TYPE_RECT:
                aRectangles.draw(canvas);
                bRectangles.draw(canvas);
                break;
            case Config.TYPE_ORIGINAL:
                aCustomLines.draw(canvas);
                bCustomLines.draw(canvas);
                break;
            default:
                break;
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        handler.removeCallbacks(drawRunnable);
    }

    public void setBgColor(int color) {
        colorOfBg = color;
    }

    public void setMoireType(final int t) {
        mType = t;
    }

    /** load line status */
    public void loadLines(final BaseTypes aTypes, final BaseTypes bTypes) {
        switch(mType) {
            case Config.TYPE_LINE:
                aLines = new Lines();
                aLines.loadData(aTypes);
                bLines = new Lines();
                bLines.loadData(bTypes);
                aLines.init(LINE_A, layoutWidth, layoutHeight);
                bLines.init(LINE_B, layoutWidth, layoutHeight);
                break;
            case Config.TYPE_CIRCLE:
                aCircles = new Circles();
                aCircles.loadData(aTypes);
                bCircles = new Circles();
                bCircles.loadData(bTypes);
                aCircles.init(LINE_A, layoutWidth, layoutHeight);
                bCircles.init(LINE_B, layoutWidth, layoutHeight);
                break;
            case Config.TYPE_RECT:
                aRectangles = new Rectangles();
                aRectangles.loadData(aTypes);
                bRectangles = new Rectangles();
                bRectangles.loadData(bTypes);
                // TODO change dynamic
                float maxTopLength = layoutHeight /3f*2;
                float maxBottomLength = layoutHeight /3f*2;
                aRectangles.init(LINE_A, layoutWidth, layoutHeight, maxTopLength, maxBottomLength);
                bRectangles.init(LINE_B, layoutWidth, layoutHeight, maxTopLength, maxBottomLength);
                break;
            case Config.TYPE_ORIGINAL:
                aCustomLines = new CustomLines();
                aCustomLines.loadData(aTypes);
                bCustomLines = new CustomLines();
                bCustomLines.loadData(bTypes);
                aCustomLines.init(LINE_A, layoutWidth, layoutHeight);
                bCustomLines.init(LINE_B, layoutWidth, layoutHeight);
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
                    aLines.addTouchVal(valX, valY);
                }
                else if(which == LINE_B) {
                    bLines.addTouchVal(valX, valY);
                }
                break;
            case Config.TYPE_CIRCLE:
                if(which == LINE_A) {
                    aCircles.addTouchVal(valX, valY);
                }
                else if(which == LINE_B) {
                    bCircles.addTouchVal(valX, valY);
                }
                break;
            case Config.TYPE_RECT:
                if(which == LINE_A) {
                    aRectangles.addTouchVal(valX, valY);
                }
                else if(which == LINE_B) {
                    bRectangles.addTouchVal(valX, valY);
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

    public void drawOriginalLine(final int which, final float valX, final float valY, final int moveCount) {
        if(which == LINE_A) {
            aCustomLines.drawOriginalLine(layoutWidth, valX, valY, moveCount);
        }
        else if(which == LINE_B) {
            bCustomLines.drawOriginalLine(layoutWidth, valX, valY, moveCount);
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
                    aLines.setOnTouchMode(isOnTouch);
                }
                else if(which == LINE_B) {
                    bLines.setOnTouchMode(isOnTouch);
                }
                break;
            case Config.TYPE_CIRCLE:
                if(which == LINE_A) {
                    aCircles.setOnTouchMode(isOnTouch);
                }
                else if(which == LINE_B) {
                    bCircles.setOnTouchMode(isOnTouch);
                }
                break;
            case Config.TYPE_RECT:
                if(which == LINE_A) {
                    aRectangles.setOnTouchMode(isOnTouch);
                }
                else if(which == LINE_B) {
                    bRectangles.setOnTouchMode(isOnTouch);
                }
                break;
            case Config.TYPE_ORIGINAL:
                if(which == LINE_A) {
                    aCustomLines.setOnTouchMode(isOnTouch);
                }
                else if(which == LINE_B) {
                    bCustomLines.setOnTouchMode(isOnTouch);
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
        return isPause;
    }

    /**
     * pause.
     * @param pause
     */
    public void pause(final boolean pause) {
        isPause = pause;
        if(!pause) {
            if(handler != null) {
                handler.postDelayed(drawRunnable, 100);
            }
        }
    }

    /**
     * is on Background.
     * @param onBack is on Background
     */
    public void setOnBackground(final boolean onBack) {
        isOnBackground = onBack;
        if(!onBack) {
            if(handler != null) {
                handler.postDelayed(drawRunnable, 100);
            }
        }
    }

}
