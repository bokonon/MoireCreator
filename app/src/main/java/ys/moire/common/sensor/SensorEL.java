package ys.moire.common.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import ys.moire.BuildConfig;

/**
 * sensor listener
 */
public class SensorEL  implements SensorEventListener {

    private static final String TAG = "SensorEL";

    /** change listener */
    public interface OnSensorChangeListener {
        void onSensorChange();
    }

    private OnSensorChangeListener listener;

    /** threshold val */
    private static final int VAL = 1;
    /** sensor x */
    public float sensorX;
    /** sensor y */
    public float sensorY;
    /** is tablet */
    private Boolean isTablet = false;
    /** threshold */
    private int threshold = VAL;

    public SensorEL(final Boolean isTablet, final OnSensorChangeListener listener) {
        super();
        this.isTablet = isTablet;
        this.listener = listener;
    }

    public void onSensorChanged(SensorEvent e) {
        if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            if(BuildConfig.DEBUG) {
                Log.d(TAG, "e.values[0] : " + e.values[0]);
                Log.d(TAG, "e.values[1] : "+e.values[1]);
            }

            if(isTablet) {
                sensorX = e.values[1];
                sensorY = -e.values[0];
            }
            else {
                sensorX = e.values[0];
                sensorY = e.values[1];
            }
            if(-threshold <= sensorX && sensorX <= threshold) {
                sensorX = 0;
            }
            if(-threshold <= sensorY && sensorY <= threshold) {
                sensorY = 0;
            }
            // call back.
            if(listener != null) listener.onSensorChange();
        }
    }

    public void onAccuracyChanged(Sensor s, int accuracy) {}

}
