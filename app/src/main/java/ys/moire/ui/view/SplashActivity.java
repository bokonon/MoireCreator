package ys.moire.ui.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;

import ys.moire.R;
import ys.moire.ui.view.main.MainActivity;

public class SplashActivity extends Activity {

    private static final int PENDING_TIME = 1000;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);

        handler = new Handler();
        handler.postDelayed(new SplashHandler(), PENDING_TIME);
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
        if (keyCode == KeyEvent.KEYCODE_BACK){
            // disable back key
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
        return false;
    }
}
