package ys.moire.presentation.ui.viewparts.type

import android.graphics.Path
import android.graphics.PointF

/**
 * rectangle class.
 */
class Rectangle(var x: Float, var y: Float, var height: Float, var topLength: Float, var bottomLength: Float) : BaseType() {

    var leftTop: PointF = PointF(x - topLength / 2, y - height)
    var rightTop: PointF = PointF(x + topLength / 2, y - height)
    var leftBottom: PointF = PointF(x - bottomLength / 2, y + height)
    var rightBottom: PointF = PointF(x + bottomLength / 2, y + height)

    var path: Path = Path()

    init {
        setPath()
    }


    /**
     * scroll rectangle automatically.
     * @param dx dx
     */
    override fun autoMove(dx: Float) {
        leftTop.x += dx
        rightTop.x += dx
        rightBottom.x += dx
        leftBottom.x += dx

        setPath()
    }

    /**
     * move x.
     */
    fun moveX(centerX: Float) {
        rightBottom.x = centerX + bottomLength / 2
        leftBottom.x = centerX - bottomLength / 2
        leftTop.x = centerX - topLength / 2
        rightTop.x = centerX + topLength / 2
        setPath()
    }

    /**
     * add touch value.
     * @param touchDx  dx
     * *
     * @param touchDy  dy
     */
    public override fun addTouchVal(touchDx: Int, touchDy: Int) {
        leftTop.x += touchDx.toFloat()
        leftTop.y += touchDy.toFloat()
        rightTop.x += touchDx.toFloat()
        rightTop.y += touchDy.toFloat()
        rightBottom.x += touchDx.toFloat()
        rightBottom.y += touchDy.toFloat()
        leftBottom.x += touchDx.toFloat()
        leftBottom.y += touchDy.toFloat()
        setPath()
    }

    private fun setPath() {
        path.reset()
        path.moveTo(leftTop.x, leftTop.y)
        path.lineTo(rightTop.x, rightTop.y)
        path.lineTo(rightBottom.x, rightBottom.y)
        path.lineTo(leftBottom.x, leftBottom.y)
        path.lineTo(leftTop.x, leftTop.y)
    }

}
