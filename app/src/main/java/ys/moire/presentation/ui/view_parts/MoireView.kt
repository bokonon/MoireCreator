package ys.moire.presentation.ui.view_parts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import ys.moire.BuildConfig
import ys.moire.presentation.ui.view_parts.type.*

class MoireView(
        /** Context  */
        @get:JvmName("getContext_") private val context: Context, private var layoutWidth: Int, private var layoutHeight: Int,
        /** draw type  */
        var type: Int) : SurfaceView(context), SurfaceHolder.Callback {

    /** Line A  */
    private var aLines: Lines? = null
    /** Line B  */
    private var bLines: Lines? = null
    /** Circle A  */
    private var aCircles: Circles? = null
    /** Circle B  */
    private var bCircles: Circles? = null
    /** Rectangle A  */
    private var aRectangles: Rectangles? = null
    /** Rectangle B  */
    private var bRectangles: Rectangles? = null
    /** Rectangle A  */
    private var aHearts: Hearts? = null
    /** Rectangle B  */
    private var bHearts: Hearts? = null
    /** Synapse A  */
    private var aSynapses: Synapses? = null
    /** Synapse B  */
    private var bSynapses: Synapses? = null
    /** CustomLines A  */
    private var aCustomLines: CustomLines? = null
    /** CustomLines B  */
    private var bCustomLines: CustomLines? = null
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
                ys.moire.common.config.TYPE_LINE() -> {
                    // check line for loop.
                    aLines!!.checkOutOfRange(layoutWidth)
                    bLines!!.checkOutOfRange(layoutWidth)
                    if (!isPause) {
                        aLines!!.move(LINE_A)
                        bLines!!.move(LINE_B)
                    }
                    aLines!!.draw(canvas)
                    bLines!!.draw(canvas)
                }
                ys.moire.common.config.TYPE_CIRCLE() -> {
                    aCircles!!.checkOutOfRange(layoutWidth)
                    bCircles!!.checkOutOfRange(layoutWidth)
                    if (!isPause) {
                        aCircles!!.move(LINE_A)
                        bCircles!!.move(LINE_B)
                    }
                    aCircles!!.draw(canvas)
                    bCircles!!.draw(canvas)
                }
                ys.moire.common.config.TYPE_RECT() -> {
                    aRectangles!!.checkOutOfRange(layoutWidth)
                    bRectangles!!.checkOutOfRange(layoutWidth)
                    if (!isPause) {
                        aRectangles!!.move(LINE_A)
                        bRectangles!!.move(LINE_B)
                    }
                    aRectangles!!.draw(canvas)
                    bRectangles!!.draw(canvas)
                }
                ys.moire.common.config.TYPE_HEART() -> {
                    aHearts!!.checkOutOfRange(layoutWidth)
                    bHearts!!.checkOutOfRange(layoutWidth)
                    if (!isPause) {
                        aHearts!!.move(LINE_A)
                        bHearts!!.move(LINE_B)
                    }
                    aHearts!!.draw(canvas)
                    bHearts!!.draw(canvas)
                }
                ys.moire.common.config.TYPE_SYNAPSE() -> {
                    aSynapses!!.checkOutOfRange(layoutWidth)
                    bSynapses!!.checkOutOfRange(layoutWidth)
                    if (!isPause) {
                        aSynapses!!.move(LINE_A)
                        bSynapses!!.move(LINE_B)
                    }
                    aSynapses!!.draw(canvas)
                    bSynapses!!.draw(canvas)
                }
                ys.moire.common.config.TYPE_ORIGINAL() -> {
                    aCustomLines!!.checkOutOfRange(LINE_A, layoutWidth)
                    bCustomLines!!.checkOutOfRange(LINE_B, layoutWidth)
                    if (!isPause) {
                        aCustomLines!!.move(LINE_A)
                        bCustomLines!!.move(LINE_B)
                    }
                    aCustomLines!!.draw(canvas)
                    bCustomLines!!.draw(canvas)
                }
                else -> {
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
        when (type) {
            ys.moire.common.config.TYPE_LINE() -> {
                aLines!!.draw(canvas)
                bLines!!.draw(canvas)
            }
            ys.moire.common.config.TYPE_CIRCLE() -> {
                aCircles!!.draw(canvas)
                bCircles!!.draw(canvas)
            }
            ys.moire.common.config.TYPE_RECT() -> {
                aRectangles!!.draw(canvas)
                bRectangles!!.draw(canvas)
            }
            ys.moire.common.config.TYPE_HEART() -> {
                aHearts!!.draw(canvas)
                bHearts!!.draw(canvas)
            }
            ys.moire.common.config.TYPE_SYNAPSE() -> {
                aSynapses!!.draw(canvas)
                bSynapses!!.draw(canvas)
            }
            ys.moire.common.config.TYPE_ORIGINAL() -> {
                aCustomLines!!.draw(canvas)
                bCustomLines!!.draw(canvas)
            }
            else -> {
            }
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        handler!!.removeCallbacks(drawRunnable)
    }

    fun setBgColor(color: Int) {
        colorOfBg = color
    }

    fun setMoireType(t: Int) {
        type = t
    }

    /** load line status  */
    fun loadLines(aTypes: BaseTypes, bTypes: BaseTypes) {
        when (type) {
            ys.moire.common.config.TYPE_LINE() -> {
                aLines = Lines()
                aLines!!.loadData(aTypes)
                bLines = Lines()
                bLines!!.loadData(bTypes)
                aLines!!.init(LINE_A, layoutWidth, layoutHeight)
                bLines!!.init(LINE_B, layoutWidth, layoutHeight)
            }
            ys.moire.common.config.TYPE_CIRCLE() -> {
                aCircles = Circles()
                aCircles!!.loadData(aTypes)
                bCircles = Circles()
                bCircles!!.loadData(bTypes)
                aCircles!!.init(LINE_A, layoutWidth, layoutHeight)
                bCircles!!.init(LINE_B, layoutWidth, layoutHeight)
            }
            ys.moire.common.config.TYPE_RECT() -> {
                aRectangles = Rectangles()
                aRectangles!!.loadData(aTypes)
                bRectangles = Rectangles()
                bRectangles!!.loadData(bTypes)
                // TODO change dynamic
                val maxTopLength = layoutHeight / 3f * 2
                val maxBottomLength = layoutHeight / 3f * 2
                aRectangles!!.init(LINE_A, layoutWidth, layoutHeight, maxTopLength, maxBottomLength)
                bRectangles!!.init(LINE_B, layoutWidth, layoutHeight, maxTopLength, maxBottomLength)
            }
            ys.moire.common.config.TYPE_HEART() -> {
                aHearts = Hearts()
                aHearts!!.loadData(aTypes)
                bHearts = Hearts()
                bHearts!!.loadData(bTypes)
                aHearts!!.init(LINE_A, layoutWidth, layoutHeight)
                bHearts!!.init(LINE_B, layoutWidth, layoutHeight)
            }
            ys.moire.common.config.TYPE_SYNAPSE() -> {
                aSynapses = Synapses()
                aSynapses!!.loadData(aTypes)
                bSynapses = Synapses()
                bSynapses!!.loadData(bTypes)
                aSynapses!!.init(LINE_A, layoutWidth, layoutHeight)
                bSynapses!!.init(LINE_B, layoutWidth, layoutHeight)
            }
            ys.moire.common.config.TYPE_ORIGINAL() -> {
                aCustomLines = CustomLines()
                aCustomLines!!.loadData(aTypes)
                bCustomLines = CustomLines()
                bCustomLines!!.loadData(bTypes)
                aCustomLines!!.init(LINE_A, layoutWidth, layoutHeight)
                bCustomLines!!.init(LINE_B, layoutWidth, layoutHeight)
            }
            else -> {
            }
        }
    }

    /**
     * add touch value.
     */
    fun addTouchValue(which: Int, valX: Int, valY: Int) {
        when (type) {
            ys.moire.common.config.TYPE_LINE() -> if (which == LINE_A) {
                aLines!!.addTouchVal(valX, valY)
            } else if (which == LINE_B) {
                bLines!!.addTouchVal(valX, valY)
            }
            ys.moire.common.config.TYPE_CIRCLE() -> if (which == LINE_A) {
                aCircles!!.addTouchVal(valX, valY)
            } else if (which == LINE_B) {
                bCircles!!.addTouchVal(valX, valY)
            }
            ys.moire.common.config.TYPE_RECT() -> if (which == LINE_A) {
                aRectangles!!.addTouchVal(valX, valY)
            } else if (which == LINE_B) {
                bRectangles!!.addTouchVal(valX, valY)
            }
            ys.moire.common.config.TYPE_HEART() -> if (which == LINE_A) {
                aHearts!!.addTouchVal(valX, valY)
            } else if (which == LINE_B) {
                bHearts!!.addTouchVal(valX, valY)
            }
            ys.moire.common.config.TYPE_SYNAPSE() -> if (which == LINE_A) {
                aSynapses!!.addTouchVal(valX, valY)
            } else if (which == LINE_B) {
                bSynapses!!.addTouchVal(valX, valY)
            }
            ys.moire.common.config.TYPE_ORIGINAL() -> if (which == LINE_A) {
                // add nothing for customLine
            } else if (which == LINE_B) {
                // add nothing for customLine
            }
            else -> {
            }
        }
    }

    fun drawOriginalLine(which: Int, valX: Float, valY: Float, moveCount: Int) {
        if (which == LINE_A) {
            aCustomLines!!.drawOriginalLine(layoutWidth, valX, valY, moveCount)
        } else if (which == LINE_B) {
            bCustomLines!!.drawOriginalLine(layoutWidth, valX, valY, moveCount)
        }
    }

    /**
     * set touch mode.
     * @param isOnTouch is on touch
     */
    fun setOnTouchMode(which: Int, isOnTouch: Boolean) {
        when (type) {
            ys.moire.common.config.TYPE_LINE() -> if (which == LINE_A) {
                aLines!!.setOnTouchMode(isOnTouch)
            } else if (which == LINE_B) {
                bLines!!.setOnTouchMode(isOnTouch)
            }
            ys.moire.common.config.TYPE_CIRCLE() -> if (which == LINE_A) {
                aCircles!!.setOnTouchMode(isOnTouch)
            } else if (which == LINE_B) {
                bCircles!!.setOnTouchMode(isOnTouch)
            }
            ys.moire.common.config.TYPE_RECT() -> if (which == LINE_A) {
                aRectangles!!.setOnTouchMode(isOnTouch)
            } else if (which == LINE_B) {
                bRectangles!!.setOnTouchMode(isOnTouch)
            }
            ys.moire.common.config.TYPE_HEART() -> if (which == LINE_A) {
                aHearts!!.setOnTouchMode(isOnTouch)
            } else if (which == LINE_B) {
                bHearts!!.setOnTouchMode(isOnTouch)
            }
            ys.moire.common.config.TYPE_SYNAPSE() -> if (which == LINE_A) {
                aSynapses!!.setOnTouchMode(isOnTouch)
            } else if (which == LINE_B) {
                bSynapses!!.setOnTouchMode(isOnTouch)
            }
            ys.moire.common.config.TYPE_ORIGINAL() -> if (which == LINE_A) {
                aCustomLines!!.setOnTouchMode(isOnTouch)
            } else if (which == LINE_B) {
                bCustomLines!!.setOnTouchMode(isOnTouch)
            }
            else -> {
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
