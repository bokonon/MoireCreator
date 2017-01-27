package ys.moire.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

import ys.moire.R;
import ys.moire.config.Config;
import ys.moire.type.BaseTypes;
import ys.moire.util.PreferencesUtil;

/**
 * ColorPickerのダイアログフラグメント.
 */
public class ColorPickerDialogFragment extends DialogFragment {

    public static final String TAG = "ColorPickerDialogFragment";

    private int mSelectedLine = 0;
    private View mPreView;
    private SeekBar[] mSeekBar = new SeekBar[3];

    private String mColorOfType;

    public interface OnColorSelectedListener {
        void onColorSelected(Bundle args);
    }

    private OnColorSelectedListener mListener;

    public static ColorPickerDialogFragment newInstance(final Bundle bundle) {
        ColorPickerDialogFragment f = new ColorPickerDialogFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_color_picker, null);

        builder.setTitle(R.string.color);
        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int witch) {
                onOkClick();
                dismiss();
            }
        });

        initViews(view);

        return builder.create();
    }

    public void setOnColorSelectedListener(final OnColorSelectedListener listener) {
        mListener = listener;
    }

    private void initViews(final View view) {
        Bundle bundle = getArguments();
        mColorOfType = bundle.getString(Config.COLOR_OF_TYPE);
        int color = -1;
        switch(mColorOfType) {
            case Config.LINE_A:
                BaseTypes aTypes = PreferencesUtil.loadTypesData(getActivity(), Config.LINE_A);
                color = aTypes.getColor();
                break;
            case Config.LINE_B:
                BaseTypes bTypes = PreferencesUtil.loadTypesData(getActivity(), Config.LINE_B);
                color = bTypes.getColor();
                break;
            case Config.BG_COLOR:
                color = PreferencesUtil.getBgColor(getActivity());
                break;
            default:
                break;
        }

        int[] selectedRgb = new int[3];
        selectedRgb[0] = Color.red(color);
        selectedRgb[1] = Color.green(color);
        selectedRgb[2] = Color.blue(color);

        mSeekBar[0] = (SeekBar)view.findViewById(R.id.seekBar_red);
        mSeekBar[1] = (SeekBar)view.findViewById(R.id.seekBar_green);
        mSeekBar[2] = (SeekBar)view.findViewById(R.id.seekBar_blue);
        mPreView = (View)view.findViewById(R.id.pre_view);

        for(int i=0; i< mSeekBar.length; i++) {
            mSeekBar[i].setMax(255);
            mSeekBar[i].setProgress(selectedRgb[i]);
            mSeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

    private void onOkClick() {
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
        if(mListener != null) {
            Bundle bundle = new Bundle();
            bundle.putString(Config.COLOR_OF_TYPE, mColorOfType);
            bundle.putInt(Config.COLOR, color);
            mListener.onColorSelected(bundle);
        }
    }
}
