package ys.moire.presentation.ui.view_parts.type

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

import ys.moire.BuildConfig

/**
 * line set class.
 */
class Lines : BaseTypes() {

    /** line array  */
    private var line: Array<Line?>? = null

    public override fun init(whichLine: Int, layoutWidth: Int, layoutHeight: Int) {
        line = arrayOfNulls<Line>(number)
        for (i in 0..number - 1) {
            line!![i] = Line()
            if (whichLine == BaseTypes.LINE_A) {
                line!![i]!!.init(layoutHeight, slope, (layoutWidth + slope) / number * i, 0)
            } else if (whichLine == BaseTypes.LINE_B) {
                line!![i]!!.init(layoutHeight, slope, (layoutWidth + slope) / number * i, slope)
            }
        }
    }

    public override fun checkOutOfRange(layoutWidth: Int) {
        for (i in 0..number - 1) {
            line!![i]!!.checkOutOfRange(layoutWidth, slope)
        }
    }

    public override fun move(whichLine: Int) {
        if (onTouch) {
            return
        }
        if (whichLine == BaseTypes.LINE_A) {
            for (i in 0..number - 1) {
                line!![i]!!.autoMove(dx)
            }

        } else if (whichLine == BaseTypes.LINE_B) {
            for (j in 0..number - 1) {
                line!![j]!!.autoMove(-dx)
            }
        }
    }

    public override fun draw(canvas: Canvas) {
        for (i in 0..number - 1) {
            if (BuildConfig.DEBUG) {
                // make first line blue only debug
                if (i == 0) {
                    val p = Paint()
                    p.style = Paint.Style.STROKE
                    p.color = Color.BLUE
                    p.strokeWidth = thick.toFloat()
                    canvas.drawLine(line!![i]!!.topX, line!![i]!!.topY, line!![i]!!.bottomX,
                            line!![i]!!.bottomY, p)
                    continue
                }
                // make last line red only debug
                if (i == number - 1) {
                    val p = Paint()
                    p.style = Paint.Style.STROKE
                    p.color = Color.RED
                    p.strokeWidth = thick.toFloat()
                    canvas.drawLine(line!![i]!!.topX, line!![i]!!.topY, line!![i]!!.bottomX,
                            line!![i]!!.bottomY, p)
                    continue
                }
            }
            canvas.drawLine(line!![i]!!.topX, line!![i]!!.topY, line!![i]!!.bottomX,
                    line!![i]!!.bottomY, paint!!)
        }
    }

    /**
     * add moving value.
     * @param valX dx
     */
    public override fun addTouchVal(valX: Int, valY: Int) {
        for (i in 0..number - 1) {
            line!![i]!!.addTouchVal(valX, 0)
        }
    }

    companion object {

        private val TAG = "Lines"
    }

}
