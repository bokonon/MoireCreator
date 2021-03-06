package ys.moire.presentation.ui.viewparts.type

import android.graphics.Canvas
import android.graphics.PointF

/**
 * Synapse set class.
 */
class Synapses : BaseTypes() {

    /** Heart array  */
    private lateinit var synapse: Array<Synapse>

    private var margin: Float = 0f

    override fun init(whichLine: Int,
                      layoutWidth: Int,
                      layoutHeight: Int) {

        val centerX : Float
        val centerY : Float
        if (whichLine == BaseTypes.LINE_A) {
            centerX = 0f
            centerY = layoutHeight / 3f

        } else {
            centerX = layoutWidth.toFloat()
            centerY = layoutHeight * 2 / 3f
        }
        margin = layoutHeight.toFloat() / number
        synapse = Array(number) {
            Synapse(PointF(centerX, centerY),
                    PointF(centerX - margin * it, centerY),
                    PointF(centerX, centerY - margin * it),
                    PointF(centerX + margin * it, centerY),
                    PointF(centerX, centerY + margin * it))
        }
    }

    public override fun checkOutOfRange(layoutWidth: Int) {
        if (synapse[number - 1].right.x < 0) {
            val startX = layoutWidth -  synapse[0].center.x + synapse[number - 1].right.x
            for (i in 0 until number) {
                synapse[i].center.x = startX
            }
        } else if (layoutWidth < synapse[number - 1].left.x) {
            val startX = synapse[0].center.x - synapse[number - 1].left.x
            for (i in 0 until number) {
                synapse[i].center.x = - startX
            }
        }
    }

    public override fun move(whichLine: Int) {
        if (onTouch) {
            return
        }
        if (whichLine == BaseTypes.LINE_A) {
            for (i in 0 until number) {
                synapse[i].autoMove(dx, margin * i)
            }
        } else if (whichLine == BaseTypes.LINE_B) {
            for (i in 0 until number) {
                synapse[i].autoMove(-dx, margin * i)
            }
        }
    }

    public override fun draw(canvas: Canvas) {
        for (i in 0 until number) {
            canvas.drawPath(synapse[i].path, paint)
        }
    }

    /**
     * add touch value.
     * @param valX dx
     */
    public override fun addTouchVal(valX: Int, valY: Int) {
        for (i in 0 until number) {
            synapse[i].addTouchVal(valX, valY, margin * i)
        }
    }

    companion object {
        private const val TAG = "Synapses"
    }
}