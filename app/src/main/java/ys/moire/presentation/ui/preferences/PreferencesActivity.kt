package ys.moire.presentation.ui.preferences

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import ys.moire.BuildConfig
import ys.moire.MoireApplication
import ys.moire.R
import ys.moire.presentation.presenter.preferences.PreferencesPresenter
import ys.moire.presentation.ui.base.BaseActivity
import ys.moire.presentation.ui.view_parts.type.BaseTypes
import javax.inject.Inject

class PreferencesActivity : BaseActivity(), View.OnClickListener, ColorPickerDialogFragment.OnColorSelectedListener {

    @Inject
    lateinit var presenter: PreferencesPresenter

    // ScrollView
    private var scrollView: ScrollView? = null

    // Type
    private var spinner: Spinner? = null
    // Color
    private var lineAPreColor: View? = null
    private var lineBPreColor: View? = null
    private var bgPreColor: View? = null
    private var lineAColorSelectButton: Button? = null
    private var lineBColorSelectButton: Button? = null
    private var backgroundColorSelectButton: Button? = null
    // Number
    private var lineANumberText: TextView? = null
    private var lineBNumberText: TextView? = null
    private var lineANumberSeekBar: SeekBar? = null
    private var lineBNumberSeekBar: SeekBar? = null
    // Thick
    private var lineAThickText: TextView? = null
    private var lineBThickText: TextView? = null
    private var lineAThickSeekBar: SeekBar? = null
    private var lineBThickSeekBar: SeekBar? = null
    // Slope
    private var slopeLayout: LinearLayout? = null
    private var lineASlopeText: TextView? = null
    private var lineBSlopeText: TextView? = null
    private var lineASlopeSeekBar: SeekBar? = null
    private var lineBSlopeSeekBar: SeekBar? = null

