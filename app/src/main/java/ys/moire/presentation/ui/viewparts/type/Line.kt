package ys.moire.presentation.ui.viewparts.type

/**
 * line class.
 */
class Line(layoutHeight: Int, slope: Int, value: Int, arg: Int) : BaseType() {

    var topX: Float = (value + slope - arg).toFloat()
    var topY: Float = 0f
    var bottomX: Float = topX - slope + arg * 2
    var bottomY: Float = layoutHeight.toFloat()

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
