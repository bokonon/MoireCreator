package ys.moire.presentation.ui.viewparts.type

import android.graphics.Path
import android.graphics.PointF
import android.util.Log

/**
 * Synapse class.
 */
class Synapse(var center: PointF, var left: PointF, var top: PointF,
              var right: PointF, var bottom: PointF) : BaseType() {

    private var leftTop: PointF = PointF(left.x, top.y)
    private var leftBottom: PointF = PointF(left.x, bottom.y)
    private var rightTop: PointF = PointF(right.x, top.y)
    private var rightBottom: PointF = PointF(right.x, bottom.y)

    private var curveMargin: Int = 1
    var path: Path = Path()

    /**
     * scroll rectangle automatically.
     * @param dx dx
     */
    override fun autoMove(dx: Float) {
        // NOP
    }

    fun autoMove(dx: Float, margin: Float) {
        center.x += dx

        Log.d(TAG, "dx : "+dx.toString())
        Log.d(TAG, "center.x : "+center.x)

        setMargin(margin)
    }

    /**
     * add touch value.
     * @param touchDx  dx
     * *
     * @param touchDy  dy
     */
    override fun addTouchVal(touchDx: Int, touchDy: Int) {
        // NOP
    }

    fun addTouchVal(touchDx: Int, touchDy: Int, margin: Float) {
        center.x += touchDx.toFloat()
        center.y += touchDy.toFloat()
        setMargin(margin)
    }

    private fun setMargin(margin: Float) {
        left.x = center.x - margin
        left.y = center.y
        top.x = center.x
        top.y = center.y - margin
        right.x = center.x + margin
        right.y = center.y
        bottom.x = center.x
        bottom.y = center.y + margin

        leftTop.x = left.x
        leftTop.y = top.y

        leftBottom.x = left.x
        leftBottom.y = bottom.y

        rightTop.x = right.x
        rightTop.y = top.y

        rightBottom.x = right.x
        rightBottom.y = bottom.y

        setPath()
    }


    private fun setPath() {
        path.reset()

        path.moveTo(top.x, top.y)
        path.cubicTo(
                (center.x + curveMargin * Math.cos(Math.toRadians(345.0))).toFloat(),
                (center.y + curveMargin * Math.sin(Math.toRadians(345.0))).toFloat(),
                (center.x + curveMargin * Math.cos(Math.toRadians(330.0))).toFloat(),
                (center.y + curveMargin * Math.sin(Math.toRadians(330.0))).toFloat(),
                leftTop.x, leftTop.y)

        path.moveTo(leftTop.x, leftTop.y)
        path.cubicTo(
                (center.x + curveMargin * Math.cos(Math.toRadians(300.0))).toFloat(),
                (center.y + curveMargin * Math.sin(Math.toRadians(300.0))).toFloat(),
                (center.x + curveMargin * Math.cos(Math.toRadians(285.0))).toFloat(),
                (center.y + curveMargin * Math.sin(Math.toRadians(285.0))).toFloat(),
                left.x, left.y)

        path.moveTo(left.x, left.y)
        path.cubicTo(
                (center.x + curveMargin * Math.cos(Math.toRadians(255.0))).toFloat(),
                (center.y + curveMargin * Math.sin(Math.toRadians(255.0))).toFloat(),
                (center.x + curveMargin * Math.cos(Math.toRadians(240.0))).toFloat(),
                (center.y + curveMargin * Math.sin(Math.toRadians(240.0))).toFloat(),
                leftBottom.x, leftBottom.y)

        path.moveTo(leftBottom.x, leftBottom.y)
        path.cubicTo(
                (center.x + curveMargin * Math.cos(Math.toRadians(210.0))).toFloat(),
                (center.y + curveMargin * Math.sin(Math.toRadians(210.0))).toFloat(),
                (center.x + curveMargin * Math.cos(Math.toRadians(195.0))).toFloat(),
                (center.y + curveMargin * Math.sin(Math.toRadians(195.0))).toFloat(),
                bottom.x, bottom.y)

        path.moveTo(bottom.x, bottom.y)
        path.cubicTo(
                (center.x + curveMargin * Math.cos(Math.toRadians(165.0))).toFloat(),
                (center.y + curveMargin * Math.sin(Math.toRadians(165.0))).toFloat(),
                (center.x + curveMargin * Math.cos(Math.toRadians(150.0))).toFloat(),
                (center.y + curveMargin * Math.sin(Math.toRadians(150.0))).toFloat(),
                rightBottom.x, rightBottom.y)

        path.moveTo(rightBottom.x, rightBottom.y)
        path.cubicTo(
                (center.x + curveMargin* Math.cos(Math.toRadians(120.0))).toFloat(),
                (center.y + curveMargin * Math.sin(Math.toRadians(120.0))).toFloat(),
                (center.x + curveMargin* Math.cos(Math.toRadians(105.0))).toFloat(),
                (center.y + curveMargin * Math.sin(Math.toRadians(105.0))).toFloat(),
                right.x, right.y)

        path.moveTo(right.x, right.y)
        path.cubicTo(
                (center.x + curveMargin* Math.cos(Math.toRadians(75.0))).toFloat(),
                (center.y + curveMargin * Math.sin(Math.toRadians(75.0))).toFloat(),
                (center.x + curveMargin* Math.cos(Math.toRadians(60.0))).toFloat(),
                (center.y + curveMargin * Math.sin(Math.toRadians(60.0))).toFloat(),
                rightTop.x, rightTop.y)

        path.moveTo(rightTop.x, rightTop.y)
        path.cubicTo(
                (center.x + curveMargin* Math.cos(Math.toRadians(30.0))).toFloat(),
                (center.y + curveMargin * Math.sin(Math.toRadians(30.0))).toFloat(),
                (center.x + curveMargin* Math.cos(Math.toRadians(15.0))).toFloat(),
                (center.y + curveMargin * Math.sin(Math.toRadians(15.0))).toFloat(),
                top.x, top.y)
    }

    companion object {
        private const val TAG = "Synapse"
    }

}