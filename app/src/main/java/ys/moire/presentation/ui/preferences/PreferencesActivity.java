package ys.moire.presentation.ui.preferences;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import javax.inject.Inject;

import ys.moire.MoireApplication;
import ys.moire.R;
import ys.moire.common.config.Config;
import ys.moire.presentation.presenter.preferences.PreferencesPresenter;
import ys.moire.presentation.ui.base.BaseActivity;
import ys.moire.presentation.ui.view_parts.type.BaseTypes;

public class PreferencesActivity extends BaseActivity implements View.OnClickListener, ColorPickerDialogFragment.OnColorSelectedListener {

    private static final String TAG = "PreferencesActivity";

    @Inject
    PreferencesPresenter presenter;

    // ScrollView
    private ScrollView scrollView;

    // Type
    private Spinner spinner;
    // Color
    private View lineAPreColor;
    private View lineBPreColor;
    private View bgPreColor;
    private Button lineAColorSelectButton;
    private Button lineBColorSelectButton;
    private Button backgroundColorSelectButton;
    // Number
    private TextView lineANumberText;
    private TextView lineBNumberText;
    private SeekBar lineANumberSeekBar;
    private SeekBar lineBNumberSeekBar;
    // Thick
    private TextView lineAThickText;
    private TextView lineBThickText;
    private SeekBar lineAThickSeekBar;
    private SeekBar lineBThickSeekBar;
    // Slope
    private LinearLayout slopeLayout;
    private TextView lineASlopeText;
    private TextView lineBSlopeText;
    private SeekBar lineASlopeSeekBar;
    private SeekBar lineBSlopeSeekBar;

