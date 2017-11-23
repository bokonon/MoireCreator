package ys.moire.presentation.ui.viewparts.type

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import ys.moire.BuildConfig

/**
 * Heart set class.
 */
class Hearts : BaseTypes() {

    /** Heart array  */
    private var heart: Array<Heart?>? = null

    override fun init(whichLine: Int,
             layoutWidth: Int,
             layoutHeight: Int) {
        heart = arrayOfNulls<Heart>(number)
        for (i in 0..number - 1) {
            heart!![i] = Heart()
            val heartWidth = layoutWidth.toFloat() / number * (i + 1)
            val heartHeight = layoutHeight.toFloat() * 2 / 3 / number * (i + 1)
            if (whichLine == BaseTypes.LINE_A) {
                heart!![i]!!.init(0.toFloat(), layoutHeight.toFloat() * 5 / 18, heartWidth, heartHeight)
            } else if (whichLine == BaseTypes.LINE_B) {
                heart!![i]!!.init(layoutWidth.toFloat(), layoutHeight.toFloat() * 2 / 3, heartWidth, heartHeight)
            }
        }
    }

    public override fun checkOutOfRange(layoutWidth: Int) {
        if (heart!![number - 1]!!.rightTop[0]!!.x < 0) {
            // disappear for less than 0
            for (i in 0..number - 1) {
                heart!![i]!!.moveX(
                        layoutWidth + layoutWidth.toFloat() / 2,
                        heart!![i]!!.centerTop.y + (heart!![i]!!.height / 2 - heart!![i]!!.height / 5))
            }
        } else if (layoutWidth < heart!![number - 1]!!.leftTop[1]!!.x) {
            // disappear for more than width
            for (i in 0..number - 1) {
                heart!![i]!!.moveX(
                        - layoutWidth.toFloat() / 2,
                        heart!![i]!!.centerTop.y + (heart!![i]!!.height / 2 - heart!![i]!!.height / 5))
            }
        }
    }

    public override fun move(whichLine: Int) {
        if (onTouch) {
            return
        }
        if (whichLine == BaseTypes.LINE_A) {
            for (i in 0..number - 1) {
                heart!![i]!!.autoMove(dx)
            }
        } else if (whichLine == BaseTypes.LINE_B) {
            for (i in 0..number - 1) {
                heart!![i]!!.autoMove(-dx)
            }
        }
    }

    public override fun draw(canvas: Canvas) {
        for (i in 0..number - 1) {
            if (BuildConfig.DEBUG) {
                // make first line to blue color for only debug
                if (i == 0) {
                    val p = Paint()
                    p.style = Paint.Style.STROKE
                    p.color = Color.BLUE
                    p.strokeWidth = thick.toFloat()
                    canvas.drawPath(heart!![i]!!.path, p)
                    continue
                }
                // make last line to red color for only debug build
                if (i == number - 1) {
                    val p = Paint()
                    p.style = Paint.Style.STROKE
                    p.color = Color.RED
                    p.strokeWidth = thick.toFloat()
                    canvas.drawPath(heart!![i]!!.path, p)
                    continue
                }
            }
            canvas.drawPath(heart!![i]!!.path, paint!!)
        }
    }

    /**
     * add touch value.
     * @param valX dx
     */
    public override fun addTouchVal(valX: Int, valY: Int) {
        for (i in 0..number - 1) {
            heart!![i]!!.addTouchVal(valX, valY)
        }
    }

    companion object {
        private val TAG = "hearts"
    }
}