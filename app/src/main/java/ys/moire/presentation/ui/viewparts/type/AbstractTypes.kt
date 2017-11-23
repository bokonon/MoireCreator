package ys.moire.presentation.ui.viewparts.type

import android.graphics.Canvas

/**
 * geometry set abstract class.
 */
abstract class AbstractTypes {

    internal abstract fun loadData(types: BaseTypes)

    internal abstract fun init(whichLine: Int, layoutWidth: Int, layoutHeight: Int)

    internal abstract fun init(whichLine: Int, layoutWidth: Int, layoutHeight: Int, maxTopLength: Float, maxBottomLength: Float)

    internal abstract fun init(whichLine: Int, layoutWidth: Int, layoutHeight: Int, number: Int)

    internal abstract fun checkOutOfRange(layoutWidth: Int)

    internal abstract fun checkOutOfRange(whichLine: Int, layoutWidth: Int)

    internal abstract fun move(whichLine: Int)

    internal abstract fun draw(canvas: Canvas)

    internal abstract fun addTouchVal(valX: Int, valY: Int)

    internal abstract fun drawOriginalLine(layoutWidth: Int, valX: Float, valY: Float, moveCount: Int)

    internal abstract fun setOnTouchMode(touch: Boolean)
}
