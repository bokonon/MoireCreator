package ys.moire.presentation.ui.about;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;

import ys.moire.BuildConfig;
import ys.moire.common.sensor.SensorEL;
import ys.moire.presentation.ui.base.BaseActivity;
import ys.moire.presentation.ui.view_parts.AboutView;

/**
 * about screen class
 * about this app
 */
public class AboutActivity extends BaseActivity implements SensorEL.OnSensorChangeListener {

    private static final String TAG = "AboutActivity";

    /** Sensor EL */
    private SensorEL sensorEL;
    /** Sensor Manager */
    private SensorManager sensorManager;
    /** View */
    private AboutView aboutView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float layoutWidth = metrics.widthPixels;
        float layoutHeight = metrics.heightPixels - getStatusBarHeight();
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "layoutWidth : "+layoutWidth);
            Log.d(TAG, "layoutHeight : "+layoutHeight);
        }

        sensorEL = new SensorEL(isTablet(), this);

        LinearLayout ll = new LinearLayout(this);
        aboutView = new AboutView(this, sensorEL, layoutWidth, layoutHeight);
        ll.addView(aboutView);
        setContentView(ll);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEL, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        sensorManager.unregisterListener(sensorEL);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, null);
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "onBackPressed");
        }
        super.onBackPressed();
    }

    @Override
    public void onSensorChange() {
        if(aboutView != null) {
            // draw on sensor change.
            aboutView.drawFrame();
        }
    }

    /**
     * is Tablet.
     * @return is Tablet
     */
    private Boolean isTablet() {
        Context context = getApplicationContext();
        Resources r = context.getResources();
        Configuration configuration = r.getConfiguration();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            if ((configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                    < Configuration.SCREENLAYOUT_SIZE_LARGE) {
                return false;
            } else {
                return true;
            }
        } else {
            if (configuration.smallestScreenWidthDp < 600) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * get status bar height.
     * @return status bar size
     */
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "status bar height : "+result);
        }
        return result;
    }

}
