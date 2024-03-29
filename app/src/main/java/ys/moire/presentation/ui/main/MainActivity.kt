package ys.moire.presentation.ui.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ys.moire.BuildConfig
import ys.moire.MoireApplication
import ys.moire.R
import ys.moire.presentation.presenter.main.MainPresenter
import ys.moire.presentation.ui.about.AboutActivity
import ys.moire.presentation.ui.base.BaseActivity
import ys.moire.presentation.ui.other.OtherActivity
import ys.moire.presentation.ui.preferences.PreferencesActivity
import ys.moire.presentation.ui.viewparts.MoireView
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity(), View.OnClickListener, MoireView.OnSurfaceChange {

    companion object {
        private val TAG: String = MainActivity::class.java.simpleName

        /** RequestCode constant  */
        private const val INTENT_FOR_SETTING = 1
        private const val INTENT_FOR_ABOUT = 2
        private const val INTENT_FOR_OTHER = 3

        private const val LINE_A = 0
        private const val LINE_B = 1

        private const val PERMISSION_REQUEST_CODE = 1
    }

    @Inject
    lateinit var mainPresenter: MainPresenter

    /** View  */
    private lateinit var moireView: MoireView
    /** Touch A Button  */
    private lateinit var aTouchSelectedButton: Button
    /** Touch B Button  */
    private lateinit var bTouchSelectedButton: Button
    /** Pause Button  */
    private lateinit var pauseImageButton: ImageButton
    /** ScreenShot Button  */
    private lateinit var screenShotImageButton: ImageButton

    /** Line Mode（AorB）  */
    private var touchLineMode: Int = 0

    private var statusBarHeight: Int = 0
    private var toolBarHeight: Int = 0

    private var timer: Timer? = null
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_main)

        MoireApplication.component.inject(this)

        // set toolbar
        val toolbar = findViewById<Toolbar>(R.id.main_tool_bar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val metrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(metrics)
        val layoutWidth = metrics.widthPixels
        statusBarHeight = getStatusBarHeight()
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "statusBarHeight : $statusBarHeight")
        }
        val layoutHeight = metrics.heightPixels - statusBarHeight

        moireView = MoireView(this, layoutWidth, layoutHeight, mainPresenter.moireType)
        moireView.setBgColor(mainPresenter.bgColor)

        val linearLayout = findViewById<LinearLayout>(R.id.parent_linear_layout)
        linearLayout?.addView(moireView)

        initToolBarViews(toolbar!!)

        mainPresenter.setMoireView(moireView)
        mainPresenter.onCreate()
    }

    override fun onResume() {
        super.onResume()

        mainPresenter.onResume()
        moireView.setOnBackground(false)
        startTimer()
    }

    override fun onPause() {
        moireView.setOnBackground(true)
        mainPresenter.onPause()
        stopTimer()

        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var contentType = ""
        when (item.itemId) {
            R.id.menu_settings -> {
                val settingIntent = Intent(this, PreferencesActivity::class.java)
                startActivityForResult(settingIntent, INTENT_FOR_SETTING)
                contentType = "setting"
            }
            R.id.menu_about -> {
                val aboutIntent = Intent(this, AboutActivity::class.java)
                startActivityForResult(aboutIntent, INTENT_FOR_ABOUT)
                contentType = "about"
            }
            R.id.menu_other -> {
                val otherIntent = Intent(this, OtherActivity::class.java)
                startActivityForResult(otherIntent, INTENT_FOR_OTHER)
                contentType = "other"
            }
            else -> {
            }
        }
        postLogEvent(contentType)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == Activity.RESULT_OK) {
            // from preference screen
            when (requestCode) {
                INTENT_FOR_SETTING -> {
                    moireView.setMoireType(mainPresenter.moireType)
                    moireView.setBgColor(mainPresenter.bgColor)

                    moireView.loadLines(
                            mainPresenter.loadTypesData(ys.moire.common.config.LINE_A()),
                            mainPresenter.loadTypesData(ys.moire.common.config.LINE_B())
                    )
                }
                INTENT_FOR_ABOUT -> {
                    // from about screen
                    // NOP
                }
                INTENT_FOR_OTHER -> {
                    // from other screen
                    // NOP
                }
            }
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
                // TouchMode on
                moireView.setOnTouchMode(touchLineMode, true)
                firstTouchX = e.x.toInt()
                firstTouchY = e.y.toInt()
                if (moireView.type == ys.moire.common.config.TypeEnum.LINE) {
                    // init dx dy
                    touchMoveX = 0
                    tempMoveX = 0
                } else if (moireView.type == ys.moire.common.config.TypeEnum.CIRCLE
                        || moireView.type == ys.moire.common.config.TypeEnum.RECT
                        || moireView.type == ys.moire.common.config.TypeEnum.HEART
                        || moireView.type == ys.moire.common.config.TypeEnum.SYNAPSE
                        || moireView.type == ys.moire.common.config.TypeEnum.OCTAGON
                        || moireView.type == ys.moire.common.config.TypeEnum.FLOWER) {
                    // init dx dy
                    touchMoveX = 0
                    touchMoveY = 0
                    tempMoveX = 0
                    tempMoveY = 0
                } else if (moireView.type == ys.moire.common.config.TypeEnum.ORIGINAL) {
                    moveCount = 0
                }
            }
            MotionEvent.ACTION_MOVE -> if (Math.abs(currentX - firstTouchX) >= 2 || Math.abs(currentY - firstTouchY) >= 2) {
                if (moireView.type == ys.moire.common.config.TypeEnum.LINE) {
                    touchMoveX = currentX - firstTouchX - tempMoveX
                    moireView.addTouchValue(touchLineMode, touchMoveX, 0)

                    tempMoveX = currentX - firstTouchX
                } else if (moireView.type == ys.moire.common.config.TypeEnum.CIRCLE
                        || moireView.type == ys.moire.common.config.TypeEnum.RECT
                        || moireView.type == ys.moire.common.config.TypeEnum.HEART
                        || moireView.type == ys.moire.common.config.TypeEnum.SYNAPSE
                        || moireView.type == ys.moire.common.config.TypeEnum.OCTAGON
                        || moireView.type == ys.moire.common.config.TypeEnum.FLOWER) {
                    touchMoveX = currentX - firstTouchX - tempMoveX
                    touchMoveY = currentY - firstTouchY - tempMoveY
                    moireView.addTouchValue(touchLineMode, touchMoveX, touchMoveY)

                    tempMoveX = currentX - firstTouchX
                    tempMoveY = currentY - firstTouchY
                } else if (moireView.type == ys.moire.common.config.TypeEnum.ORIGINAL) {
                    moireView.drawOriginalLine(touchLineMode, e.x, e.y - statusBarHeight.toFloat() - toolBarHeight.toFloat(), moveCount)
                    moveCount++
                }
                // draw
                moireView.drawFrame()
            }
            MotionEvent.ACTION_UP -> {
                // TouchMode off
                moireView.setOnTouchMode(touchLineMode, false)
                if (moireView.type == ys.moire.common.config.TypeEnum.LINE) {
                    // init dx dy
                    touchMoveX = 0
                    tempMoveX = 0
                } else if (moireView.type == ys.moire.common.config.TypeEnum.CIRCLE
                        || moireView.type == ys.moire.common.config.TypeEnum.RECT
                        || moireView.type == ys.moire.common.config.TypeEnum.HEART
                        || moireView.type == ys.moire.common.config.TypeEnum.SYNAPSE
                        || moireView.type == ys.moire.common.config.TypeEnum.OCTAGON
                        || moireView.type == ys.moire.common.config.TypeEnum.FLOWER) {
                    // init dx dy
                    touchMoveX = 0
                    touchMoveY = 0
                    tempMoveX = 0
                    tempMoveY = 0
                } else if (moireView.type == ys.moire.common.config.TypeEnum.ORIGINAL) {
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
        var contentType = ""
        if (view === aTouchSelectedButton) {
            touchLineMode = LINE_A
            aTouchSelectedButton.isSelected = true
            bTouchSelectedButton.isSelected = false
            contentType = "a button"
        } else if (view === bTouchSelectedButton) {
            touchLineMode = LINE_B
            aTouchSelectedButton.isSelected = false
            bTouchSelectedButton.isSelected = true
            contentType = "b button"
        } else if (view === pauseImageButton) {
            if (moireView.isPause) {
                moireView.pause(false)
                pauseImageButton.setImageResource(R.drawable.ic_pause_black_24dp)
                contentType = "start"
            } else {
                moireView.pause(true)
                pauseImageButton.setImageResource(R.drawable.ic_play_arrow_black_24dp)
                contentType = "pause"
            }
        } else if (view === screenShotImageButton) {
            if (isWriteExternalStoragePermission()) {
                mainPresenter.captureCanvas()
            } else {
                requestPermission()
            }
            contentType = "screen shot"
        }
        postLogEvent(contentType)
    }

    private fun initToolBarViews(toolbar: Toolbar) {
        aTouchSelectedButton = toolbar.findViewById(R.id.a_button)
        bTouchSelectedButton = toolbar.findViewById(R.id.b_button)
        pauseImageButton = toolbar.findViewById(R.id.pause_button)
        screenShotImageButton = toolbar.findViewById(R.id.camera_button)

        aTouchSelectedButton.setOnClickListener(this)
        bTouchSelectedButton.setOnClickListener(this)
        pauseImageButton.setOnClickListener(this)
        screenShotImageButton.setOnClickListener(this)

        // set A button at first.
        aTouchSelectedButton.isSelected = true
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
        val toolbar = findViewById<Toolbar>(R.id.main_tool_bar)

        toolBarHeight = toolbar!!.height
    }

    override fun onSurfaceChange() {
        moireView.loadLines(
                mainPresenter.loadTypesData(ys.moire.common.config.LINE_A()),
                mainPresenter.loadTypesData(ys.moire.common.config.LINE_B())
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mainPresenter.captureCanvas()
                } else {
                    mainPresenter.showToast(getString(R.string.message_capture_failed))
                }
            }
        }
    }

    private fun startTimer() {
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                handler.post {
                    moireView.drawFrame()
                }
            }
        }, 0, 100)

    }

    private fun stopTimer() {
        timer?.cancel()
        timer = null
    }

    private fun isWriteExternalStoragePermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (ContextCompat.checkSelfPermission(
                                this,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    return true
                }
            } else {
                if (ContextCompat.checkSelfPermission(
                                this,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    return true
                }
            }
            return false
        }
        return true
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE)
        }
    }

}