package ys.moire.presentation.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.Window

import ys.moire.R
import ys.moire.presentation.ui.main.MainActivity

class SplashActivity : Activity() {

    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.splash)

        handler = Handler()
        handler!!.postDelayed(SplashHandler(), PENDING_TIME.toLong())
    }

    private inner class SplashHandler : Runnable {
        override fun run() {
            val it = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(it)
            this@SplashActivity.finish()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // disable back key
        } else {
            return super.onKeyDown(keyCode, event)
        }
        return false
    }

    companion object {

        private val PENDING_TIME = 1000
    }
}
