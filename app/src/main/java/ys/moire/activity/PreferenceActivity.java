package ys.moire.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import ys.moire.R;
import ys.moire.config.Config;
import ys.moire.fragment.ColorPickerDialogFragment;
import ys.moire.type.BaseTypes;
import ys.moire.util.PreferencesUtil;

public class PreferenceActivity extends AppCompatActivity implements View.OnClickListener, ColorPickerDialogFragment.OnColorSelectedListener {

    private static final String TAG = "PreferenceActivity";

    // ScrollView
    private ScrollView mScrollView;

    // Type
    private Spinner mSpinner;
    // Color
    private View mLineAPreColor;
    private View mLineBPreColor;
    private View mBgPreColor;
    private Button mLineAColorSelectButton;
    private Button mLineBColorSelectButton;
    private Button mBackgroundColorSelectButton;
    // Number
    private TextView mLineANumberText;
    private TextView mLineBNumberText;
    private SeekBar mLineANumberSeekBar;
    private SeekBar mLineBNumberSeekBar;
    // Thick
    private TextView mLineAThickText;
    private TextView mLineBThickText;
    private SeekBar mLineAThickSeekBar;
    private SeekBar mLineBThickSeekBar;
    // Slope
    private LinearLayout mSlopeLayout;
    private TextView mLineASlopeText;
    private TextView mLineBSlopeText;
    private SeekBar mLineASlopeSeekBar;
    private SeekBar mLineBSlopeSeekBar;

