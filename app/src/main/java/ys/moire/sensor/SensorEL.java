package ys.moire.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * センサーリスナークラス
 */
public class SensorEL  implements SensorEventListener {

    private static final String TAG = "SensorEL";

    /** センサー変更のリスナー */
    public interface OnSensorChangeListener {
        void onSensorChange();
    }

    private OnSensorChangeListener mListener;

    /** 閾値 */
    private static final int VAL = 1;
    /** センサーX値 */
    public float mSensorX;
    /** センサーY値 */
    public float mSensorY;
    /** タブレットであるか */
    private Boolean mIsTablet = false;
    /** 閾値 */
    private int mThreshold = VAL;

    public SensorEL(final Boolean isTablet, final OnSensorChangeListener listener) {
        super();
        mIsTablet = isTablet;
        mListener = listener;
    }

    public void onSensorChanged(SensorEvent e) {
        if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            if(BuildConfig.DEBUG) {
//                Log.d(TAG, "e.values[0] : " + e.values[0]);
//                Log.d(TAG, "e.values[1] : "+e.values[1]);
//            }
            if(mIsTablet) {
                mSensorX = e.values[1];
                mSensorY = -e.values[0];
            }
            else {
                mSensorX = e.values[0];
                mSensorY = e.values[1];
            }
            if(-mThreshold <= mSensorX && mSensorX <= mThreshold) {
                mSensorX = 0;
            }
            if(-mThreshold <= mSensorY && mSensorY <= mThreshold) {
                mSensorY = 0;
            }
            // 描画します.
            if(mListener != null) {
                mListener.onSensorChange();
            }
        }
    }

    public void onAccuracyChanged(Sensor s, int accuracy) {}

}
