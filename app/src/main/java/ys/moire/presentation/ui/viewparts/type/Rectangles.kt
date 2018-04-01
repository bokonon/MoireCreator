package ys.moire.presentation.ui.viewparts.type

import android.graphics.Canvas

/**
 * rectangle set class.
 */
class Rectangles : BaseTypes() {

    /** Rectangle array  */
    private lateinit var rectangle: Array<Rectangle>

    // Trapezoidal judgment
    private var isTrapezoid: Boolean = false

    override fun init(whichLine: Int,
             layoutWidth: Int,
             layoutHeight: Int,
             maxTopLength: Float,
             maxBottomLength: Float) {

        rectangle = if (whichLine == BaseTypes.LINE_A) {
            Array(number, {
                Rectangle(0f, layoutHeight / 3f,
                        layoutHeight.toFloat() / 3f / number.toFloat() * it,
                        maxTopLength / number * it,
                        maxBottomLength / number * it)
            })
        } else {
            Array(number, {
                Rectangle(layoutWidth.toFloat(), layoutHeight * (2 / 3f),
                        layoutHeight.toFloat() / 3f / number.toFloat() * it,
                        maxTopLength / number * it,
                        maxBottomLength / number * it)
            })
        }
        isTrapezoid = maxTopLength <= maxBottomLength
    }

    public override fun checkOutOfRange(layoutWidth: Int) {
        // check only out side line
        if (isTrapezoid) {
            if (rectangle[number - 1].rightBottom.x < 0) {
                // disappear for less than 0
                for (i in 0 until number) {
                    rectangle[i].moveX(layoutWidth + rectangle[number - 1].bottomLength / 2)
                }
            } else if (layoutWidth < rectangle[number - 1].leftBottom.x) {
                // disappear for more than width
                for (i in 0 until number) {
                    rectangle[i].moveX(-rectangle[number - 1].bottomLength / 2)
                }
            }
        } else {
            if (rectangle[number - 1].rightTop.x < 0) {
                // disappear less than 0
                for (i in 0 until number) {
                    rectangle[i].moveX(layoutWidth + rectangle[number - 1].topLength / 2)
                }
            } else if (layoutWidth < rectangle[number - 1].leftTop.x) {
                // disappear more than width
                for (i in 0 until number) {
                    rectangle[i].moveX(-rectangle[number - 1].topLength / 2)
                }
            }
        }
    }

    public override fun move(whichLine: Int) {
        if (onTouch) {
            return
        }
        if (whichLine == BaseTypes.LINE_A) {
            for (i in 0 until number) {
                rectangle[i].autoMove(dx)
            }
        } else if (whichLine == BaseTypes.LINE_B) {
            for (j in 0 until number) {
                rectangle[j].autoMove(-dx)
            }
        }
    }

    public override fun draw(canvas: Canvas) {
        for (i in 0 until number) {
            canvas.drawPath(rectangle[i].path, paint)
        }
    }

    /**
     * add touch value.
     * @param valX dx
     */
    public override fun addTouchVal(valX: Int, valY: Int) {
        for (i in 0 until number) {
            rectangle[i].addTouchVal(valX, valY)
        }
    }

    companion object {

        private const val TAG = "Rectangles"
    }
}
