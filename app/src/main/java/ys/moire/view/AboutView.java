package ys.moire.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ys.moire.R;
import ys.moire.sensor.SensorEL;

/**
 * about moire view class
 * description screen
 */
public class AboutView extends SurfaceView implements SurfaceHolder.Callback {

    /** move value */
    private static int VAL = 8;
    private float mLayoutWidth;
    private float mLayoutHeight;
    /** char image bitmap */
    private Bitmap[] mBmp = new Bitmap[145];
    /** char image point */
    private PointF[] mBmpPoint = new PointF[145];
    /** paint */
    private Paint mPaint;
    /** SensorEventListener */
    private SensorEL mSensorEL;

    public AboutView(final Context context, final SensorEL sensorEL, final float width, final float height) {
        super(context);
        getHolder().addCallback(this);
        mSensorEL = sensorEL;
        mLayoutWidth = width;
        mLayoutHeight = height;
    }
    public void drawFrame() {
        // get canvas
        Canvas canvas = getHolder().lockCanvas();
        if(canvas != null){
            // draw canvas
            canvas.drawColor(Color.WHITE);
            for(int i=0; i< mBmp.length; i++) {
                float r = (float)Math.random()*5;
                float abDx = VAL/2f*(r/5);
                mBmpPoint[i].set(mBmpPoint[i].x- mSensorEL.mSensorX *abDx, mBmpPoint[i].y+mSensorEL.mSensorY *abDx);
                checkInFrame(i, abDx);
                canvas.drawBitmap(mBmp[i], mBmpPoint[i].x, mBmpPoint[i].y, mPaint);
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    // called when SurfaceView is created.
    public void surfaceCreated(SurfaceHolder holder) {

        TypedArray tArray = getResources().obtainTypedArray(R.array.chara_items);
        for (int i=0; i<tArray.length(); i++) {
            int resource = tArray.getResourceId(i, 0);
            mBmp[i] = BitmapFactory.decodeResource(getResources(), resource);
        }
        // init char point
        // line1
        for (int i=0; i<15; i++) {
            if(i == 0) {
                mBmpPoint[i] = new PointF(mLayoutWidth /17*(i+1)+20, mLayoutHeight /10- mBmp[i].getHeight());
            }
            else if(i == 2) {
                mBmpPoint[i] = new PointF(mLayoutWidth /17*(i+1)-10, mLayoutHeight /10- mBmp[i].getHeight());
            }
            else if(i == 4) {
                mBmpPoint[i] = new PointF(mLayoutWidth /17*(i+1)+10, mLayoutHeight /10- mBmp[i].getHeight());
            }
            else if(i == 8) {
                mBmpPoint[i] = new PointF(mLayoutWidth /17*(i+1)+5, mLayoutHeight /10- mBmp[i].getHeight()*4/5);
            }
            else {
                mBmpPoint[i] = new PointF(mLayoutWidth /17*(i+1)+5, mLayoutHeight /10- mBmp[i].getHeight());
            }
        }
        // line2
        for (int i=15; i<33; i++) {
            if(i == 15 || i == 21) {
                mBmpPoint[i] = new PointF(mLayoutWidth /19*(i-15)+10, mLayoutHeight *2/10- mBmp[i].getHeight());
            }
            else {
                mBmpPoint[i] = new PointF(mLayoutWidth /19*(i-15)+5, mLayoutHeight *2/10- mBmp[i].getHeight());
            }
        }
        // line3
        for (int i=33; i<49; i++) {
            if(i == 33) {
                mBmpPoint[i] = new PointF(mLayoutWidth /18*(i-33)+5, mLayoutHeight *3/10- mBmp[i].getHeight()*4/5);
            }
            else {
                mBmpPoint[i] = new PointF(mLayoutWidth /18*(i-33)+5, mLayoutHeight *3/10- mBmp[i].getHeight());
            }
        }
        // line4
        for (int i=49; i<65; i++) {
            if(i == 57) {
                mBmpPoint[i] = new PointF(mLayoutWidth /16*(i-49)+15, mLayoutHeight *4/10- mBmp[i].getHeight()*4/5);
            }
            else if(i == 49 || i == 58) {
                mBmpPoint[i] = new PointF(mLayoutWidth /16*(i-49)+15, mLayoutHeight *4/10- mBmp[i].getHeight());
            }
            else if(i == 61) {
                mBmpPoint[i] = new PointF(mLayoutWidth /16*(i-49), mLayoutHeight *4/10- mBmp[i].getHeight());
            }
            else {
                mBmpPoint[i] = new PointF(mLayoutWidth /16*(i-49)+5, mLayoutHeight *4/10- mBmp[i].getHeight());
            }
        }
        // line5
        for (int i=65; i<78; i++) {
            if(i == 65) {
                mBmpPoint[i] = new PointF(mLayoutWidth /15*(i-65)+10, mLayoutHeight *5/10- mBmp[i].getHeight());
            }
            else if(i == 69) {
                mBmpPoint[i] = new PointF(mLayoutWidth /15*(i-65)+5, mLayoutHeight *5/10- mBmp[i].getHeight()*4/5);
            }
            else {
                mBmpPoint[i] = new PointF(mLayoutWidth /15*(i-65)+5, mLayoutHeight *5/10- mBmp[i].getHeight());
            }
        }
        // line6
        for (int i=78; i<99; i++) {
            if(i == 95) {
                mBmpPoint[i] = new PointF(mLayoutWidth /21*(i-78)+5, mLayoutHeight *6/10- mBmp[i].getHeight()*4/5);
            }
            else {
                mBmpPoint[i] = new PointF(mLayoutWidth /21*(i-78)+5, mLayoutHeight *6/10- mBmp[i].getHeight());
            }
        }
        // line7
        for (int i=99; i<116; i++) {
            if(i == 102) {
                mBmpPoint[i] = new PointF(mLayoutWidth /20*(i-98)-15, mLayoutHeight *7/10- mBmp[i].getHeight());
            }
            else if(i == 110) {
                mBmpPoint[i] = new PointF(mLayoutWidth /20*(i-98)+5, mLayoutHeight *7/10- mBmp[i].getHeight()*4/5);
            }
            else {
                mBmpPoint[i] = new PointF(mLayoutWidth /20*(i-98)+5, mLayoutHeight *7/10- mBmp[i].getHeight());
            }
        }
        // line8
        for (int i=116; i<134; i++) {
            if(i == 116 || i == 117 || i == 118 || i == 126) {
                mBmpPoint[i] = new PointF(mLayoutWidth /20*(i-116)+10, mLayoutHeight *8/10- mBmp[i].getHeight());
            }
            else if(i == 119 || i == 123) {
                mBmpPoint[i] = new PointF(mLayoutWidth /20*(i-116)+5, mLayoutHeight *8/10- mBmp[i].getHeight()*4/5);
            }
            else {
                mBmpPoint[i] = new PointF(mLayoutWidth /20*(i-116)+5, mLayoutHeight *8/10- mBmp[i].getHeight());
            }
        }
        // line9
        for (int i=134; i<145; i++) {
            if(i == 134) {
                mBmpPoint[i] = new PointF(mLayoutWidth /15*(i-130)-5, mLayoutHeight *9/10- mBmp[i].getHeight());
            }
            else if(i == 139 || i == 140) {
                mBmpPoint[i] = new PointF(mLayoutWidth /15*(i-130)+10, mLayoutHeight *9/10- mBmp[i].getHeight());
            }
            else {
                mBmpPoint[i] = new PointF(mLayoutWidth /15*(i-130)+5, mLayoutHeight *9/10- mBmp[i].getHeight());
            }
        }
        mPaint = new Paint();
        drawFrame();
    }
    // called when SurfaceView is changed.
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        drawFrame();
    }
    // called when SurfaceView is destroyed.
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void checkInFrame(int i, float ab_dx) {
        if(mBmpPoint[i].x-mSensorEL.mSensorX *ab_dx < 0) {
            mBmpPoint[i].set(0, mBmpPoint[i].y);
        }
        else if(mLayoutWidth < mBmpPoint[i].x-mSensorEL.mSensorX *ab_dx+ mBmp[i].getWidth()) {
            mBmpPoint[i].set(mLayoutWidth - mBmp[i].getWidth(), mBmpPoint[i].y);
        }
        if(mBmpPoint[i].y+mSensorEL.mSensorY *ab_dx < 0) {
            mBmpPoint[i].set(mBmpPoint[i].x, 0);
        }
        else if(mLayoutHeight < mBmpPoint[i].y+mSensorEL.mSensorY *ab_dx+ mBmp[i].getHeight()) {
            mBmpPoint[i].set(mBmpPoint[i].x, mLayoutHeight - mBmp[i].getHeight());
        }
    }
}