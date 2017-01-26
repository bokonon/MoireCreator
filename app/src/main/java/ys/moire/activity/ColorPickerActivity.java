package ys.moire.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import ys.moire.R;
import ys.moire.config.Config;
import ys.moire.type.BaseTypes;
import ys.moire.util.PreferencesUtil;

public class ColorPickerActivity extends Activity {

    private int mSelectedLine = 0;
    private View mPreView;
    private SeekBar[] mSeekBar = new SeekBar[3];
    private Button mOkButton;

    private String mColorOfType;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_colorpick);

        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int layout_width = metrics.widthPixels;

        mColorOfType = getIntent().getStringExtra(Config.COLOR_OF_TYPE);
        int color = -1;
        switch(mColorOfType) {
            case Config.LINE_A:
                BaseTypes aTypes = PreferencesUtil.loadTypesData(this, Config.LINE_A);
                color = aTypes.getColor();
                break;
            case Config.LINE_B:
                BaseTypes bTypes = PreferencesUtil.loadTypesData(this, Config.LINE_B);
                color = bTypes.getColor();
                break;
            case Config.BG_COLOR:
                color = PreferencesUtil.getBgColor(this);
                break;
            default:
                break;
        }

        int[] selectedRgb = new int[3];
        selectedRgb[0] = Color.red(color);
        selectedRgb[1] = Color.green(color);
        selectedRgb[2] = Color.blue(color);

        LinearLayout parentLL = (LinearLayout)findViewById(R.id.parent_ll);
//        parent_ll.setPadding(10, layout_width/3, 10, layout_width/3);
        MarginLayoutParams lp=(MarginLayoutParams)parentLL.getLayoutParams();
        lp.setMargins(layout_width/20, layout_width/3, layout_width/20, layout_width/3);
        parentLL.setLayoutParams(lp);

        mSeekBar[0] = (SeekBar)findViewById(R.id.seekBar_red);
        mSeekBar[1] = (SeekBar)findViewById(R.id.seekBar_green);
        mSeekBar[2] = (SeekBar)findViewById(R.id.seekBar_blue);
        mPreView = (View)findViewById(R.id.pre_view);

        mOkButton = (Button)findViewById(R.id.ok_button);
        mOkButton.getLayoutParams().width = (int)(layout_width*0.3);
        mOkButton.setOnClickListener(new BtClickListener());

        for(int i=0; i< mSeekBar.length; i++) {
            mSeekBar[i].setMax(255);
            mSeekBar[i].setProgress(selectedRgb[i]);
            mSeekBar[i].setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                public void onStartTrackingTouch(SeekBar seekBar) {}
                public void onProgressChanged(SeekBar seekBar,
                                              int progress, boolean fromUser) {
                    seekBar.setProgress(progress);

                    mPreView.setBackgroundColor(Color.rgb(mSeekBar[0].getProgress(),
                            mSeekBar[1].getProgress(), mSeekBar[2].getProgress()));

                }
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }
        mPreView.setBackgroundColor(Color.rgb(mSeekBar[0].getProgress(),
                mSeekBar[1].getProgress(), mSeekBar[2].getProgress()));
    }
    public void onDestroy(){
        super.onDestroy();
    }

    class BtClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            int color = -1;
            switch(mSelectedLine) {
                case 0:
                    color = Color.rgb(mSeekBar[0].getProgress(),
                            mSeekBar[1].getProgress(), mSeekBar[2].getProgress());
                    break;
                case 1:
                    color = Color.rgb(mSeekBar[0].getProgress(),
                            mSeekBar[1].getProgress(), mSeekBar[2].getProgress());
                    break;
                case 2:
                    color = Color.rgb(mSeekBar[0].getProgress(),
                            mSeekBar[1].getProgress(), mSeekBar[2].getProgress());
                    break;
            }
            Intent intent = new Intent();
            intent.putExtra(Config.COLOR_OF_TYPE, mColorOfType);
            intent.putExtra(Config.COLOR, color);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //戻りボタンの処理
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK, null);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}