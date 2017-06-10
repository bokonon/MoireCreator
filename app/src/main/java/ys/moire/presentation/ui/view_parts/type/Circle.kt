package ys.moire.presentation.ui.view_parts.type

/**
 * Circle class.
 */
class Circle : BaseType() {
    var x: Float = 0.toFloat()
    var y: Float = 0.toFloat()
    /** radius  */
    var r: Float = 0.toFloat()

    fun init(x: Float, y: Float, r: Float) {
        this.x = x
        this.y = y
        this.r = r
    }

    /**
     * scroll circle automatically.
     * @param dx auto move distance
     */
    internal override fun autoMove(dx: Float) {
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
