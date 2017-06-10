package ys.moire.presentation.ui.view_parts.type

/**
 * geometry abstract class.
 */
abstract class AbstractType {

    internal abstract fun autoMove(dx: Float)

    internal abstract fun checkOutOfRange(layoutWidth: Int, slope: Int)

    internal abstract fun addTouchVal(touchDx: Int, touchDy: Int)
}