    private BaseTypes aLines;
    private BaseTypes bLines;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);

        ((MoireApplication)getApplication()).getComponent().inject(this);

        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.preference_tool_bar);
        setSupportActionBar(toolbar);

        final AdView adView = (AdView)this.findViewById(R.id.ad_view);
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
                int currentY = 0;
                if(scrollView != null) {
                    currentY = scrollView.getScrollY();
                }
                adView.setVisibility(View.VISIBLE);
                if(scrollView != null) {
                    scrollView.scrollTo(0, currentY);
                }
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // get view resources.
        initViews();
        // set listener
        setListener();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> v, View view, int pos, long id) {
                Spinner spinner = (Spinner) v;
                int type = spinner.getSelectedItemPosition();
                presenter.putMoireType(type);
                if (type == Config.TYPE_LINE) {
                    slopeLayout.setVisibility(View.VISIBLE);
                } else {
                    slopeLayout.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> v) {
            }

        });

        // load save data
        aLines = presenter.loadTypesData(Config.LINE_A);
        bLines = presenter.loadTypesData(Config.LINE_B);

        // Line A Number
        lineANumberSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lineANumberText.setText(getString(R.string.line_a_val_text) + " " + progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                aLines.setNumber(seekBar.getProgress());
                presenter.saveTypesData(Config.LINE_A, aLines);
            }
        });
        // Line B Number
        lineBNumberSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lineBNumberText.setText(getString(R.string.line_b_val_text) + " " + progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                bLines.setNumber(seekBar.getProgress());
                presenter.saveTypesData(Config.LINE_B, aLines);
            }
        });
        // Line A Thick
        lineAThickSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lineAThickText.setText(getString(R.string.line_a_val_text) + " " + progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                aLines.setThick(seekBar.getProgress());
                presenter.saveTypesData(Config.LINE_A, aLines);
            }
        });
        // Line B Thick
        lineBThickSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lineBThickText.setText(getString(R.string.line_b_val_text) + " " + progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                bLines.setThick(seekBar.getProgress());
                presenter.saveTypesData(Config.LINE_B, bLines);
            }
        });
        // Slope
        lineASlopeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lineASlopeText.setText(getString(R.string.line_a_val_text) + " " + progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                aLines.setSlope(seekBar.getProgress());
                presenter.saveTypesData(Config.LINE_A, aLines);
            }
        });
        lineBSlopeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lineBSlopeText.setText(getString(R.string.line_b_val_text) + " " + progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                bLines.setSlope(seekBar.getProgress());
                presenter.saveTypesData(Config.LINE_B, bLines);
            }
        });

        setPreferencesValues();
    }

    public void onDestroy(){
//        adView.destroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        String colorOfType = null;
        int color = -1;
        switch(view.getId()) {
            case R.id.line_a_color_button:
                colorOfType = Config.LINE_A;
                color = presenter.loadTypesData(Config.LINE_A).getColor();
                break;
            case R.id.line_b_color_button:
                colorOfType = Config.LINE_B;
                color = presenter.loadTypesData(Config.LINE_B).getColor();
                break;
            case R.id.bg_color_button:
                colorOfType = Config.BG_COLOR;
                color = presenter.getBgColor();
                break;
            default:
                break;
        }

        Bundle bundle = new Bundle();
        bundle.putString(Config.COLOR_OF_TYPE, colorOfType);
        bundle.putInt(Config.COLOR, color);

        ColorPickerDialogFragment f = ColorPickerDialogFragment.newInstance(bundle);
        f.setOnColorSelectedListener(this);
        f.show(getSupportFragmentManager(), ColorPickerDialogFragment.TAG);
    }

    public void onColorSelected(final Bundle bundle) {
        if(bundle == null) {
            return;
        }
        int color = bundle.getInt(Config.COLOR, -1);
        String type = bundle.getString(Config.COLOR_OF_TYPE);
        if(type == null) {
            return;
        }
        switch(type) {
            case Config.LINE_A:
                aLines.setColor(color);
                presenter.saveTypesData(Config.LINE_A, aLines);
                lineAPreColor.setBackgroundColor(color);
                break;
            case Config.LINE_B:
                bLines.setColor(color);
                presenter.saveTypesData(Config.LINE_B, bLines);
                lineBPreColor.setBackgroundColor(color);
                break;
            case Config.BG_COLOR:
                presenter.putBgColor(color);
                bgPreColor.setBackgroundColor(color);
                break;
            default:
                break;
        }
        Log.d(TAG, "color type : " + type);
        Log.d(TAG, "color : " + color);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, null);
        super.onBackPressed();
    }

    /**
     * getResources.
     */
    private void initViews() {
        // ScrollView
        scrollView = (ScrollView)findViewById(R.id.parent_scroll_view);
        // Spinner
        spinner = (Spinner)findViewById(R.id.type_spinner);
        // Color
        lineAPreColor = (View)findViewById(R.id.line_a_pre_color);
        lineBPreColor = (View)findViewById(R.id.line_b_pre_color);
        bgPreColor = (View)findViewById(R.id.bg_pre_color);
        lineAColorSelectButton = (Button)findViewById(R.id.line_a_color_button);
        lineBColorSelectButton = (Button)findViewById(R.id.line_b_color_button);
        backgroundColorSelectButton = (Button)findViewById(R.id.bg_color_button);
        // Number
        lineANumberSeekBar = (SeekBar)findViewById(R.id.number_a_seekBar);
        lineANumberText = (TextView)findViewById(R.id.number_a_seekBar_text);
        lineBNumberSeekBar = (SeekBar)findViewById(R.id.number_b_seekBar);
        lineBNumberText = (TextView)findViewById(R.id.number_b_seekBar_text);
        // Thick
        lineAThickSeekBar = (SeekBar)findViewById(R.id.thick_a_seekBar);
        lineAThickText = (TextView)findViewById(R.id.thick_a_seekBar_text);
        lineBThickSeekBar = (SeekBar)findViewById(R.id.thick_b_seekBar);
        lineBThickText = (TextView)findViewById(R.id.thick_b_seekBar_text);
        // Slope
        lineASlopeSeekBar = (SeekBar)findViewById(R.id.slope_a_seekBar);
        lineASlopeText = (TextView)findViewById(R.id.slope_a_seekBar_text);
        lineBSlopeSeekBar = (SeekBar)findViewById(R.id.slope_b_seekBar);
        lineBSlopeText = (TextView)findViewById(R.id.slope_b_seekBar_text);
        slopeLayout = (LinearLayout)findViewById(R.id.slope_layout);
    }

    private void setListener() {
        lineAColorSelectButton.setOnClickListener(this);
        lineBColorSelectButton.setOnClickListener(this);
        backgroundColorSelectButton.setOnClickListener(this);
    }

    /**
     * set Preferences value.
     */
    private void setPreferencesValues() {
        // Type
        spinner.setSelection(presenter.getMoireType());
        // Color
        lineAPreColor.setBackgroundColor(aLines.getColor());
        lineBPreColor.setBackgroundColor(bLines.getColor());
        bgPreColor.setBackgroundColor(presenter.getBgColor());
        // Number
        lineANumberSeekBar.setProgress(aLines.getNumber());
        lineANumberText.setText(getString(R.string.line_a_val_text) + " " + lineANumberSeekBar.getProgress());
        lineBNumberSeekBar.setProgress(bLines.getNumber());
        lineBNumberText.setText(getString(R.string.line_b_val_text) + " " + lineBNumberSeekBar.getProgress());
        // Thick
        lineAThickSeekBar.setProgress(aLines.getThick());
        lineAThickText.setText(getString(R.string.line_a_val_text) + " " + lineAThickSeekBar.getProgress());
        lineBThickSeekBar.setProgress(bLines.getThick());
        lineBThickText.setText(getString(R.string.line_b_val_text) + " " + lineBThickSeekBar.getProgress());
        // Slope
        lineASlopeSeekBar.setProgress(aLines.getSlope());
        lineASlopeText.setText(getString(R.string.line_a_val_text) + " " + lineASlopeSeekBar.getProgress());
        lineBSlopeSeekBar.setProgress(bLines.getSlope());
        lineBSlopeText.setText(getString(R.string.line_b_val_text) + " " + lineBSlopeSeekBar.getProgress());
    }

}
