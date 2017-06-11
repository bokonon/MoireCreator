package ys.moire.presentation.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * BaseActivity.
 */

open class BaseActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    public override fun onResume() {
        super.onResume()
    }

    public override fun onPause() {
        super.onPause()
    }

    public override fun onDestroy() {
        super.onDestroy()
    }
}
