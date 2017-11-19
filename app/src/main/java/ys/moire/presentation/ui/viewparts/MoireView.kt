package ys.moire.presentation.ui.viewparts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import ys.moire.BuildConfig
import ys.moire.common.config.TypeEnum
import ys.moire.presentation.ui.viewparts.type.*

class MoireView(
        /** Context  */
        @get:JvmName("getContext_") private val context: Context, private var layoutWidth: Int, private var layoutHeight: Int,
        /** draw type  */
        var type: TypeEnum) : SurfaceView(context), SurfaceHolder.Callback {

    private var aBaseTypes: BaseTypes? = null
    private var bBaseTypes: BaseTypes? = null

    /** Background Color  */
    private var colorOfBg = Color.WHITE

    /**
     * return is pause.
     * @return boolean is on pause
     */
    var isPause = false
        private set

    private var isOnBackground = false

    /** Handler  */
    @get:JvmName("getHandler_") private var handler: Handler? = null
    /** Thread  */
    private val drawRunnable = Runnable { drawFrame() }

    interface OnSurfaceChange {
        fun onSurfaceChange()
    }

    private var listener: OnSurfaceChange? = null

    init {
        holder.addCallback(this)

        if (context is OnSurfaceChange) {
            listener = context
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        handler = Handler()

        // Do on SurfaceChanged for height is changed.
        //        linesLoad();
        //        drawFrame();
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "surfaceChanged height : " + height)
        }
        if (layoutHeight != height) {
            layoutWidth = width
            layoutHeight = height

            if (listener != null) {
                listener!!.onSurfaceChange()
            }
        }
        drawFrame()
    }

    fun drawFrame() {
        val canvas = holder.lockCanvas()
        if (canvas != null) {
            canvas.drawColor(colorOfBg)
            when (type) {
                ys.moire.common.config.TypeEnum.LINE,
                ys.moire.common.config.TypeEnum.CIRCLE,
                ys.moire.common.config.TypeEnum.RECT,
                ys.moire.common.config.TypeEnum.HEART,
                ys.moire.common.config.TypeEnum.SYNAPSE -> {
                    // check line for loop.
                    aBaseTypes!!.checkOutOfRange(layoutWidth)
                    bBaseTypes!!.checkOutOfRange(layoutWidth)
                    if (!isPause) {
                        aBaseTypes!!.move(LINE_A)
                        bBaseTypes!!.move(LINE_B)
                    }
                    aBaseTypes!!.draw(canvas)
                    bBaseTypes!!.draw(canvas)
                }
                ys.moire.common.config.TypeEnum.ORIGINAL -> {
                    aBaseTypes!!.checkOutOfRange(LINE_A, layoutWidth)
                    bBaseTypes!!.checkOutOfRange(LINE_B, layoutWidth)
                    if (!isPause) {
                        aBaseTypes!!.move(LINE_A)
                        bBaseTypes!!.move(LINE_B)
                    }
                    aBaseTypes!!.draw(canvas)
                    bBaseTypes!!.draw(canvas)
                }
            }
            holder.unlockCanvasAndPost(canvas)
            handler!!.removeCallbacks(drawRunnable)
            if (!isPause && !isOnBackground) {
                handler!!.postDelayed(drawRunnable, 100)
            }
        }
    }

    fun captureCanvas(canvas: Canvas) {
        canvas.drawColor(colorOfBg)
        aBaseTypes!!.draw(canvas)
        bBaseTypes!!.draw(canvas)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        handler!!.removeCallbacks(drawRunnable)
    }

    fun setBgColor(color: Int) {
        colorOfBg = color
    }

    fun setMoireType(t: TypeEnum) {
        type = t
    }

    /** load line status  */
    fun loadLines(aTypes: BaseTypes, bTypes: BaseTypes) {
        when (type) {
            ys.moire.common.config.TypeEnum.LINE -> {
                aBaseTypes = Lines()
                aBaseTypes!!.loadData(aTypes)
                bBaseTypes = Lines()
                bBaseTypes!!.loadData(bTypes)
                aBaseTypes!!.init(LINE_A, layoutWidth, layoutHeight)
                bBaseTypes!!.init(LINE_B, layoutWidth, layoutHeight)
            }
            ys.moire.common.config.TypeEnum.CIRCLE -> {
                aBaseTypes = Circles()
                aBaseTypes!!.loadData(aTypes)
                bBaseTypes = Circles()
                bBaseTypes!!.loadData(bTypes)
                aBaseTypes!!.init(LINE_A, layoutWidth, layoutHeight)
                bBaseTypes!!.init(LINE_B, layoutWidth, layoutHeight)
            }
            ys.moire.common.config.TypeEnum.RECT -> {
                aBaseTypes = Rectangles()
                aBaseTypes!!.loadData(aTypes)
                bBaseTypes = Rectangles()
                bBaseTypes!!.loadData(bTypes)
                // TODO change dynamic
                val maxTopLength = layoutHeight / 3f * 2
                val maxBottomLength = layoutHeight / 3f * 2
                aBaseTypes!!.init(LINE_A, layoutWidth, layoutHeight, maxTopLength, maxBottomLength)
                bBaseTypes!!.init(LINE_B, layoutWidth, layoutHeight, maxTopLength, maxBottomLength)
            }
            ys.moire.common.config.TypeEnum.HEART -> {
                aBaseTypes = Hearts()
                aBaseTypes!!.loadData(aTypes)
                bBaseTypes = Hearts()
                bBaseTypes!!.loadData(bTypes)
                aBaseTypes!!.init(LINE_A, layoutWidth, layoutHeight)
                bBaseTypes!!.init(LINE_B, layoutWidth, layoutHeight)
            }
            ys.moire.common.config.TypeEnum.SYNAPSE -> {
                aBaseTypes = Synapses()
                aBaseTypes!!.loadData(aTypes)
                bBaseTypes = Synapses()
                bBaseTypes!!.loadData(bTypes)
                aBaseTypes!!.init(LINE_A, layoutWidth, layoutHeight)
                bBaseTypes!!.init(LINE_B, layoutWidth, layoutHeight)
            }
            ys.moire.common.config.TypeEnum.ORIGINAL -> {
                aBaseTypes = CustomLines()
                aBaseTypes!!.loadData(aTypes)
                bBaseTypes = CustomLines()
                bBaseTypes!!.loadData(bTypes)
                aBaseTypes!!.init(LINE_A, layoutWidth, layoutHeight)
                bBaseTypes!!.init(LINE_B, layoutWidth, layoutHeight)
            }
        }
    }

    /**
     * add touch value.
     */
    fun addTouchValue(which: Int, valX: Int, valY: Int) {
        when (type) {
            ys.moire.common.config.TypeEnum.LINE,
            ys.moire.common.config.TypeEnum.CIRCLE,
            ys.moire.common.config.TypeEnum.RECT,
            ys.moire.common.config.TypeEnum.HEART,
            ys.moire.common.config.TypeEnum.SYNAPSE
            -> if (which == LINE_A) {
                aBaseTypes!!.addTouchVal(valX, valY)
            } else if (which == LINE_B) {
                bBaseTypes!!.addTouchVal(valX, valY)
            }
            ys.moire.common.config.TypeEnum.ORIGINAL -> if (which == LINE_A) {
                // add nothing for customLine
            } else if (which == LINE_B) {
                // add nothing for customLine
            }
        }
    }

    fun drawOriginalLine(which: Int, valX: Float, valY: Float, moveCount: Int) {
        if (which == LINE_A) {
            aBaseTypes!!.drawOriginalLine(layoutWidth, valX, valY, moveCount)
        } else if (which == LINE_B) {
            bBaseTypes!!.drawOriginalLine(layoutWidth, valX, valY, moveCount)
        }
    }

    /**
     * set touch mode.
     * @param isOnTouch is on touch
     */
    fun setOnTouchMode(which: Int, isOnTouch: Boolean) {
        when (type) {
            ys.moire.common.config.TypeEnum.LINE,
            ys.moire.common.config.TypeEnum.CIRCLE,
            ys.moire.common.config.TypeEnum.RECT,
            ys.moire.common.config.TypeEnum.HEART,
            ys.moire.common.config.TypeEnum.SYNAPSE,
            ys.moire.common.config.TypeEnum.ORIGINAL
            -> if (which == LINE_A) {
                aBaseTypes!!.setOnTouchMode(isOnTouch)
            } else if (which == LINE_B) {
                bBaseTypes!!.setOnTouchMode(isOnTouch)
            }
        }
    }

    /**
     * pause.
     * @param pause
     */
    fun pause(pause: Boolean) {
        isPause = pause
        if (!pause) {
            if (handler != null) {
                handler!!.postDelayed(drawRunnable, 100)
            }
        }
    }

    /**
     * is on Background.
     * @param onBack is on Background
     */
    fun setOnBackground(onBack: Boolean) {
        isOnBackground = onBack
        if (!onBack) {
            if (handler != null) {
                handler!!.postDelayed(drawRunnable, 100)
            }
        }
    }

    companion object {

        private val TAG = "MoireView"

        private val LINE_A = 0
        private val LINE_B = 1
    }

}
