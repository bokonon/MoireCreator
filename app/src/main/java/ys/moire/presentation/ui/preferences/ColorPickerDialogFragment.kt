package ys.moire.presentation.ui.preferences

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import ys.moire.R

/**
 * ColorPicker dialog fragment.
 */
class ColorPickerDialogFragment : DialogFragment() {

    private lateinit var preView: View
    private val seekBar = arrayOfNulls<SeekBar>(3)

    private var colorOfType: String? = null

    interface OnColorSelectedListener {
        fun onColorSelected(args: Bundle)
    }

    private var listener: OnColorSelectedListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        val inflater = activity.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.fragment_color_picker, null)

        builder.setTitle(R.string.color)
        builder.setView(view)
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            onOkClick()
            dismiss()
        }

        initViews(view)

        return builder.create()
    }

    fun setOnColorSelectedListener(listener: OnColorSelectedListener) {
        this.listener = listener
    }

    private fun initViews(view: View) {
        val bundle = arguments
        colorOfType = bundle.getString(ys.moire.common.config.COLOR_OF_TYPE())
        val color = bundle.getInt(ys.moire.common.config.COLOR())

        val selectedRgb = IntArray(3)
        selectedRgb[0] = Color.red(color)
        selectedRgb[1] = Color.green(color)
        selectedRgb[2] = Color.blue(color)

        seekBar[0] = view.findViewById<SeekBar>(R.id.seekBar_red)
        seekBar[1] = view.findViewById<SeekBar>(R.id.seekBar_green)
        seekBar[2] = view.findViewById<SeekBar>(R.id.seekBar_blue)
        preView = view.findViewById<View>(R.id.pre_view)

        for (i in seekBar.indices) {
            seekBar[i]!!.setMax(255)
            seekBar[i]!!.setProgress(selectedRgb[i])
            seekBar[i]!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onProgressChanged(seekBar: SeekBar,
                                               progress: Int, fromUser: Boolean) {
                    preView.setBackgroundColor(Color.rgb(this@ColorPickerDialogFragment.seekBar[0]!!.getProgress(),
                            this@ColorPickerDialogFragment.seekBar[1]!!.getProgress(), this@ColorPickerDialogFragment.seekBar[2]!!.getProgress()))
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }
        preView.setBackgroundColor(Color.rgb(seekBar[0]!!.getProgress(),
                seekBar[1]!!.progress, seekBar[2]!!.getProgress()))
    }

    private fun onOkClick() {
        if (listener != null) {
            val bundle = Bundle()
            bundle.putString(ys.moire.common.config.COLOR_OF_TYPE(), colorOfType)
            bundle.putInt(ys.moire.common.config.COLOR(), Color.rgb(this@ColorPickerDialogFragment.seekBar[0]!!.getProgress(),
                    this@ColorPickerDialogFragment.seekBar[1]!!.getProgress(), this@ColorPickerDialogFragment.seekBar[2]!!.getProgress()))
            listener!!.onColorSelected(bundle)
        }
    }

    companion object {

        val TAG = "ColorPickerDialogFragment"

        fun newInstance(bundle: Bundle): ColorPickerDialogFragment {
            val f = ColorPickerDialogFragment()
            f.arguments = bundle
            return f
        }
    }
}
