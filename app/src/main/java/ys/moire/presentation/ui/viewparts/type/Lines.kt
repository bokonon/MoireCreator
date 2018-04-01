package ys.moire.presentation.ui.viewparts.type

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import ys.moire.BuildConfig

/**
 * line set class.
 */
class Lines : BaseTypes() {

    /** line array  */
    private lateinit var line: Array<Line>

    public override fun init(whichLine: Int, layoutWidth: Int, layoutHeight: Int) {

        line = if (whichLine == BaseTypes.LINE_A) {
            Array(number, {
                Line(layoutHeight, slope, (layoutWidth + slope) / number * it, 0)
            })
        } else {
            Array(number, {
                Line(layoutHeight, slope, (layoutWidth + slope) / number * it, slope)
            })
        }
    }

    public override fun checkOutOfRange(layoutWidth: Int) {
        for (i in 0 until number) {
            line[i].checkOutOfRange(layoutWidth, slope)
        }
    }

    public override fun move(whichLine: Int) {
        if (onTouch) {
            return
        }
        if (whichLine == BaseTypes.LINE_A) {
            for (i in 0 until number) {
                line[i].autoMove(dx)
            }

        } else if (whichLine == BaseTypes.LINE_B) {
            for (j in 0 until number) {
                line[j].autoMove(-dx)
            }
        }
    }

    public override fun draw(canvas: Canvas) {
        for (i in 0 until number) {
            if (BuildConfig.DEBUG) {
                // make first line to blue color for only debug
                if (i == 0) {
                    val p = Paint()
                    p.style = Paint.Style.STROKE
                    p.color = Color.BLUE
                    p.strokeWidth = thick.toFloat()
                    canvas.drawLine(line[i].topX, line[i].topY, line[i].bottomX,
                            line[i].bottomY, p)
                    continue
                }
                // make last line to red color for only debug
                if (i == number - 1) {
                    val p = Paint()
                    p.style = Paint.Style.STROKE
                    p.color = Color.RED
                    p.strokeWidth = thick.toFloat()
                    canvas.drawLine(line[i].topX, line[i].topY, line[i].bottomX,
                            line[i].bottomY, p)
                    continue
                }
            }
            canvas.drawLine(line[i].topX, line[i].topY, line[i].bottomX,
                    line[i].bottomY, paint)
        }
    }

    /**
     * add moving value.
     * @param valX dx
     */
    public override fun addTouchVal(valX: Int, valY: Int) {
        for (i in 0 until number) {
            line[i].addTouchVal(valX, 0)
        }
    }

    companion object {

        private const val TAG = "Lines"
    }

}
