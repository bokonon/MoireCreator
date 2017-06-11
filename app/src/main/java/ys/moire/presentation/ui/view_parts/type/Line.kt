package ys.moire.presentation.ui.view_parts.type

/**
 * line class.
 */
class Line : BaseType() {
    var topX: Float = 0.toFloat()
    var topY: Float = 0.toFloat()
    var bottomX: Float = 0.toFloat()
    var bottomY: Float = 0.toFloat()

    /**
     * init line.
     * @param layoutHeight screen width
     * *
     * @param slope slope
     * *
     * @param val offset x
     * *
     * @param arg b slope value (A:0 B:slope)
     */
    fun init(layoutHeight: Int, slope: Int, `val`: Int, arg: Int) {
        topX = (`val` + slope - arg).toFloat()
        topY = 0f
        bottomX = topX - slope + arg * 2
        bottomY = layoutHeight.toFloat()
    }

    /**
     * scroll line automatically.
     * @param dx dx
     */
    override fun autoMove(dx: Float) {
        topX += dx
        bottomX += dx
    }

    /**
     * check if the line is out of range.
     * @param layoutWidth 画面の横幅
     */
    override fun checkOutOfRange(layoutWidth: Int, slope: Int) {
        // LineA
        if (bottomX < topX) {
            if (layoutWidth < bottomX) {
                val diff = bottomX - layoutWidth
                topX = diff
                bottomX = topX - slope
            } else if (topX < 0) {
                val diff = -topX
                topX = layoutWidth + slope - diff
                bottomX = topX - slope
            }
        } else {
            if (topX < -slope) {
                val diff = -slope - topX
                topX = layoutWidth - diff
                bottomX = topX + slope
            } else if (layoutWidth < topX) {
                val diff = topX - layoutWidth
                topX = -slope + diff
                bottomX = topX + slope
            }
        }// LineB
    }

    /**
     * タッチ移動の値を反映します.
     * @param touchDx  x座標の移動値
     * *
     * @param touchDy  y座標の移動値
     */
    public override fun addTouchVal(touchDx: Int, touchDy: Int) {
        topX += touchDx.toFloat()
        bottomX += touchDx.toFloat()
        topY += touchDy.toFloat()
        bottomY += touchDy.toFloat()
    }

}
