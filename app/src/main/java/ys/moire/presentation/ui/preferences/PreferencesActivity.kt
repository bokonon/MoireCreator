package ys.moire.presentation.ui.preferences

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import ys.moire.BuildConfig
import ys.moire.MoireApplication
import ys.moire.R
import ys.moire.common.config.getType
import ys.moire.presentation.presenter.preferences.PreferencesPresenter
import ys.moire.presentation.ui.base.BaseActivity
import ys.moire.presentation.ui.viewparts.type.BaseTypes
import javax.inject.Inject

class PreferencesActivity : BaseActivity(), View.OnClickListener, ColorPickerDialogFragment.OnColorSelectedListener {

    companion object {
        private val TAG: String = PreferencesActivity::class.java.simpleName

        private const val MINIMUM_VAL = 10;
    }

    @Inject
    lateinit var presenter: PreferencesPresenter

    // ScrollView
    private lateinit var scrollView: ScrollView

    // Type
    private lateinit var spinner: Spinner
    // Color
    private lateinit var lineAPreColor: View
    private lateinit var lineBPreColor: View
    private lateinit var bgPreColor: View
    private lateinit var lineAColorSelectButton: Button
    private lateinit var lineBColorSelectButton: Button
    private lateinit var backgroundColorSelectButton: Button
    // Number
    private lateinit var lineANumberText: TextView
    private lateinit var lineBNumberText: TextView
    private lateinit var lineANumberSeekBar: SeekBar
    private lateinit var lineBNumberSeekBar: SeekBar
    // Thick
    private lateinit var lineAThickText: TextView
    private lateinit var lineBThickText: TextView
    private lateinit var lineAThickSeekBar: SeekBar
    private lateinit var lineBThickSeekBar: SeekBar
    // Slope
    private lateinit var slopeLayout: LinearLayout
    private lateinit var lineASlopeText: TextView
    private lateinit var lineBSlopeText: TextView
    private lateinit var lineASlopeSeekBar: SeekBar
    private lateinit var lineBSlopeSeekBar: SeekBar

