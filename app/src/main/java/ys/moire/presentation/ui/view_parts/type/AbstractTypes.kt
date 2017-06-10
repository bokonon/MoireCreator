package ys.moire.presentation.ui.view_parts.type

import android.graphics.Canvas

/**
 * geometry set abstract class.
 */
abstract class AbstractTypes {

    internal abstract fun loadData(types: BaseTypes)

    internal abstract fun init(whichLine: Int, layoutWidth: Int, layoutHeight: Int)

    internal abstract fun checkOutOfRange(layoutWidth: Int)

    internal abstract fun move(whichLine: Int)

    internal abstract fun draw(canvas: Canvas)

//    internal abstract fun setColor(color: Int)
//
//    internal abstract fun setNumber(number: Int)
//
//    internal abstract fun setThick(thick: Int)
//
//    internal abstract fun setSlope(slope: Int)

    internal abstract fun addTouchVal(valX: Int, valY: Int)

    internal abstract fun setOnTouchMode(touch: Boolean)
}
