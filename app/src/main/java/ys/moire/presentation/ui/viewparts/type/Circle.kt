package ys.moire.presentation.ui.viewparts.type

/**
 * Circle class.
 */
class Circle(var x: Float, var y: Float, var r: Float) : BaseType() {

    /**
     * scroll circle automatically.
     * @param dx auto move distance
     */
    override fun autoMove(dx: Float) {
        x += dx
    }

    /**
     * add touch value.
     * @param touchDx  dx
     * *
     * @param touchDy  dy
     */
    public override fun addTouchVal(touchDx: Int, touchDy: Int) {
        x += touchDx.toFloat()
        y += touchDy.toFloat()
    }

}