    private lateinit var aLines: BaseTypes
    private lateinit var bLines: BaseTypes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pref)

        MoireApplication.component.inject(this)

        // set toolbar
        val toolbar = findViewById<Toolbar>(R.id.preference_tool_bar)
        setSupportActionBar(toolbar)

        // get view resources.
        initViews()
        // set listener
        setListener()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(v: AdapterView<*>, view: View, pos: Int, id: Long) {
                val spinner = v as Spinner
                val typeRawValue = spinner.selectedItemPosition
                val moireType = getType(typeRawValue)
                presenter.putMoireType(moireType)
                if (moireType == ys.moire.common.config.TypeEnum.LINE) {
                    slopeLayout.visibility = View.VISIBLE
                } else {
                    slopeLayout.visibility = View.GONE
                }
            }

            override fun onNothingSelected(v: AdapterView<*>) {}

        }

        // load save data
        aLines = presenter.loadTypesData(ys.moire.common.config.LINE_A())
        bLines = presenter.loadTypesData(ys.moire.common.config.LINE_B())

        // Line A Number
        lineANumberSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                lineANumberText.text = getString(R.string.line_a_val_text, progress + MINIMUM_VAL)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                aLines.number = seekBar.progress + MINIMUM_VAL
                presenter.saveTypesData(ys.moire.common.config.LINE_A(), aLines)
            }
        })
        // Line B Number
        lineBNumberSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                lineBNumberText.text = getString(R.string.line_b_val_text, progress + MINIMUM_VAL)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                bLines.number = seekBar.progress + MINIMUM_VAL
                presenter.saveTypesData(ys.moire.common.config.LINE_B(), bLines)
            }
        })
        // Line A Thick
        lineAThickSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                lineAThickText.text = getString(R.string.line_a_val_text, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                aLines.thick = seekBar.progress
                presenter.saveTypesData(ys.moire.common.config.LINE_A(), aLines)
            }
        })
        // Line B Thick
        lineBThickSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                lineBThickText.text = getString(R.string.line_b_val_text, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                bLines.thick = seekBar.progress
                presenter.saveTypesData(ys.moire.common.config.LINE_B(), bLines)
            }
        })
        // Slope
        lineASlopeSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                lineASlopeText.text = getString(R.string.line_a_val_text, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                aLines.slope = seekBar.progress
                presenter.saveTypesData(ys.moire.common.config.LINE_A(), aLines)
            }
        })
        lineBSlopeSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                lineBSlopeText.text = getString(R.string.line_b_val_text, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                bLines.slope = seekBar.progress
                presenter.saveTypesData(ys.moire.common.config.LINE_B(), bLines)
            }
        })

        setPreferencesValues()

        val adView = this.findViewById<AdView>(R.id.ad_view)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    override fun onClick(view: View) {
        var colorOfType: String? = null
        var color = -1

        var contentType = ""

        when (view.id) {
            R.id.line_a_color_button -> {
                colorOfType = ys.moire.common.config.LINE_A()
                color = presenter.loadTypesData(ys.moire.common.config.LINE_A()).color
                contentType = "line a color"
            }
            R.id.line_b_color_button -> {
                colorOfType = ys.moire.common.config.LINE_B()
                color = presenter.loadTypesData(ys.moire.common.config.LINE_B()).color
                contentType = "line b color"
            }
            R.id.bg_color_button -> {
                colorOfType = ys.moire.common.config.BG_COLOR()
                color = presenter.bgColor
                contentType = "background color"
            }
            else -> {
            }
        }

        val bundle = Bundle()
        bundle.putString(ys.moire.common.config.COLOR_OF_TYPE(), colorOfType)
        bundle.putInt(ys.moire.common.config.COLOR(), color)

        val f = ColorPickerDialogFragment.newInstance(bundle)
        f.setOnColorSelectedListener(this)
        f.show(supportFragmentManager, ColorPickerDialogFragment.TAG)

        postLogEvent(contentType)
    }

    override fun onColorSelected(args: Bundle) {
        val color = args.getInt(ys.moire.common.config.COLOR(), -1)
        val type = args.getString(ys.moire.common.config.COLOR_OF_TYPE()) ?: return
        when (type) {
            ys.moire.common.config.LINE_A() -> {
                aLines.color = color
                presenter.saveTypesData(ys.moire.common.config.LINE_A(), aLines)
                lineAPreColor.setBackgroundColor(color)
            }
            ys.moire.common.config.LINE_B() -> {
                bLines.color = color
                presenter.saveTypesData(ys.moire.common.config.LINE_B(), bLines)
                lineBPreColor.setBackgroundColor(color)
            }
            ys.moire.common.config.BG_COLOR() -> {
                presenter.putBgColor(color)
                bgPreColor.setBackgroundColor(color)
            }
            else -> {
            }
        }
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "color type : " + type.toString())
            Log.d(TAG, "color : " + color.toString())
        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK, null)
        super.onBackPressed()
    }

    /**
     * getResources.
     */
    private fun initViews() {
        // ScrollView
        scrollView = findViewById<ScrollView>(R.id.parent_scroll_view)
        // Spinner
        spinner = findViewById<Spinner>(R.id.type_spinner)
        // Color
        lineAPreColor = findViewById(R.id.line_a_pre_color)
        lineBPreColor = findViewById(R.id.line_b_pre_color)
        bgPreColor = findViewById(R.id.bg_pre_color)
        lineAColorSelectButton = findViewById(R.id.line_a_color_button)
        lineBColorSelectButton = findViewById(R.id.line_b_color_button)
        backgroundColorSelectButton = findViewById(R.id.bg_color_button)
        // Number
        lineANumberSeekBar = findViewById(R.id.number_a_seekBar)
        lineANumberText = findViewById(R.id.number_a_seekBar_text)
        lineBNumberSeekBar = findViewById(R.id.number_b_seekBar)
        lineBNumberText = findViewById(R.id.number_b_seekBar_text)
        // Thick
        lineAThickSeekBar = findViewById(R.id.thick_a_seekBar)
        lineAThickText = findViewById(R.id.thick_a_seekBar_text)
        lineBThickSeekBar = findViewById(R.id.thick_b_seekBar)
        lineBThickText = findViewById(R.id.thick_b_seekBar_text)
        // Slope
        lineASlopeSeekBar = findViewById(R.id.slope_a_seekBar)
        lineASlopeText = findViewById(R.id.slope_a_seekBar_text)
        lineBSlopeSeekBar = findViewById(R.id.slope_b_seekBar)
        lineBSlopeText = findViewById(R.id.slope_b_seekBar_text)
        slopeLayout = findViewById(R.id.slope_layout)
    }

    private fun setListener() {
        lineAColorSelectButton.setOnClickListener(this)
        lineBColorSelectButton.setOnClickListener(this)
        backgroundColorSelectButton.setOnClickListener(this)
    }

    /**
     * set Preferences value.
     */
    private fun setPreferencesValues() {
        // Type
        spinner.setSelection(presenter.moireType.number)
        // Color
        lineAPreColor.setBackgroundColor(aLines.color)
        lineBPreColor.setBackgroundColor(bLines.color)
        bgPreColor.setBackgroundColor(presenter.bgColor)
        // Number
        lineANumberSeekBar.progress = aLines.number - MINIMUM_VAL
        lineANumberText.text = getString(R.string.line_a_val_text, lineANumberSeekBar.progress + MINIMUM_VAL)
        lineBNumberSeekBar.progress = bLines.number - MINIMUM_VAL
        lineBNumberText.text = getString(R.string.line_b_val_text, lineBNumberSeekBar.progress + MINIMUM_VAL)
        // Thick
        lineAThickSeekBar.progress = aLines.thick
        lineAThickText.text = getString(R.string.line_a_val_text, lineAThickSeekBar.progress)
        lineBThickSeekBar.progress = bLines.thick
        lineBThickText.text = getString(R.string.line_b_val_text, lineBThickSeekBar.progress)
        // Slope
        lineASlopeSeekBar.progress = aLines.slope
        lineASlopeText.text = getString(R.string.line_a_val_text, lineASlopeSeekBar.progress)
        lineBSlopeSeekBar.progress = bLines.slope
        lineBSlopeText.text = getString(R.string.line_b_val_text, lineBSlopeSeekBar.progress)
    }

}
