package ys.moire.common.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log

import ys.moire.BuildConfig

/**
 * sensor listener
 */
class SensorEL(private val isTablet: Boolean, private val listener: SensorEL.OnSensorChangeListener?) : SensorEventListener {

    /** change listener  */
    interface OnSensorChangeListener {
        fun onSensorChange()
    }

    /** sensor x  */
    var sensorX: Float = 0.toFloat()
    /** sensor y  */
    var sensorY: Float = 0.toFloat()
    /** threshold  */
    private val threshold = VAL

    override fun onSensorChanged(e: SensorEvent) {
        if (e.sensor.type == Sensor.TYPE_ACCELEROMETER) {

            if (BuildConfig.DEBUG) {
                Log.d(TAG, "e.values[0] : " + e.values[0])
                Log.d(TAG, "e.values[1] : " + e.values[1])
            }

            if (isTablet) {
                sensorX = e.values[1]
                sensorY = -e.values[0]
            } else {
                sensorX = e.values[0]
                sensorY = e.values[1]
            }
            if (-threshold <= sensorX && sensorX <= threshold) {
                sensorX = 0f
            }
            if (-threshold <= sensorY && sensorY <= threshold) {
                sensorY = 0f
            }
            // call back.
            listener?.onSensorChange()
        }
    }

    override fun onAccuracyChanged(s: Sensor, accuracy: Int) {}

    companion object {

        private val TAG = "SensorEL"

        /** threshold val  */
        private val VAL = 1
    }

}
