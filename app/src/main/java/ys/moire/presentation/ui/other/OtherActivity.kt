package ys.moire.presentation.ui.other

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

import ys.moire.R
import ys.moire.presentation.ui.base.BaseActivity

/**
 * other activity.
 */

class OtherActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)

        // set toolbar
        val toolbar = findViewById(R.id.other_tool_bar) as Toolbar?
        setSupportActionBar(toolbar)

        val adView = findViewById(R.id.ad_view) as AdView?
        adView!!.adListener = object : AdListener() {
            override fun onAdClosed() {}
            override fun onAdFailedToLoad(errorCode: Int) {}
            override fun onAdLeftApplication() {}
            override fun onAdOpened() {}
            override fun onAdLoaded() {
                adView.visibility = View.VISIBLE
            }
        }
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}
