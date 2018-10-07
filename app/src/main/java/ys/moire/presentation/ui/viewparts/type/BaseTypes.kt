package ys.moire.presentation.ui.viewparts.type

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

/**
 * Geometry base class.
 */
open class BaseTypes : AbstractTypes() {
    /** number  */
    var number = MAX_NUMBER
    /** thick  */
    var thick = 1

    /** slope  */
    var slope = 25
    /** color  */
    var color = Color.BLACK
    /** paint  */
    protected val paint: Paint = Paint()
    /** isOnTouch  */
    protected var onTouch: Boolean = false

    /** move value  */
    protected var dx = AUTO_SPEED

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = thick.toFloat()
        paint.color = color
    }

    public override fun loadData(types: BaseTypes) {
        color = types.color
        paint.color = color
        // for migration to new version
        val previousValue = types.number
        number = if (previousValue < MINIMUM_VAL) {
            MINIMUM_VAL
        } else {
            previousValue
        }
        thick = types.thick
        paint.strokeWidth = thick.toFloat()
        slope = types.slope
    }

    override fun init(whichLine: Int, layoutWidth: Int, layoutHeight: Int) {

    }

    // for rect
    override fun init(whichLine: Int, layoutWidth: Int, layoutHeight: Int, maxTopLength: Float, maxBottomLength: Float) {

    }

    // for octagon
    override fun init(whichLine: Int, layoutWidth: Int, layoutHeight: Int, number: Int) {

    }

    override fun checkOutOfRange(layoutWidth: Int) {

    }

    override fun checkOutOfRange(whichLine: Int, layoutWidth: Int) {

    }

    override fun move(whichLine: Int) {

    }

    override fun draw(canvas: Canvas) {

    }

    override fun addTouchVal(valX: Int, valY: Int) {

    }

    override fun drawOriginalLine(layoutWidth: Int, valX: Float, valY: Float, moveCount: Int) {

    }

    /**
     * set is OnTouch.
     * @param touch isOnTouch
     */
    public override fun setOnTouchMode(touch: Boolean) {
        onTouch = touch
    }

    companion object {

        const val LINE_A = 0
        const val LINE_B = 1

        const val MINIMUM_VAL = 10;

        protected const val MAX_NUMBER = 50
        /** auto move speed  */
        protected const val AUTO_SPEED = 0.7f
    }
}
