package ys.moire.presentation.ui.about

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Window
import android.widget.LinearLayout
import ys.moire.BuildConfig
import ys.moire.common.sensor.SensorEL
import ys.moire.presentation.ui.base.BaseActivity
import ys.moire.presentation.ui.view_parts.AboutView

/**
 * about screen class
 * about this app
 */
class AboutActivity : BaseActivity(), SensorEL.OnSensorChangeListener {

    /** Sensor EL  */
    private var sensorEL: SensorEL? = null
    /** Sensor Manager  */
    private var sensorManager: SensorManager? = null
    /** View  */
    private var aboutView: AboutView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        val metrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(metrics)
        val layoutWidth = metrics.widthPixels.toFloat()
        val layoutHeight = (metrics.heightPixels - statusBarHeight).toFloat()
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "layoutWidth : " + layoutWidth)
            Log.d(TAG, "layoutHeight : " + layoutHeight)
        }

        sensorEL = SensorEL(isTablet, this)

        val ll = LinearLayout(this)
        aboutView = AboutView(this, sensorEL!!, layoutWidth, layoutHeight)
        ll.addView(aboutView)
        setContentView(ll)
    }

    override fun onResume() {
        super.onResume()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager!!.registerListener(sensorEL, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        sensorManager!!.unregisterListener(sensorEL)
        super.onPause()
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK, null)
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onBackPressed")
        }
        super.onBackPressed()
    }

    override fun onSensorChange() {
        if (aboutView != null) {
            // draw on sensor change.
            aboutView!!.drawFrame()
        }
    }

    /**
     * is Tablet.
     * @return is Tablet
     */
    private val isTablet: Boolean
        get() {
            val context = applicationContext
            val r = context.resources
            val configuration = r.configuration
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
                return configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
            } else {
                return configuration.smallestScreenWidthDp >= 600
            }
        }

    /**
     * get status bar height.
     * @return status bar size
     */
    private val statusBarHeight: Int
        get() {
            var result = 0
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = resources.getDimensionPixelSize(resourceId)
            }
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "status bar height : " + result)
            }
            return result
        }

    companion object {

        private val TAG = "AboutActivity"
    }

}