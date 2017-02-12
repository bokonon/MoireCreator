package ys.moire.ui.view.other;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import ys.moire.R;
import ys.moire.ui.base.BaseActivity;

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
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
