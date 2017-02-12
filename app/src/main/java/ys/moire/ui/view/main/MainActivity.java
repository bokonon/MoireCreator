package ys.moire.ui.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import ys.moire.BuildConfig;
import ys.moire.R;
import ys.moire.config.Config;
import ys.moire.ui.base.BaseActivity;
import ys.moire.ui.view.about.AboutActivity;
import ys.moire.ui.view.moire.MoireView;
import ys.moire.ui.view.other.OtherActivity;
import ys.moire.ui.view.preferences.PreferencesActivity;

public class MainActivity extends BaseActivity implements MainMvpView, View.OnClickListener, MoireView.OnSurfaceChange {

    private static final String TAG = "MainActivity";

    /** RequestCode constant */
    private static final int INTENT_FOR_SETTING = 1;
    private static final int INTENT_FOR_ABOUT = 2;
    private static final int INTENT_FOR_OTHER = 3;

    private static final int LINE_A = 0;
    private static final int LINE_B = 1;

    private MainPresenter mainPresenter;

    /** View */
    private MoireView moireView;
    /** Touch A Button */
    private Button aTouchSelectedButton;
    /** Touch B Button */
    private Button bTouchSelectedButton;
    /** Pause Button */
    private ImageButton pauseImageButton;
    /** ScreenShot Button */
    private ImageButton screenShotImageButton;

    /** Line Mode（AorB） */
    private int touchLineMode;

