package ys.moire.presentation.ui.other;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import ys.moire.R;
import ys.moire.presentation.ui.base.BaseActivity;

/**
 * other activity.
 */

public class OtherActivity extends BaseActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.other_tool_bar);
        setSupportActionBar(toolbar);

        final AdView adView = (AdView)findViewById(R.id.ad_view);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
            }
            @Override
            public void onAdLeftApplication() {
            }
            @Override
            public void onAdOpened() {
            }
            @Override
            public void onAdLoaded() {
                adView.setVisibility(View.VISIBLE);
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
