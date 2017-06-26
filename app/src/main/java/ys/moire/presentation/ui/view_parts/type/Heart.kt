package ys.moire.presentation.ui.view_parts.type

import android.graphics.Path
import android.graphics.PointF

/**
 * Heart class.
 */
class Heart : BaseType() {

    var width = 0f
    var height = 0f
    lateinit var centerTop: PointF
    var leftTop: Array<PointF?> = arrayOfNulls(3)
    var leftBottom: Array<PointF?> = arrayOfNulls(3)
    var rightBottom: Array<PointF?> = arrayOfNulls(3)
    var rightTop: Array<PointF?> = arrayOfNulls(3)

    lateinit var path: Path

    fun init(centerX: Float, centerY: Float, width: Float, height: Float) {
        this.width = width
        this.height = height

        centerTop = PointF()
        for (i in leftTop.indices)
            leftTop[i] = PointF()

        for (i in leftBottom.indices)
            leftBottom[i] = PointF()

        for (i in rightBottom.indices)
            rightBottom[i] = PointF()

        for (i in rightTop.indices)
            rightTop[i] = PointF()

        initPoint(centerX, centerY, width, height)

        path = Path()
        setPath()
    }

    fun initPoint(centerX: Float, centerY: Float, width: Float, height: Float) {
        centerTop.x = centerX
        centerTop.y = centerY - (height / 2 - height / 5)

        leftTop[0]!!.x = centerX - (width / 2 - 5 * width / 14)
        leftTop[0]!!.y = centerY - height / 2
        leftTop[1]!!.x = centerX - width / 2
        leftTop[1]!!.y = centerY - (height / 2 - height / 15)
        leftTop[2]!!.x = centerX - (width / 2 - width / 28)
        leftTop[2]!!.y = centerY - (height / 2 - 2 * height / 5)

        leftBottom[0]!!.x = centerX - (width / 2 - width / 14)
        leftBottom[0]!!.y = centerY + (2 * height / 3 - height / 2)
        leftBottom[1]!!.x = centerX - (width / 2 - 3 * width / 7)
        leftBottom[1]!!.y = centerY + (5 * height / 6 - height / 2)
        leftBottom[2]!!.x = centerX
        leftBottom[2]!!.y = centerY + height / 2

        rightBottom[0]!!.x = centerX + (4 * width / 7 - width / 2)
        rightBottom[0]!!.y = centerY + (5 * height / 6 - height / 2)
        rightBottom[1]!!.x = centerX + (13 * width / 14 - width / 2)
        rightBottom[1]!!.y = centerY + (2 * height / 3 - height / 2)
        rightBottom[2]!!.x = centerX + (27 * width / 28 - width / 2)
        rightBottom[2]!!.y = centerY - (height / 2 - 2 * height / 5)

        rightTop[0]!!.x = centerX + width / 2
        rightTop[0]!!.y = centerY - (height / 2 - height / 15)
        rightTop[1]!!.x = centerX + (9 * width / 14 - width / 2)
        rightTop[1]!!.y = centerY - height / 2
        rightTop[2]!!.x = centerX
        rightTop[2]!!.y = centerY - (height /2 - height / 5)
    }

    /**
     * scroll rectangle automatically.
     * @param dx dx
     */
    override fun autoMove(dx: Float) {
        centerTop.x += dx

        for (i in leftTop.indices)
            leftTop[i]!!.x += dx
        for (i in leftBottom.indices)
            leftBottom[i]!!.x += dx
        for (i in rightBottom.indices)
            rightBottom[i]!!.x += dx
        for (i in rightTop.indices)
            rightTop[i]!!.x += dx

        setPath()
    }

    /**
     * move x.
     */
    fun moveX(centerX: Float, centerY: Float) {
        initPoint(centerX, centerY, width, height)
        setPath()
    }

    /**
     * add touch value.
     * @param touchDx  dx
     * *
     * @param touchDy  dy
     */
    public override fun addTouchVal(touchDx: Int, touchDy: Int) {
        centerTop.x += touchDx.toFloat()
        centerTop.y += touchDy.toFloat()

        for (i in leftTop.indices) {
            leftTop[i]!!.x += touchDx.toFloat()
            leftTop[i]!!.y += touchDy.toFloat()
        }
        for (i in leftBottom.indices) {
            leftBottom[i]!!.x += touchDx.toFloat()
            leftBottom[i]!!.y += touchDy.toFloat()
        }
        for (i in rightBottom.indices) {
            rightBottom[i]!!.x += touchDx.toFloat()
            rightBottom[i]!!.y += touchDy.toFloat()
        }
        for (i in rightTop.indices) {
            rightTop[i]!!.x += touchDx.toFloat()
            rightTop[i]!!.y += touchDy.toFloat()
        }

        setPath()
    }

    private fun setPath() {

        path.reset()
        // center top
        path.moveTo(centerTop.x, centerTop.y);

        // left top
        path.cubicTo(leftTop[0]!!.x, leftTop[0]!!.y,
                leftTop[1]!!.x, leftTop[1]!!.y,
                leftTop[2]!!.x, leftTop[2]!!.y);

        // left bottom
        path.cubicTo(leftBottom[0]!!.x, leftBottom[0]!!.y,
                leftBottom[1]!!.x, leftBottom[1]!!.y,
                leftBottom[2]!!.x, leftBottom[2]!!.y);

        // right bottom
        path.cubicTo(rightBottom[0]!!.x, rightBottom[0]!!.y,
                rightBottom[1]!!.x, rightBottom[1]!!.y,
                rightBottom[2]!!.x, rightBottom[2]!!.y);

        // right top
        path.cubicTo(rightTop[0]!!.x, rightTop[0]!!.y,
                rightTop[1]!!.x, rightTop[1]!!.y,
                rightTop[2]!!.x, rightTop[2]!!.y);
    }
}