    private BaseTypes mALines;
    private BaseTypes mBLines;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pref);

        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.preference_tool_bar);
        setSupportActionBar(toolbar);

        final AdView adView = (AdView)this.findViewById(R.id.adView);
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
                if(mScrollView != null) {
                    currentY = mScrollView.getScrollY();
                }
                adView.setVisibility(View.VISIBLE);
                if(mScrollView != null) {
                    mScrollView.scrollTo(0, currentY);
                }
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // リソースを取得します.
        initViews();
        // リスナーをセットします
        setListener();

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> v, View view, int pos, long id) {
                Spinner spinner = (Spinner) v;
                int type = spinner.getSelectedItemPosition();
                PreferencesUtil.putMoireType(PreferenceActivity.this, type);
                if (type == Config.TYPE_LINE) {
                    mSlopeLayout.setVisibility(View.VISIBLE);
                } else {
                    mSlopeLayout.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> v) {
            }

        });

        // 保存状態をまとめてロード
        mALines = PreferencesUtil.loadTypesData(this, Config.LINE_A);
        mBLines = PreferencesUtil.loadTypesData(this, Config.LINE_B);

        // Line A Number
        mLineANumberSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLineANumberText.setText(getString(R.string.line_a_val_text) + " " + progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                mALines.setNumber(seekBar.getProgress());
                PreferencesUtil.saveTypesData(PreferenceActivity.this, mALines, Config.LINE_A);
            }
        });
        // Line B Number
        mLineBNumberSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLineBNumberText.setText(getString(R.string.line_b_val_text) + " " + progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                mBLines.setNumber(seekBar.getProgress());
                PreferencesUtil.saveTypesData(PreferenceActivity.this, mALines, Config.LINE_B);
            }
        });
        // Line A Thick
        mLineAThickSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLineAThickText.setText(getString(R.string.line_a_val_text) + " " + progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                mALines.setThick(seekBar.getProgress());
                PreferencesUtil.saveTypesData(PreferenceActivity.this, mALines, Config.LINE_A);
            }
        });
        // Line B Thick
        mLineBThickSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLineBThickText.setText(getString(R.string.line_b_val_text) + " " + progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                mBLines.setThick(seekBar.getProgress());
                PreferencesUtil.saveTypesData(PreferenceActivity.this, mBLines, Config.LINE_B);
            }
        });
        // Slope
        mLineASlopeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLineASlopeText.setText(getString(R.string.line_a_val_text) + " " + progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                mALines.setSlope(seekBar.getProgress());
                PreferencesUtil.saveTypesData(PreferenceActivity.this, mALines, Config.LINE_A);
            }
        });
        mLineBSlopeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLineBSlopeText.setText(getString(R.string.line_b_val_text) + " " + progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                mBLines.setSlope(seekBar.getProgress());
                PreferencesUtil.saveTypesData(PreferenceActivity.this, mBLines, Config.LINE_B);
            }
        });

        setPreferenceValues();
    }

    public void onDestroy(){
//        adView.destroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        String colorOfType = null;
        switch(view.getId()) {
            case R.id.line_a_color_button:
                colorOfType = Config.LINE_A;
                break;
            case R.id.line_b_color_button:
                colorOfType = Config.LINE_B;
                break;
            case R.id.bg_color_button:
                colorOfType = Config.BG_COLOR;
                break;
        }

        Bundle bundle = new Bundle();
        bundle.putString(Config.COLOR_OF_TYPE, colorOfType);
        ColorPickerDialogFragment f = ColorPickerDialogFragment.newInstance(bundle);
        f.setOnColorSelectedListener(this);
        f.show(getSupportFragmentManager(), ColorPickerDialogFragment.TAG);
    }

    public void onColorSelected(final Bundle bundle) {
        if(bundle == null) {
            return;
        }
        int color = bundle.getInt(Config.COLOR, -1);
        if(color == -1) {
            return;
        }
        String type = bundle.getString(Config.COLOR_OF_TYPE);
        if(type == null) {
            return;
        }
        switch(type) {
            case Config.LINE_A:
                mALines.setColor(color);
                PreferencesUtil.saveTypesData(PreferenceActivity.this, mALines, Config.LINE_A);
                mLineAPreColor.setBackgroundColor(color);
                break;
            case Config.LINE_B:
                mBLines.setColor(color);
                PreferencesUtil.saveTypesData(PreferenceActivity.this, mBLines, Config.LINE_B);
                mLineBPreColor.setBackgroundColor(color);
                break;
            case Config.BG_COLOR:
                PreferencesUtil.putBgColor(this, color);
                mBgPreColor.setBackgroundColor(color);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, null);
        super.onBackPressed();
    }

    /**
     * リソースを取得します.
     */
    private void initViews() {
        // ScrollView
        mScrollView = (ScrollView)findViewById(R.id.parent_scroll_view);
        // Spinner
        mSpinner = (Spinner)findViewById(R.id.type_spinner);
        // Color
        mLineAPreColor = (View)findViewById(R.id.line_a_pre_color);
        mLineBPreColor = (View)findViewById(R.id.line_b_pre_color);
        mBgPreColor = (View)findViewById(R.id.bg_pre_color);
        mLineAColorSelectButton = (Button)findViewById(R.id.line_a_color_button);
        mLineBColorSelectButton = (Button)findViewById(R.id.line_b_color_button);
        mBackgroundColorSelectButton = (Button)findViewById(R.id.bg_color_button);
        // Number
        mLineANumberSeekBar = (SeekBar)findViewById(R.id.number_a_seekBar);
        mLineANumberText = (TextView)findViewById(R.id.number_a_seekBar_text);
        mLineBNumberSeekBar = (SeekBar)findViewById(R.id.number_b_seekBar);
        mLineBNumberText = (TextView)findViewById(R.id.number_b_seekBar_text);
        // Thick
        mLineAThickSeekBar = (SeekBar)findViewById(R.id.thick_a_seekBar);
        mLineAThickText = (TextView)findViewById(R.id.thick_a_seekBar_text);
        mLineBThickSeekBar = (SeekBar)findViewById(R.id.thick_b_seekBar);
        mLineBThickText = (TextView)findViewById(R.id.thick_b_seekBar_text);
        // Slope
        mLineASlopeSeekBar = (SeekBar)findViewById(R.id.slope_a_seekBar);
        mLineASlopeText = (TextView)findViewById(R.id.slope_a_seekBar_text);
        mLineBSlopeSeekBar = (SeekBar)findViewById(R.id.slope_b_seekBar);
        mLineBSlopeText = (TextView)findViewById(R.id.slope_b_seekBar_text);
        mSlopeLayout = (LinearLayout)findViewById(R.id.slope_layout);
    }

    private void setListener() {
        mLineAColorSelectButton.setOnClickListener(this);
        mLineBColorSelectButton.setOnClickListener(this);
        mBackgroundColorSelectButton.setOnClickListener(this);
    }

    /**
     * Preferenceの値をセットします.
     */
    private void setPreferenceValues() {
        // Type
        mSpinner.setSelection(PreferencesUtil.getMoireType(this));
        // Color
        mLineAPreColor.setBackgroundColor(mALines.getColor());
        mLineBPreColor.setBackgroundColor(mBLines.getColor());
        mBgPreColor.setBackgroundColor(PreferencesUtil.getBgColor(this));
        // Number
        mLineANumberSeekBar.setProgress(mALines.getNumber());
        mLineANumberText.setText(getString(R.string.line_a_val_text) + " " + mLineANumberSeekBar.getProgress());
        mLineBNumberSeekBar.setProgress(mBLines.getNumber());
        mLineBNumberText.setText(getString(R.string.line_b_val_text) + " " + mLineBNumberSeekBar.getProgress());
        // Thick
        mLineAThickSeekBar.setProgress(mALines.getThick());
        mLineAThickText.setText(getString(R.string.line_a_val_text) + " " + mLineAThickSeekBar.getProgress());
        mLineBThickSeekBar.setProgress(mBLines.getThick());
        mLineBThickText.setText(getString(R.string.line_b_val_text) + " " + mLineBThickSeekBar.getProgress());
        // Slope
        mLineASlopeSeekBar.setProgress(mALines.getSlope());
        mLineASlopeText.setText(getString(R.string.line_a_val_text) + " " + mLineASlopeSeekBar.getProgress());
        mLineBSlopeSeekBar.setProgress(mBLines.getSlope());
        mLineBSlopeText.setText(getString(R.string.line_b_val_text) + " " + mLineBSlopeSeekBar.getProgress());
    }

}
