package ys.moire.presentation.ui.viewparts.type

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import ys.moire.BuildConfig

/**
 * octagon set class.
 */
class Octagons : BaseTypes() {

    /** Octagon array  */
    private var octagon: Array<Octagon?>? = null

    private var maxLength: Float = 0f

    override fun init(whichLine: Int, layoutWidth: Int, layoutHeight: Int){

        maxLength = layoutHeight/3f
        octagon = arrayOfNulls<Octagon>(number)
        for (i in 0..number - 1) {
            octagon!![i] = Octagon()
            if(whichLine == BaseTypes.LINE_A){
                octagon!![i]!!.init(PointF(0f,layoutHeight/3f),maxLength/number*i)
            }
            else {
                octagon!![i]!!.init(PointF(layoutWidth.toFloat(),layoutHeight*2f/3),maxLength/number*i)
            }
        }
    }

    override fun checkOutOfRange(layoutWidth :Int){
        if(layoutWidth < octagon!![number-1]!!.centerPoint.x - octagon!![number-1]!!.length) {
            val diff: Float = octagon!![number-1]!!.centerPoint.x - octagon!![number-1]!!.length - layoutWidth
            val centerX: Float = -maxLength + diff
            for (i in 0..number - 1) {
                octagon!![i]!!.checkOutOfRange(centerX)
            }
        }
        else if(octagon!![number-1]!!.centerPoint.x + octagon!![number-1]!!.length < 0) {
            val diff: Float = -(octagon!![number-1]!!.centerPoint.x + octagon!![number-1]!!.length)
            val centerX: Float = layoutWidth + maxLength - diff
            for (i in 0..number - 1) {
                octagon!![i]!!.checkOutOfRange(centerX)
            }
        }
    }

    override fun move(whichLine :Int){
        if (onTouch) {
            return
        }
        if(whichLine == BaseTypes.LINE_A) {
            for (i in 0..number - 1) {
                octagon!![i]!!.autoMove(dx);
            }
        }
        else if(whichLine == BaseTypes.LINE_B) {
            for (i in 0..number - 1) {
                octagon!![i]!!.autoMove(-dx);
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
                    canvas.drawPath(octagon!![i]!!.path, p)
                    continue
                }
                // make last line to red color for only debug build
                if (i == number - 1) {
                    val p = Paint()
                    p.style = Paint.Style.STROKE
                    p.color = Color.RED
                    p.strokeWidth = thick.toFloat()
                    canvas.drawPath(octagon!![i]!!.path, p)
                    continue
                }
            }
            canvas.drawPath(octagon!![i]!!.path, paint!!)
        }
    }

    override fun addTouchVal(valX: Int, valY: Int){
        for (i in 0..number - 1) {
            octagon!![i]!!.addTouchVal(valX, valY);
        }
    }

}