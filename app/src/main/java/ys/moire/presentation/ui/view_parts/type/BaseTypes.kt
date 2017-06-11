package ys.moire.presentation.ui.view_parts.type

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
    protected var paint: Paint? = null
    /** isOnTouch  */
    protected var onTouch: Boolean = false

    /** move value  */
    protected var dx = AUTO_SPEED

    init {
        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.style = Paint.Style.STROKE
        paint!!.strokeWidth = thick.toFloat()
        paint!!.color = color
    }

    public override fun loadData(types: BaseTypes) {
        color = types.color
        paint!!.color = color
        number = types.number
        thick = types.thick
        paint!!.strokeWidth = thick.toFloat()
        slope = types.slope
    }

    override fun init(whichLine: Int, layoutWidth: Int, layoutHeight: Int) {

    }

    override fun checkOutOfRange(layoutWidth: Int) {

    }

    override fun move(whichLine: Int) {

    }

    override fun draw(canvas: Canvas) {

    }

    override fun addTouchVal(valX: Int, valY: Int) {

    }

    /**
     * set is OnTouch.
     * @param touch isOnTouch
     */
    public override fun setOnTouchMode(touch: Boolean) {
        onTouch = touch
    }

    companion object {

        val LINE_A = 0
        val LINE_B = 1

        protected val MAX_NUMBER = 50
        /** auto move speed  */
        protected val AUTO_SPEED = 0.7f
    }
}
