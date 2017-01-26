package ys.moire.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;

import ys.moire.R;

public class SplashActivity extends Activity {

    private static final int PENDING_TIME = 1000;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);

        mHandler = new Handler();
        mHandler.postDelayed(new SplashHandler(), PENDING_TIME);
    }

    class SplashHandler implements Runnable {
        public void run() {
            Intent it = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(it);
            SplashActivity.this.finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //戻りボタンの処理
        if (keyCode == KeyEvent.KEYCODE_BACK){
            // バックキーを無効に
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
        return false;
    }
}
