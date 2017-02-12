package ys.moire.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ys.moire.BuildConfig;
import ys.moire.R;
import ys.moire.config.Config;
import ys.moire.util.PreferencesUtil;
import ys.moire.view.MoireView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    /** RequestCode constant */
    private static final int INTENT_FOR_SETTING = 1;
    private static final int INTENT_FOR_ABOUT = 2;
    private static final int INTENT_FOR_OTHER = 3;

    private static final int LINE_A = 0;
    private static final int LINE_B = 1;

    /** view width */
    private int mLayoutWidth;
    /** view height */
    private int mLayoutHeight;
    /** View */
    private MoireView mMoireView;
    /** Touch A Button */
    private Button mATouchSelectedButton;
    /** Touch B Button */
    private Button mBTouchSelectedButton;
    /** Pause Button */
    private ImageButton mPauseImageButton;
    /** ScreenShot Button */
    private ImageButton mScreenShotImageButton;

    /** Line Mode（AorB） */
    private int mTouchLineMode;

    private int mStatusBarHeight;
    private int mToolBarHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mLayoutWidth = metrics.widthPixels;
        mStatusBarHeight = getStatusBarHeight();
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "mStatusBarHeight : " + mStatusBarHeight);
        }
        mLayoutHeight = (metrics.heightPixels - mStatusBarHeight);

        mMoireView = new MoireView(this, mLayoutWidth, mLayoutHeight, PreferencesUtil.getMoireType(this));
        mMoireView.setBgColor(PreferencesUtil.getBgColor(this));

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.parent_linear_layout);
        linearLayout.addView(mMoireView);

        initToolBarViews(toolbar);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMoireView.setOnBackground(false);
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "onResume");
        }
    }

    @Override
    public void onPause() {
        mMoireView.setOnBackground(true);
        super.onPause();
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "onPause");
        }
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
                Intent settingIntent = new Intent(this, PreferenceActivity.class);
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
                mMoireView.setMoireType(PreferencesUtil.getMoireType(this));
                mMoireView.setBgColor(PreferencesUtil.getBgColor(this));

                mMoireView.loadLines();
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
        super.onDestroy();
        finish();
    }

    private int mFirstTouchX;
    private int mFirstTouchY;

    private int mTouchMoveX;
    private int mTouchMoveY;

    private int mTempMoveX;
    private int mTempMoveY;

    private int mMoveCount = 0;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int currentX = (int)e.getX();
        int currentY = (int)e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFirstTouchX = (int)e.getX();
                mFirstTouchY = (int)e.getY();
                if(mMoireView.mType == Config.TYPE_LINE) {
                    // TouchMode on
                    mMoireView.setOnTouchMode(mTouchLineMode, true);
                    // init dx dy
                    mTouchMoveX = 0;
                    mTempMoveX = 0;
                }
                else if(mMoireView.mType == Config.TYPE_CIRCLE || mMoireView.mType == Config.TYPE_RECT) {
                    // TouchMode on
                    mMoireView.setOnTouchMode(mTouchLineMode, true);
                    // init dx dy
                    mTouchMoveX = 0;
                    mTouchMoveY = 0;
                    mTempMoveX = 0;
                    mTempMoveY = 0;
                }
                else if(mMoireView.mType == Config.TYPE_ORIGINAL) {
                    // TouchModeをOnにします.
                    mMoireView.setOnTouchMode(mTouchLineMode, true);
                    mMoveCount = 0;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(currentX- mFirstTouchX) >= 2 || Math.abs(currentY- mFirstTouchY) >= 2) {
                    if(mMoireView.mType == Config.TYPE_LINE) {
                        mTouchMoveX = currentX - mFirstTouchX - mTempMoveX;
                        mMoireView.addTouchValue(mTouchLineMode, mTouchMoveX, 0);

                        mTempMoveX = currentX - mFirstTouchX;
                    }
                    else if(mMoireView.mType == Config.TYPE_CIRCLE ||mMoireView.mType == Config.TYPE_RECT) {
                        mTouchMoveX = currentX - mFirstTouchX - mTempMoveX;
                        mTouchMoveY = currentY - mFirstTouchY - mTempMoveY;
                        mMoireView.addTouchValue(mTouchLineMode, mTouchMoveX, mTouchMoveY);

                        mTempMoveX = currentX - mFirstTouchX;
                        mTempMoveY = currentY - mFirstTouchY;
                    }
                    else if(mMoireView.mType == Config.TYPE_ORIGINAL) {
                        mMoireView.drawOriginalLine(mTouchLineMode, mLayoutWidth, e.getX(), e.getY() - mStatusBarHeight - mToolBarHeight, mMoveCount);
                        mMoveCount++;
                    }
                    // draw
                    mMoireView.drawFrame();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(mMoireView.mType == Config.TYPE_LINE) {
                    // TouchMode off
                    mMoireView.setOnTouchMode(mTouchLineMode, false);
                    // init dx dy
                    mTouchMoveX = 0;
                    mTempMoveX = 0;
                }
                else if(mMoireView.mType == Config.TYPE_CIRCLE || mMoireView.mType == Config.TYPE_RECT) {
                    // TouchMode off
                    mMoireView.setOnTouchMode(mTouchLineMode, false);
                    // init dx dy
                    mTouchMoveX = 0;
                    mTouchMoveY = 0;
                    mTempMoveX = 0;
                    mTempMoveY = 0;
                }
                if(mMoireView.mType == Config.TYPE_ORIGINAL) {
                    // TouchMode off
                    mMoireView.setOnTouchMode(mTouchLineMode, false);
                    // init dx dy
                    mTouchMoveX = 0;
                    mTouchMoveY = 0;
                    mTempMoveX = 0;
                    mTempMoveY = 0;
                }
                break;
        }return true;
    }

    @Override
    public void onClick(View view) {
        if(view == mATouchSelectedButton) {
            mTouchLineMode = LINE_A;
            mATouchSelectedButton.setSelected(true);
            mBTouchSelectedButton.setSelected(false);
        }
        else if(view == mBTouchSelectedButton) {
            mTouchLineMode = LINE_B;
            mATouchSelectedButton.setSelected(false);
            mBTouchSelectedButton.setSelected(true);
        }
        else if(view == mPauseImageButton) {
            if(mMoireView.isPause()) {
                mMoireView.pause(false);
                mPauseImageButton.setImageResource(R.drawable.ic_pause_black_24dp);
            }
            else {
                mMoireView.pause(true);
                mPauseImageButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            }
        }
        else if(view == mScreenShotImageButton) {
            captureCanvas();
        }
    }

    private void initToolBarViews(final Toolbar toolbar) {
        mATouchSelectedButton = (Button)toolbar.findViewById(R.id.a_button);
        mBTouchSelectedButton = (Button)toolbar.findViewById(R.id.b_button);
        mPauseImageButton = (ImageButton)toolbar.findViewById(R.id.pause_button);
        mScreenShotImageButton = (ImageButton)toolbar.findViewById(R.id.camera_button);

        mATouchSelectedButton.setOnClickListener(this);
        mBTouchSelectedButton.setOnClickListener(this);
        mPauseImageButton.setOnClickListener(this);
        mScreenShotImageButton.setOnClickListener(this);

        // set A button at first.
        mATouchSelectedButton.setSelected(true);
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

        mToolBarHeight = toolbar.getHeight();
    }

    /**
     * save capture image.
     */
    private void captureCanvas() {
        // decide stored directory.
        File dir;
        String path = Environment.getExternalStorageDirectory() + "/ys.MoireCreator/";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dir = new File(path);
            dir.mkdirs();
        } else {
            dir = Environment.getDataDirectory();
        }
        // create file name.
        String fileName = getFileName();
        String AttachName = dir.getAbsolutePath() + "/" + fileName;

        File file = new File(path + fileName);
        FileOutputStream fos = null;
        Bitmap bitmap = drawBitmap();
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch(FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), R.string.message_capture_failed, Toast.LENGTH_SHORT).show();
        } finally {
            bitmap.recycle();
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }
        // reflected in gallery.
        ContentValues values = new ContentValues();
        ContentResolver contentResolver = getContentResolver();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put("_data", AttachName);
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Toast.makeText(getApplicationContext(), getString(R.string.message_capture_success)
                        +"\nFilePath : "+AttachName,
                Toast.LENGTH_SHORT).show();
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "FilePath : "+AttachName);
        }
    }

    private Bitmap drawBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mMoireView.getWidth(), mMoireView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        mMoireView.captureCanvas(canvas);
        return bitmap;
    }

    private String getFileName() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return "Moire_" + formatter.format(date) + ".png";
    }

}