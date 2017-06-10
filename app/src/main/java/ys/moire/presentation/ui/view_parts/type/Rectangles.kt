package ys.moire.presentation.ui.view_parts.type

import android.graphics.Canvas

/**
 * rectangle set class.
 */
class Rectangles : BaseTypes() {

    /** Rectangle array  */
    private var rectangle: Array<Rectangle?>? = null

    // Trapezoidal judgment
    private var isTrapezoid: Boolean = false

    fun init(whichLine: Int,
             layoutWidth: Int,
             layoutHeight: Int,
             maxTopLength: Float,
             maxBottomLength: Float) {
        rectangle = arrayOfNulls<Rectangle>(number)
        for (i in 0..number - 1) {
            rectangle!![i] = Rectangle()
            if (whichLine == BaseTypes.LINE_A) {
                // The upper side and the lower side are provisional values
                rectangle!![i]!!.init(0f, layoutHeight / 3f,
                        layoutHeight.toFloat() / 3f / number.toFloat() * i,
                        maxTopLength / number * i,
                        maxBottomLength / number * i)
            } else if (whichLine == BaseTypes.LINE_B) {
                rectangle!![i]!!.init(layoutWidth.toFloat(), layoutHeight * (2 / 3f),
                        layoutHeight.toFloat() / 3f / number.toFloat() * i,
                        maxTopLength / number * i,
                        maxBottomLength / number * i)
            }
        }
        isTrapezoid = maxTopLength <= maxBottomLength
    }

    public override fun checkOutOfRange(layoutWidth: Int) {
        // check only out side line
        if (isTrapezoid) {
            if (rectangle!![number - 1]!!.rightBottom.x < 0) {
                // disappear less than 0
                for (i in 0..number - 1) {
                    rectangle!![i]!!.moveX(layoutWidth + rectangle!![number - 1]!!.bottomLength / 2)
                }
            } else if (layoutWidth < rectangle!![number - 1]!!.leftBottom.x) {
                // disappear more than width
                for (i in 0..number - 1) {
                    rectangle!![i]!!.moveX(-rectangle!![number - 1]!!.bottomLength / 2)
                }
            }
        } else {
            if (rectangle!![number - 1]!!.rightTop.x < 0) {
                // disappear less than 0
                for (i in 0..number - 1) {
                    rectangle!![i]!!.moveX(layoutWidth + rectangle!![number - 1]!!.topLength / 2)
                }
            } else if (layoutWidth < rectangle!![number - 1]!!.leftTop.x) {
                // disappear more than width
                for (i in 0..number - 1) {
                    rectangle!![i]!!.moveX(-rectangle!![number - 1]!!.topLength / 2)
                }
            }
        }
    }

    public override fun move(whichLine: Int) {
        if (onTouch) {
            return
        }
        if (whichLine == BaseTypes.LINE_A) {
            for (i in 0..number - 1) {
                rectangle!![i]!!.autoMove(dx)
            }
        } else if (whichLine == BaseTypes.LINE_B) {
            for (j in 0..number - 1) {
                rectangle!![j]!!.autoMove(-dx)
            }
        }
    }

    public override fun draw(canvas: Canvas) {
        for (i in 0..number - 1) {
            canvas.drawPath(rectangle!![i]!!.path, paint!!)
        }
    }

    /**
     * add touch value.
     * @param valX dx
     */
    public override fun addTouchVal(valX: Int, valY: Int) {
        for (i in 0..number - 1) {
            rectangle!![i]!!.addTouchVal(valX, valY)
        }
    }

    companion object {

        private val TAG = "Rectangles"
    }
}
