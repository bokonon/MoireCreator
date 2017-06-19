package ys.moire.presentation.ui.view_parts.type

import android.graphics.Path
import android.graphics.PointF

/**
 * rectangle class.
 */
class Rectangle : BaseType() {

    lateinit var leftTop: PointF
    lateinit var rightTop: PointF
    lateinit var rightBottom: PointF
    lateinit var leftBottom: PointF

    var topLength: Float = 0f
    var bottomLength: Float = 0f

    lateinit var path: Path

    fun init(x: Float, y: Float, height: Float, topLength: Float, bottomLength: Float) {
        this.topLength = topLength
        this.bottomLength = bottomLength
        this.leftTop = PointF(x - topLength / 2, y - height)
        this.rightTop = PointF(x + topLength / 2, y - height)
        this.leftBottom = PointF(x - bottomLength / 2, y + height)
        this.rightBottom = PointF(x + bottomLength / 2, y + height)

        path = Path()
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
