package ys.moire.presentation.ui.viewparts.type

import android.graphics.Canvas

/**
 * custom line set class.
 */
class CustomLines : BaseTypes() {

    /** CustomLine array  */
    private lateinit var customLines: Array<CustomLine>

    public override fun init(whichLine: Int,
                             layoutWidth: Int,
                             layoutHeight: Int) {

        customLines = Array(number, {
            CustomLine()
        })
    }

    public override fun checkOutOfRange(whichLine: Int, layoutWidth: Int) {
        if (onTouch) {
            return
        }
        for (i in 0 until number) {
            customLines[i].checkParcellingOutOfRange(whichLine, layoutWidth)
        }
    }

    public override fun move(whichLine: Int) {
        if (onTouch) {
            return
        }
        if (whichLine == BaseTypes.LINE_A) {
            for (i in 0 until number) {
                customLines[i].autoMove(dx)
            }
        } else if (whichLine == BaseTypes.LINE_B) {
            for (j in 0 until number) {
                customLines[j].autoMove(-dx)
            }
        }
    }

    public override fun draw(canvas: Canvas) {
        for (i in 0 until number) {
            canvas.drawPath(customLines[i].path, paint)
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
        for (i in 0 until number) {
            customLines[i].drawOriginalLine(valX - layoutWidth / 2f + (i + 1) * layoutWidth / number, valY, moveCount)
        }
    }

    companion object {

        private const val TAG = "CustomLines"
    }
}