    private var aLines: BaseTypes? = null
    private var bLines: BaseTypes? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pref)

        MoireApplication.component.inject(this)

        // set toolbar
        val toolbar = findViewById<Toolbar>(R.id.preference_tool_bar)
        setSupportActionBar(toolbar)

        val adView = this.findViewById<AdView>(R.id.ad_view)
        adView!!.adListener = object : AdListener() {
            override fun onAdClosed() {}
            override fun onAdFailedToLoad(errorCode: Int) {}
            override fun onAdLeftApplication() {}
            override fun onAdOpened() {}
            override fun onAdLoaded() {
                var currentY = 0
                if (scrollView != null) {
                    currentY = scrollView!!.scrollY
                }
                adView.visibility = View.VISIBLE
                if (scrollView != null) {
                    scrollView!!.scrollTo(0, currentY)
                }
            }
        }

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        // get view resources.
        initViews()
        // set listener
        setListener()

        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(v: AdapterView<*>, view: View, pos: Int, id: Long) {
                val spinner = v as Spinner
                val type = spinner.selectedItemPosition
                presenter.putMoireType(type)
                if (type == ys.moire.common.config.TYPE_LINE()) {
                    slopeLayout!!.visibility = View.VISIBLE
                } else {
                    slopeLayout!!.visibility = View.GONE
                }
            }

            override fun onNothingSelected(v: AdapterView<*>) {}

        }

        // load save data
        aLines = presenter.loadTypesData(ys.moire.common.config.LINE_A())
        bLines = presenter.loadTypesData(ys.moire.common.config.LINE_B())

        // Line A Number
        lineANumberSeekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                lineANumberText!!.text = getString(R.string.line_a_val_text, progress + MINIMUM_VAL)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                aLines!!.number = seekBar.progress + MINIMUM_VAL
                presenter.saveTypesData(ys.moire.common.config.LINE_A(), aLines!!)
            }
        })
        // Line B Number
        lineBNumberSeekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                lineBNumberText!!.text = getString(R.string.line_b_val_text, progress + MINIMUM_VAL)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                bLines!!.number = seekBar.progress + MINIMUM_VAL
                presenter.saveTypesData(ys.moire.common.config.LINE_B(), bLines!!)
            }
        })
        // Line A Thick
        lineAThickSeekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                lineAThickText!!.text = getString(R.string.line_a_val_text, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                aLines!!.thick = seekBar.progress
                presenter.saveTypesData(ys.moire.common.config.LINE_A(), aLines!!)
            }
        })
        // Line B Thick
        lineBThickSeekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                lineBThickText!!.text = getString(R.string.line_b_val_text, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                bLines!!.thick = seekBar.progress
                presenter.saveTypesData(ys.moire.common.config.LINE_B(), bLines!!)
            }
        })
        // Slope
        lineASlopeSeekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                lineASlopeText!!.text = getString(R.string.line_a_val_text, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                aLines!!.slope = seekBar.progress
                presenter.saveTypesData(ys.moire.common.config.LINE_A(), aLines!!)
            }
        })
        lineBSlopeSeekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                lineBSlopeText!!.text = getString(R.string.line_b_val_text, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                bLines!!.slope = seekBar.progress
                presenter.saveTypesData(ys.moire.common.config.LINE_B(), bLines!!)
            }
        })

        setPreferencesValues()
    }

    override fun onDestroy() {
        //        adView.destroy();
        super.onDestroy()
    }

    override fun onClick(view: View) {
        var colorOfType: String? = null
        var color = -1
        when (view.id) {
            R.id.line_a_color_button -> {
                colorOfType = ys.moire.common.config.LINE_A()
                color = presenter.loadTypesData(ys.moire.common.config.LINE_A()).color
            }
            R.id.line_b_color_button -> {
                colorOfType = ys.moire.common.config.LINE_B()
                color = presenter.loadTypesData(ys.moire.common.config.LINE_B()).color
            }
            R.id.bg_color_button -> {
                colorOfType = ys.moire.common.config.BG_COLOR()
                color = presenter.bgColor
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
    }

    override fun onColorSelected(args: Bundle) {
        val color = args.getInt(ys.moire.common.config.COLOR(), -1)
        val type = args.getString(ys.moire.common.config.COLOR_OF_TYPE()) ?: return
        when (type) {
            ys.moire.common.config.LINE_A() -> {
                aLines!!.color = color
                presenter.saveTypesData(ys.moire.common.config.LINE_A(), aLines!!)
                lineAPreColor!!.setBackgroundColor(color)
            }
            ys.moire.common.config.LINE_B() -> {
                bLines!!.color = color
                presenter.saveTypesData(ys.moire.common.config.LINE_B(), bLines!!)
                lineBPreColor!!.setBackgroundColor(color)
            }
            ys.moire.common.config.BG_COLOR() -> {
                presenter.putBgColor(color)
                bgPreColor!!.setBackgroundColor(color)
            }
            else -> {
            }
        }
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "color type : " + type)
            Log.d(TAG, "color : " + color)
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
        lineAColorSelectButton = findViewById<Button>(R.id.line_a_color_button)
        lineBColorSelectButton = findViewById<Button>(R.id.line_b_color_button)
        backgroundColorSelectButton = findViewById<Button>(R.id.bg_color_button)
        // Number
        lineANumberSeekBar = findViewById<SeekBar>(R.id.number_a_seekBar)
        lineANumberText = findViewById<TextView>(R.id.number_a_seekBar_text)
        lineBNumberSeekBar = findViewById<SeekBar>(R.id.number_b_seekBar)
        lineBNumberText = findViewById<TextView>(R.id.number_b_seekBar_text)
        // Thick
        lineAThickSeekBar = findViewById<SeekBar>(R.id.thick_a_seekBar)
        lineAThickText = findViewById<TextView>(R.id.thick_a_seekBar_text)
        lineBThickSeekBar = findViewById<SeekBar>(R.id.thick_b_seekBar)
        lineBThickText = findViewById<TextView>(R.id.thick_b_seekBar_text)
        // Slope
        lineASlopeSeekBar = findViewById<SeekBar>(R.id.slope_a_seekBar)
        lineASlopeText = findViewById<TextView>(R.id.slope_a_seekBar_text)
        lineBSlopeSeekBar = findViewById<SeekBar>(R.id.slope_b_seekBar)
        lineBSlopeText = findViewById<TextView>(R.id.slope_b_seekBar_text)
        slopeLayout = findViewById<LinearLayout>(R.id.slope_layout)
    }

    private fun setListener() {
        lineAColorSelectButton!!.setOnClickListener(this)
        lineBColorSelectButton!!.setOnClickListener(this)
        backgroundColorSelectButton!!.setOnClickListener(this)
    }

    /**
     * set Preferences value.
     */
    private fun setPreferencesValues() {
        // Type
        spinner!!.setSelection(presenter.moireType)
        // Color
        lineAPreColor!!.setBackgroundColor(aLines!!.color)
        lineBPreColor!!.setBackgroundColor(bLines!!.color)
        bgPreColor!!.setBackgroundColor(presenter.bgColor)
        // Number
        lineANumberSeekBar!!.progress = aLines!!.number - MINIMUM_VAL
        lineANumberText!!.text = getString(R.string.line_a_val_text, lineANumberSeekBar!!.progress + MINIMUM_VAL)
        lineBNumberSeekBar!!.progress = bLines!!.number - MINIMUM_VAL
        lineBNumberText!!.text = getString(R.string.line_b_val_text, lineBNumberSeekBar!!.progress + MINIMUM_VAL)
        // Thick
        lineAThickSeekBar!!.progress = aLines!!.thick
        lineAThickText!!.text = getString(R.string.line_a_val_text, lineAThickSeekBar!!.progress)
        lineBThickSeekBar!!.progress = bLines!!.thick
        lineBThickText!!.text = getString(R.string.line_b_val_text, lineBThickSeekBar!!.progress)
        // Slope
        lineASlopeSeekBar!!.progress = aLines!!.slope
        lineASlopeText!!.text = getString(R.string.line_a_val_text, lineASlopeSeekBar!!.progress)
        lineBSlopeSeekBar!!.progress = bLines!!.slope
        lineBSlopeText!!.text = getString(R.string.line_b_val_text, lineBSlopeSeekBar!!.progress)
    }

    companion object {
        private val TAG = "PreferencesActivity"

        private val MINIMUM_VAL = 10;
    }

}
