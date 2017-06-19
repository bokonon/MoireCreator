package ys.moire.presentation.ui.view_parts.type

import android.graphics.Canvas
import android.util.Log
import ys.moire.BuildConfig

/**
 * circle set class.
 */
class Circles : BaseTypes() {

    /** Circle array  */
    private var circle: Array<Circle?>? = null

    public override fun init(whichLine: Int, layoutWidth: Int, layoutHeight: Int) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Circles layoutHeight : " + layoutHeight)
        }
        circle = arrayOfNulls<Circle>(number)
        for (i in 0..number - 1) {
            circle!![i] = Circle()
            if (whichLine == BaseTypes.LINE_A) {
                circle!![i]!!.init(0f, layoutHeight / 3f, layoutHeight.toFloat() / 3f / number.toFloat() * i + 4)
            } else if (whichLine == BaseTypes.LINE_B) {
                circle!![i]!!.init(layoutWidth.toFloat(), layoutHeight * (2 / 3f), layoutHeight.toFloat() / 3f / number.toFloat() * i + 4)
            }
        }
    }

    public override fun checkOutOfRange(layoutWidth: Int) {
        // check only outside circle.
        if (circle!![number - 1]!!.x < -circle!![number - 1]!!.r) {
            // disappear for minus x val
            for (i in 0..number - 1) {
                circle!![i]!!.x = layoutWidth + circle!![number - 1]!!.r
            }
        } else if (layoutWidth + circle!![number - 1]!!.r < circle!![number - 1]!!.x) {
            // disappear for plus x value
            for (i in 0..number - 1) {
                circle!![i]!!.x = -circle!![number - 1]!!.r
            }
        }
    }

    public override fun move(whichLine: Int) {
        if (onTouch) {
            return
        }
        if (whichLine == BaseTypes.LINE_A) {
            for (i in 0..number - 1) {
                circle!![i]!!.autoMove(dx)
            }

        } else if (whichLine == BaseTypes.LINE_B) {
            for (j in 0..number - 1) {
                circle!![j]!!.autoMove(-dx)
            }
        }
    }

    public override fun draw(canvas: Canvas) {
        for (i in 0..number - 1) {
            canvas.drawCircle(circle!![i]!!.x, circle!![i]!!.y, circle!![i]!!.r, paint!!)
        }
    }

    /**
     * add touch value.
     * @param valX dx
     * *
     * @param valY dy
     */
    public override fun addTouchVal(valX: Int, valY: Int) {
        for (i in 0..number - 1) {
            circle!![i]!!.addTouchVal(valX, valY)
        }
    }

    companion object {
        private val TAG = "Circles"
    }
}
