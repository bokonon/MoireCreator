package ys.moire.presentation.ui.view_parts;

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
import ys.moire.common.sensor.SensorEL;

/**
 * about moire view class
 * description screen
 */
public class AboutView extends SurfaceView implements SurfaceHolder.Callback {

    /** move value */
    private static int VAL = 8;
    private float layoutWidth;
    private float layoutHeight;
    /** char image bitmap */
    private Bitmap[] bmp = new Bitmap[145];
    /** char image point */
    private PointF[] bmpPoint = new PointF[145];
    /** paint */
    private Paint paint;
    /** SensorEventListener */
    private SensorEL sensorEL;

    public AboutView(final Context context, final SensorEL sensorEL, final float width, final float height) {
        super(context);
        getHolder().addCallback(this);
        this.sensorEL = sensorEL;
        layoutWidth = width;
        layoutHeight = height;
    }
    public void drawFrame() {
        // get canvas
        Canvas canvas = getHolder().lockCanvas();
        if(canvas != null){
            // draw canvas
            canvas.drawColor(Color.WHITE);
            for(int i = 0; i< bmp.length; i++) {
                float r = (float)Math.random()*5;
                float abDx = VAL/2f*(r/5);
                bmpPoint[i].set(bmpPoint[i].x- sensorEL.sensorX *abDx, bmpPoint[i].y+ sensorEL.sensorY *abDx);
                checkInFrame(i, abDx);
                canvas.drawBitmap(bmp[i], bmpPoint[i].x, bmpPoint[i].y, paint);
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    // called when SurfaceView is created.
    public void surfaceCreated(SurfaceHolder holder) {

        TypedArray tArray = getResources().obtainTypedArray(R.array.chara_items);
        for (int i=0; i<tArray.length(); i++) {
            int resource = tArray.getResourceId(i, 0);
            bmp[i] = BitmapFactory.decodeResource(getResources(), resource);
        }
        // init char point
        // line1
        for (int i=0; i<15; i++) {
            if(i == 0) {
                bmpPoint[i] = new PointF(layoutWidth /17*(i+1)+20, layoutHeight /10- bmp[i].getHeight());
            }
            else if(i == 2) {
                bmpPoint[i] = new PointF(layoutWidth /17*(i+1)-10, layoutHeight /10- bmp[i].getHeight());
            }
            else if(i == 4) {
                bmpPoint[i] = new PointF(layoutWidth /17*(i+1)+10, layoutHeight /10- bmp[i].getHeight());
            }
            else if(i == 8) {
                bmpPoint[i] = new PointF(layoutWidth /17*(i+1)+5, layoutHeight /10- bmp[i].getHeight()*4/5);
            }
            else {
                bmpPoint[i] = new PointF(layoutWidth /17*(i+1)+5, layoutHeight /10- bmp[i].getHeight());
            }
        }
        // line2
        for (int i=15; i<33; i++) {
            if(i == 15 || i == 21) {
                bmpPoint[i] = new PointF(layoutWidth /19*(i-15)+10, layoutHeight *2/10- bmp[i].getHeight());
            }
            else {
                bmpPoint[i] = new PointF(layoutWidth /19*(i-15)+5, layoutHeight *2/10- bmp[i].getHeight());
            }
        }
        // line3
        for (int i=33; i<49; i++) {
            if(i == 33) {
                bmpPoint[i] = new PointF(layoutWidth /18*(i-33)+5, layoutHeight *3/10- bmp[i].getHeight()*4/5);
            }
            else {
                bmpPoint[i] = new PointF(layoutWidth /18*(i-33)+5, layoutHeight *3/10- bmp[i].getHeight());
            }
        }
        // line4
        for (int i=49; i<65; i++) {
            if(i == 57) {
                bmpPoint[i] = new PointF(layoutWidth /16*(i-49)+15, layoutHeight *4/10- bmp[i].getHeight()*4/5);
            }
            else if(i == 49 || i == 58) {
                bmpPoint[i] = new PointF(layoutWidth /16*(i-49)+15, layoutHeight *4/10- bmp[i].getHeight());
            }
            else if(i == 61) {
                bmpPoint[i] = new PointF(layoutWidth /16*(i-49), layoutHeight *4/10- bmp[i].getHeight());
            }
            else {
                bmpPoint[i] = new PointF(layoutWidth /16*(i-49)+5, layoutHeight *4/10- bmp[i].getHeight());
            }
        }
        // line5
        for (int i=65; i<78; i++) {
            if(i == 65) {
                bmpPoint[i] = new PointF(layoutWidth /15*(i-65)+10, layoutHeight *5/10- bmp[i].getHeight());
            }
            else if(i == 69) {
                bmpPoint[i] = new PointF(layoutWidth /15*(i-65)+5, layoutHeight *5/10- bmp[i].getHeight()*4/5);
            }
            else {
                bmpPoint[i] = new PointF(layoutWidth /15*(i-65)+5, layoutHeight *5/10- bmp[i].getHeight());
            }
        }
        // line6
        for (int i=78; i<99; i++) {
            if(i == 95) {
                bmpPoint[i] = new PointF(layoutWidth /21*(i-78)+5, layoutHeight *6/10- bmp[i].getHeight()*4/5);
            }
            else {
                bmpPoint[i] = new PointF(layoutWidth /21*(i-78)+5, layoutHeight *6/10- bmp[i].getHeight());
            }
        }
        // line7
        for (int i=99; i<116; i++) {
            if(i == 102) {
                bmpPoint[i] = new PointF(layoutWidth /20*(i-98)-15, layoutHeight *7/10- bmp[i].getHeight());
            }
            else if(i == 110) {
                bmpPoint[i] = new PointF(layoutWidth /20*(i-98)+5, layoutHeight *7/10- bmp[i].getHeight()*4/5);
            }
            else {
                bmpPoint[i] = new PointF(layoutWidth /20*(i-98)+5, layoutHeight *7/10- bmp[i].getHeight());
            }
        }
        // line8
        for (int i=116; i<134; i++) {
            if(i == 116 || i == 117 || i == 118 || i == 126) {
                bmpPoint[i] = new PointF(layoutWidth /20*(i-116)+10, layoutHeight *8/10- bmp[i].getHeight());
            }
            else if(i == 119 || i == 123) {
                bmpPoint[i] = new PointF(layoutWidth /20*(i-116)+5, layoutHeight *8/10- bmp[i].getHeight()*4/5);
            }
            else {
                bmpPoint[i] = new PointF(layoutWidth /20*(i-116)+5, layoutHeight *8/10- bmp[i].getHeight());
            }
        }
        // line9
        for (int i=134; i<145; i++) {
            if(i == 134) {
                bmpPoint[i] = new PointF(layoutWidth /15*(i-130)-5, layoutHeight *9/10- bmp[i].getHeight());
            }
            else if(i == 139 || i == 140) {
                bmpPoint[i] = new PointF(layoutWidth /15*(i-130)+10, layoutHeight *9/10- bmp[i].getHeight());
            }
            else {
                bmpPoint[i] = new PointF(layoutWidth /15*(i-130)+5, layoutHeight *9/10- bmp[i].getHeight());
            }
        }
        paint = new Paint();
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
        if(bmpPoint[i].x- sensorEL.sensorX *ab_dx < 0) {
            bmpPoint[i].set(0, bmpPoint[i].y);
        }
        else if(layoutWidth < bmpPoint[i].x- sensorEL.sensorX *ab_dx+ bmp[i].getWidth()) {
            bmpPoint[i].set(layoutWidth - bmp[i].getWidth(), bmpPoint[i].y);
        }
        if(bmpPoint[i].y+ sensorEL.sensorY *ab_dx < 0) {
            bmpPoint[i].set(bmpPoint[i].x, 0);
        }
        else if(layoutHeight < bmpPoint[i].y+ sensorEL.sensorY *ab_dx+ bmp[i].getHeight()) {
            bmpPoint[i].set(bmpPoint[i].x, layoutHeight - bmp[i].getHeight());
        }
    }
}