package ys.moire.presentation.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import ys.moire.BuildConfig
import ys.moire.MoireApplication
import ys.moire.R
import ys.moire.presentation.presenter.main.MainPresenter
import ys.moire.presentation.ui.about.AboutActivity
import ys.moire.presentation.ui.base.BaseActivity
import ys.moire.presentation.ui.other.OtherActivity
import ys.moire.presentation.ui.preferences.PreferencesActivity
import ys.moire.presentation.ui.view_parts.MoireView
import javax.inject.Inject

class MainActivity : BaseActivity(), View.OnClickListener, MoireView.OnSurfaceChange {

    @Inject
    lateinit var mainPresenter: MainPresenter

    /** View  */
    private var moireView: MoireView? = null
    /** Touch A Button  */
    private var aTouchSelectedButton: Button? = null
    /** Touch B Button  */
    private var bTouchSelectedButton: Button? = null
    /** Pause Button  */
    private var pauseImageButton: ImageButton? = null
    /** ScreenShot Button  */
    private var screenShotImageButton: ImageButton? = null

    /** Line Mode（AorB）  */
    private var touchLineMode: Int = 0

    private var statusBarHeight: Int = 0
    private var toolBarHeight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_main)

        MoireApplication.component.inject(this)

        // set toolbar
        val toolbar = findViewById(R.id.main_tool_bar) as Toolbar?
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val metrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(metrics)
        val layoutWidth = metrics.widthPixels
        statusBarHeight = getStatusBarHeight()
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "statusBarHeight : " + statusBarHeight)
        }
        val layoutHeight = metrics.heightPixels - statusBarHeight

        moireView = MoireView(this, layoutWidth, layoutHeight, mainPresenter.moireType)
        moireView!!.setBgColor(mainPresenter.bgColor)

        val linearLayout = findViewById(R.id.parent_linear_layout) as LinearLayout?
        linearLayout!!.addView(moireView)

        initToolBarViews(toolbar!!)

        mainPresenter.setMoireView(moireView!!)
        mainPresenter.onCreate()
    }

    override fun onResume() {
        super.onResume()
        mainPresenter.onResume()
        moireView!!.setOnBackground(false)
    }

    override fun onPause() {
        moireView!!.setOnBackground(true)
        mainPresenter.onPause()
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> {
                val settingIntent = Intent(this, PreferencesActivity::class.java)
                startActivityForResult(settingIntent, INTENT_FOR_SETTING)
            }
            R.id.menu_about -> {
                val aboutIntent = Intent(this, AboutActivity::class.java)
                startActivityForResult(aboutIntent, INTENT_FOR_ABOUT)
            }
            R.id.menu_other -> {
                val otherIntent = Intent(this, OtherActivity::class.java)
                startActivityForResult(otherIntent, INTENT_FOR_OTHER)
            }
            else -> {
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == Activity.RESULT_OK) {
            // from preference screen
            if (requestCode == INTENT_FOR_SETTING) {
                moireView!!.setMoireType(mainPresenter.moireType)
                moireView!!.setBgColor(mainPresenter.bgColor)

                moireView!!.loadLines(
                        mainPresenter.loadTypesData(ys.moire.common.config.LINE_A()),
                        mainPresenter.loadTypesData(ys.moire.common.config.LINE_B())
                )
            } else if (requestCode == INTENT_FOR_ABOUT) {
                // NOP
            } else if (requestCode == INTENT_FOR_OTHER) {
                // NOP
            }// from other screen
            // from about screen
        }
    }

    override fun onDestroy() {
        mainPresenter.onDestroy()
        super.onDestroy()
        finish()
    }

    private var firstTouchX: Int = 0
    private var firstTouchY: Int = 0

    private var touchMoveX: Int = 0
    private var touchMoveY: Int = 0

    private var tempMoveX: Int = 0
    private var tempMoveY: Int = 0

    private var moveCount = 0

    override fun onTouchEvent(e: MotionEvent): Boolean {
        val currentX = e.x.toInt()
        val currentY = e.y.toInt()
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                firstTouchX = e.x.toInt()
                firstTouchY = e.y.toInt()
                if (moireView!!.type == ys.moire.common.config.TYPE_LINE()) {
                    // TouchMode on
                    moireView!!.setOnTouchMode(touchLineMode, true)
                    // init dx dy
                    touchMoveX = 0
                    tempMoveX = 0
                } else if (moireView!!.type == ys.moire.common.config.TYPE_CIRCLE()
                        || moireView!!.type == ys.moire.common.config.TYPE_RECT()
                        || moireView!!.type == ys.moire.common.config.TYPE_HEART()
                        || moireView!!.type == ys.moire.common.config.TYPE_SYNAPSE()) {
                    // TouchMode on
                    moireView!!.setOnTouchMode(touchLineMode, true)
                    // init dx dy
                    touchMoveX = 0
                    touchMoveY = 0
                    tempMoveX = 0
                    tempMoveY = 0
                } else if (moireView!!.type == ys.moire.common.config.TYPE_ORIGINAL()) {
                    // TouchModeをOnにします.
                    moireView!!.setOnTouchMode(touchLineMode, true)
                    moveCount = 0
                }
            }
            MotionEvent.ACTION_MOVE -> if (Math.abs(currentX - firstTouchX) >= 2 || Math.abs(currentY - firstTouchY) >= 2) {
                if (moireView!!.type == ys.moire.common.config.TYPE_LINE()) {
                    touchMoveX = currentX - firstTouchX - tempMoveX
                    moireView!!.addTouchValue(touchLineMode, touchMoveX, 0)

                    tempMoveX = currentX - firstTouchX
                } else if (moireView!!.type == ys.moire.common.config.TYPE_CIRCLE()
                        || moireView!!.type == ys.moire.common.config.TYPE_RECT()
                        || moireView!!.type == ys.moire.common.config.TYPE_HEART()
                        || moireView!!.type == ys.moire.common.config.TYPE_SYNAPSE()) {
                    touchMoveX = currentX - firstTouchX - tempMoveX
                    touchMoveY = currentY - firstTouchY - tempMoveY
                    moireView!!.addTouchValue(touchLineMode, touchMoveX, touchMoveY)

                    tempMoveX = currentX - firstTouchX
                    tempMoveY = currentY - firstTouchY
                } else if (moireView!!.type == ys.moire.common.config.TYPE_ORIGINAL()) {
                    moireView!!.drawOriginalLine(touchLineMode, e.x, e.y - statusBarHeight.toFloat() - toolBarHeight.toFloat(), moveCount)
                    moveCount++
                }
                // draw
                moireView!!.drawFrame()
            }
            MotionEvent.ACTION_UP -> {
                if (moireView!!.type == ys.moire.common.config.TYPE_LINE()) {
                    // TouchMode off
                    moireView!!.setOnTouchMode(touchLineMode, false)
                    // init dx dy
                    touchMoveX = 0
                    tempMoveX = 0
                } else if (moireView!!.type == ys.moire.common.config.TYPE_CIRCLE()
                        || moireView!!.type == ys.moire.common.config.TYPE_RECT()
                        || moireView!!.type == ys.moire.common.config.TYPE_HEART()
                        || moireView!!.type == ys.moire.common.config.TYPE_SYNAPSE()) {
                    // TouchMode off
                    moireView!!.setOnTouchMode(touchLineMode, false)
                    // init dx dy
                    touchMoveX = 0
                    touchMoveY = 0
                    tempMoveX = 0
                    tempMoveY = 0
                }
                if (moireView!!.type == ys.moire.common.config.TYPE_ORIGINAL()) {
                    // TouchMode off
                    moireView!!.setOnTouchMode(touchLineMode, false)
                    // init dx dy
                    touchMoveX = 0
                    touchMoveY = 0
                    tempMoveX = 0
                    tempMoveY = 0
                }
            }
        }
        return true
    }

    override fun onClick(view: View) {
        if (view === aTouchSelectedButton) {
            touchLineMode = LINE_A
            aTouchSelectedButton!!.isSelected = true
            bTouchSelectedButton!!.isSelected = false
        } else if (view === bTouchSelectedButton) {
            touchLineMode = LINE_B
            aTouchSelectedButton!!.isSelected = false
            bTouchSelectedButton!!.isSelected = true
        } else if (view === pauseImageButton) {
            if (moireView!!.isPause) {
                moireView!!.pause(false)
                pauseImageButton!!.setImageResource(R.drawable.ic_pause_black_24dp)
            } else {
                moireView!!.pause(true)
                pauseImageButton!!.setImageResource(R.drawable.ic_play_arrow_black_24dp)
            }
        } else if (view === screenShotImageButton) {
            mainPresenter.captureCanvas()
        }
    }

    private fun initToolBarViews(toolbar: Toolbar) {
        aTouchSelectedButton = toolbar.findViewById(R.id.a_button) as Button
        bTouchSelectedButton = toolbar.findViewById(R.id.b_button) as Button
        pauseImageButton = toolbar.findViewById(R.id.pause_button) as ImageButton
        screenShotImageButton = toolbar.findViewById(R.id.camera_button) as ImageButton

        aTouchSelectedButton!!.setOnClickListener(this)
        bTouchSelectedButton!!.setOnClickListener(this)
        pauseImageButton!!.setOnClickListener(this)
        screenShotImageButton!!.setOnClickListener(this)

        // set A button at first.
        aTouchSelectedButton!!.isSelected = true
    }

    /**
     * get StatusBar height.
     * @return status bar size
     */
    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val toolbar = findViewById(R.id.main_tool_bar) as Toolbar?

        toolBarHeight = toolbar!!.height
    }

    override fun onSurfaceChange() {
        moireView!!.loadLines(
                mainPresenter.loadTypesData(ys.moire.common.config.LINE_A()),
                mainPresenter.loadTypesData(ys.moire.common.config.LINE_B())
        )
    }

    companion object {

        private val TAG = "MainActivity"

        /** RequestCode constant  */
        private val INTENT_FOR_SETTING = 1
        private val INTENT_FOR_ABOUT = 2
        private val INTENT_FOR_OTHER = 3

        private val LINE_A = 0
        private val LINE_B = 1
    }
}