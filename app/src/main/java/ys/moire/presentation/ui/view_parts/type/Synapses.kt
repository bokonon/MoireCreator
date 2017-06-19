package ys.moire.presentation.ui.view_parts.type

import android.graphics.Canvas
import android.graphics.PointF

/**
 * Synapse set class.
 */
class Synapses : BaseTypes() {

    /** Heart array  */
    private var synapse: Array<Synapse?>? = null

    private var margin: Float = 0f

    override fun init(whichLine: Int,
                      layoutWidth: Int,
                      layoutHeight: Int) {
        synapse = arrayOfNulls<Synapse>(number)
        var centerX = 0f
        var centerY = 0f
        if (whichLine == BaseTypes.LINE_A) {
            centerX = 0f
            centerY = layoutHeight / 3f
        } else if (whichLine == BaseTypes.LINE_B) {
            centerX = layoutWidth.toFloat()
            centerY = layoutHeight * 2 / 3f
        }
        margin = layoutHeight.toFloat() / number
        for (i in 0..number - 1) {
            synapse!![i] = Synapse()
            synapse!![i]!!.init(
                    PointF(centerX, centerY),
                    PointF(centerX - margin * i, centerY),
                    PointF(centerX, centerY - margin * i),
                    PointF(centerX + margin * i, centerY),
                    PointF(centerX, centerY + margin * i))
        }
    }

    public override fun checkOutOfRange(layoutWidth: Int) {
        if (synapse!![number - 1]!!.right.x < 0) {
            val startX = layoutWidth -  synapse!![0]!!.center.x
            for (i in 0..number - 1) {
                synapse!![i]!!.center.x = startX
            }
        } else if (layoutWidth < synapse!![number - 1]!!.left.x) {
            val startX = synapse!![0]!!.center.x - layoutWidth
            for (i in 0..number - 1) {
                synapse!![i]!!.center.x = - startX
            }
        }
    }

    public override fun move(whichLine: Int) {
        if (onTouch) {
            return
        }
        if (whichLine == BaseTypes.LINE_A) {
            for (i in 0..number - 1) {
                synapse!![i]!!.autoMove(dx, margin * i)
            }
        } else if (whichLine == BaseTypes.LINE_B) {
            for (i in 0..number - 1) {
                synapse!![i]!!.autoMove(-dx, margin * i)
            }
        }
    }

    public override fun draw(canvas: Canvas) {
        for (i in 0..number - 1) {
            canvas.drawPath(synapse!![i]!!.path, paint!!)
        }
    }

    /**
     * add touch value.
     * @param valX dx
     */
    public override fun addTouchVal(valX: Int, valY: Int) {
        for (i in 0..number - 1) {
            synapse!![i]!!.addTouchVal(valX, valY, margin * i)
        }
    }

    companion object {
        private val TAG = "Synapses"
    }
}