    private int statusBarHeight;
    private int toolBarHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(this);

        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int layoutWidth = metrics.widthPixels;
        statusBarHeight = getStatusBarHeight();
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "statusBarHeight : " + statusBarHeight);
        }
        int layoutHeight = (metrics.heightPixels - statusBarHeight);

        moireView = new MoireView(this, layoutWidth, layoutHeight, mainPresenter.getMoireType());
        moireView.setBgColor(mainPresenter.getBgColor());

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.parent_linear_layout);
        linearLayout.addView(moireView);

        initToolBarViews(toolbar);

        mainPresenter.setMoireView(moireView);
        mainPresenter.onCreate(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mainPresenter.onResume();
        moireView.setOnBackground(false);
    }

    @Override
    public void onPause() {
        moireView.setOnBackground(true);
        mainPresenter.onPause();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent settingIntent = new Intent(this, PreferencesActivity.class);
                startActivityForResult(settingIntent, INTENT_FOR_SETTING);
                break;
            case R.id.menu_about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivityForResult(aboutIntent, INTENT_FOR_ABOUT);
                break;
            case R.id.menu_other:
                Intent otherIntent = new Intent(this, OtherActivity.class);
                startActivityForResult(otherIntent, INTENT_FOR_OTHER);
                break;
            default:
                break;
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK){
            // from preference screen
            if (requestCode == INTENT_FOR_SETTING){
                moireView.setMoireType(mainPresenter.getMoireType());
                moireView.setBgColor(mainPresenter.getBgColor());

                moireView.loadLines(
                        mainPresenter.loadTypesData(Config.LINE_A),
                        mainPresenter.loadTypesData(Config.LINE_B)
                );
            }
            // from about screen
            else if(requestCode == INTENT_FOR_ABOUT) {

            }
            // from other screen
            else if(requestCode == INTENT_FOR_OTHER) {

            }
        }
    }

    public void onDestroy() {
        mainPresenter.onDestroy();
        super.onDestroy();
        finish();
    }

    private int firstTouchX;
    private int firstTouchY;

    private int touchMoveX;
    private int touchMoveY;

    private int tempMoveX;
    private int tempMoveY;

    private int moveCount = 0;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int currentX = (int)e.getX();
        int currentY = (int)e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                firstTouchX = (int)e.getX();
                firstTouchY = (int)e.getY();
                if(moireView.mType == Config.TYPE_LINE) {
                    // TouchMode on
                    moireView.setOnTouchMode(touchLineMode, true);
                    // init dx dy
                    touchMoveX = 0;
                    tempMoveX = 0;
                }
                else if(moireView.mType == Config.TYPE_CIRCLE || moireView.mType == Config.TYPE_RECT) {
                    // TouchMode on
                    moireView.setOnTouchMode(touchLineMode, true);
                    // init dx dy
                    touchMoveX = 0;
                    touchMoveY = 0;
                    tempMoveX = 0;
                    tempMoveY = 0;
                }
                else if(moireView.mType == Config.TYPE_ORIGINAL) {
                    // TouchModeをOnにします.
                    moireView.setOnTouchMode(touchLineMode, true);
                    moveCount = 0;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(currentX- firstTouchX) >= 2 || Math.abs(currentY- firstTouchY) >= 2) {
                    if(moireView.mType == Config.TYPE_LINE) {
                        touchMoveX = currentX - firstTouchX - tempMoveX;
                        moireView.addTouchValue(touchLineMode, touchMoveX, 0);

                        tempMoveX = currentX - firstTouchX;
                    }
                    else if(moireView.mType == Config.TYPE_CIRCLE || moireView.mType == Config.TYPE_RECT) {
                        touchMoveX = currentX - firstTouchX - tempMoveX;
                        touchMoveY = currentY - firstTouchY - tempMoveY;
                        moireView.addTouchValue(touchLineMode, touchMoveX, touchMoveY);

                        tempMoveX = currentX - firstTouchX;
                        tempMoveY = currentY - firstTouchY;
                    }
                    else if(moireView.mType == Config.TYPE_ORIGINAL) {
                        moireView.drawOriginalLine(touchLineMode, e.getX(), e.getY() - statusBarHeight - toolBarHeight, moveCount);
                        moveCount++;
                    }
                    // draw
                    moireView.drawFrame();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(moireView.mType == Config.TYPE_LINE) {
                    // TouchMode off
                    moireView.setOnTouchMode(touchLineMode, false);
                    // init dx dy
                    touchMoveX = 0;
                    tempMoveX = 0;
                }
                else if(moireView.mType == Config.TYPE_CIRCLE || moireView.mType == Config.TYPE_RECT) {
                    // TouchMode off
                    moireView.setOnTouchMode(touchLineMode, false);
                    // init dx dy
                    touchMoveX = 0;
                    touchMoveY = 0;
                    tempMoveX = 0;
                    tempMoveY = 0;
                }
                if(moireView.mType == Config.TYPE_ORIGINAL) {
                    // TouchMode off
                    moireView.setOnTouchMode(touchLineMode, false);
                    // init dx dy
                    touchMoveX = 0;
                    touchMoveY = 0;
                    tempMoveX = 0;
                    tempMoveY = 0;
                }
                break;
        }return true;
    }

    @Override
    public void onClick(View view) {
        if(view == aTouchSelectedButton) {
            touchLineMode = LINE_A;
            aTouchSelectedButton.setSelected(true);
            bTouchSelectedButton.setSelected(false);
        }
        else if(view == bTouchSelectedButton) {
            touchLineMode = LINE_B;
            aTouchSelectedButton.setSelected(false);
            bTouchSelectedButton.setSelected(true);
        }
        else if(view == pauseImageButton) {
            if(moireView.isPause()) {
                moireView.pause(false);
                pauseImageButton.setImageResource(R.drawable.ic_pause_black_24dp);
            }
            else {
                moireView.pause(true);
                pauseImageButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            }
        }
        else if(view == screenShotImageButton) {
            mainPresenter.captureCanvas();
        }
    }

    private void initToolBarViews(final Toolbar toolbar) {
        aTouchSelectedButton = (Button)toolbar.findViewById(R.id.a_button);
        bTouchSelectedButton = (Button)toolbar.findViewById(R.id.b_button);
        pauseImageButton = (ImageButton)toolbar.findViewById(R.id.pause_button);
        screenShotImageButton = (ImageButton)toolbar.findViewById(R.id.camera_button);

        aTouchSelectedButton.setOnClickListener(this);
        bTouchSelectedButton.setOnClickListener(this);
        pauseImageButton.setOnClickListener(this);
        screenShotImageButton.setOnClickListener(this);

        // set A button at first.
        aTouchSelectedButton.setSelected(true);
    }

    /**
     * get StatusBar height.
     * @return status bar size
     */
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_tool_bar);

        toolBarHeight = toolbar.getHeight();
    }

    public void showToast(final String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSurfaceChange() {
        moireView.loadLines(
                mainPresenter.loadTypesData(Config.LINE_A),
                mainPresenter.loadTypesData(Config.LINE_B)
        );
    }
}