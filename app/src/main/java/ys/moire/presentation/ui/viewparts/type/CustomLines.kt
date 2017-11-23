package ys.moire.presentation.ui.viewparts.type

import android.graphics.Canvas

/**
 * custom line set class.
 */
class CustomLines : BaseTypes() {

    /** CustomLine array  */
    private var customLines: Array<CustomLine?>? = null

    public override fun init(whichLine: Int,
                             layoutWidth: Int,
                             layoutHeight: Int) {
        customLines = arrayOfNulls<CustomLine>(number)
        for (i in 0..number - 1) {
            customLines!![i] = CustomLine()
        }
    }

    public override fun checkOutOfRange(whichLine: Int, layoutWidth: Int) {
        if (onTouch) {
            return
        }
        for (i in 0..number - 1) {
            customLines!![i]!!.checkParcellingOutOfRange(whichLine, layoutWidth)
        }
    }

    public override fun move(whichLine: Int) {
        if (onTouch) {
            return
        }
        if (whichLine == BaseTypes.LINE_A) {
            for (i in 0..number - 1) {
                customLines!![i]!!.autoMove(dx)
            }
        } else if (whichLine == BaseTypes.LINE_B) {
            for (j in 0..number - 1) {
                customLines!![j]!!.autoMove(-dx)
            }
        }
    }

    public override fun draw(canvas: Canvas) {
        for (i in 0..number - 1) {
            canvas.drawPath(customLines!![i]!!.path, paint!!)
        }
    }

    /**
     * add moving value.
     * @param layoutWidth width
     * *
     * @param valX dx
     * *
     * @param valY dy
     * *
     * @param moveCount move count
     */
    public override fun drawOriginalLine(layoutWidth: Int, valX: Float, valY: Float, moveCount: Int) {
        for (i in 0..number - 1) {
            customLines!![i]!!.drawOriginalLine(valX - layoutWidth / 2f + (i + 1) * layoutWidth / number, valY, moveCount)
        }
    }

    companion object {

        private val TAG = "CustomLines"
    }
}
