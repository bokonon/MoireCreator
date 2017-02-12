package ys.moire.ui.view.preferences;

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

/**
 * ColorPicker dialog fragment.
 */
public class ColorPickerDialogFragment extends DialogFragment {

    public static final String TAG = "ColorPickerDialogFragment";

    private View preView;
    private SeekBar[] seekBar = new SeekBar[3];

    private String colorOfType;

    public interface OnColorSelectedListener {
        void onColorSelected(Bundle args);
    }

    private OnColorSelectedListener listener;

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
        this.listener = listener;
    }

    private void initViews(final View view) {
        Bundle bundle = getArguments();
        colorOfType = bundle.getString(Config.COLOR_OF_TYPE);
        int color = bundle.getInt(Config.COLOR);

        int[] selectedRgb = new int[3];
        selectedRgb[0] = Color.red(color);
        selectedRgb[1] = Color.green(color);
        selectedRgb[2] = Color.blue(color);

        seekBar[0] = (SeekBar)view.findViewById(R.id.seekBar_red);
        seekBar[1] = (SeekBar)view.findViewById(R.id.seekBar_green);
        seekBar[2] = (SeekBar)view.findViewById(R.id.seekBar_blue);
        preView = (View)view.findViewById(R.id.pre_view);

        for(int i = 0; i< seekBar.length; i++) {
            seekBar[i].setMax(255);
            seekBar[i].setProgress(selectedRgb[i]);
            seekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStartTrackingTouch(SeekBar seekBar) {}
                public void onProgressChanged(SeekBar seekBar,
                                              int progress, boolean fromUser) {
                    preView.setBackgroundColor(Color.rgb(ColorPickerDialogFragment.this.seekBar[0].getProgress(),
                            ColorPickerDialogFragment.this.seekBar[1].getProgress(), ColorPickerDialogFragment.this.seekBar[2].getProgress()));
                }
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }
        preView.setBackgroundColor(Color.rgb(seekBar[0].getProgress(),
                seekBar[1].getProgress(), seekBar[2].getProgress()));
    }

    private void onOkClick() {
        if(listener != null) {
            Bundle bundle = new Bundle();
            bundle.putString(Config.COLOR_OF_TYPE, colorOfType);
            bundle.putInt(Config.COLOR, Color.rgb(ColorPickerDialogFragment.this.seekBar[0].getProgress(),
                    ColorPickerDialogFragment.this.seekBar[1].getProgress(), ColorPickerDialogFragment.this.seekBar[2].getProgress()));
            listener.onColorSelected(bundle);
        }
    }
}
