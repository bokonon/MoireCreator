package ys.moire.presentation.ui.other

import android.os.Bundle
import androidx.appcompat.widget.Toolbar

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
        val toolbar = findViewById<Toolbar>(R.id.other_tool_bar)
        setSupportActionBar(toolbar)

        val adView = findViewById<AdView>(R.id.ad_view)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}
