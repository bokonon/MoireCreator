package ys.moire.activity;

import android.app.Activity;
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
import ys.moire.sensor.SensorEL;
import ys.moire.view.AboutView;

/**
 * このアプリについて画面
 * about screen
 */
public class AboutActivity extends Activity implements SensorEL.OnSensorChangeListener {

    private static final String TAG = "AboutActivity";

    /** Sensor EL */
    private SensorEL mSensorEL;
    /** Sensor Manager */
    private SensorManager mSensorManager;
    /** View */
    private AboutView mAboutView;

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

        mSensorEL = new SensorEL(isTablet(), this);

        LinearLayout ll = new LinearLayout(this);
        mAboutView = new AboutView(this, mSensorEL, layoutWidth, layoutHeight);
        ll.addView(mAboutView);
        setContentView(ll);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(mSensorEL, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        mSensorManager.unregisterListener(mSensorEL);
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
        if(mAboutView != null) {
            // センサー変更で描画します.
            mAboutView.drawFrame();
        }
    }

    /**
     * タブレットであるか.
     * @return タブレットである
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
     * ステータスバーのサイズを取得します.
     * @return status bar size
     */
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "status_bar_height : "+result);
        }
        return result;
    }

}
