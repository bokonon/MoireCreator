package ys.moire.presentation.ui.viewparts.type

import android.graphics.Path
import android.graphics.PointF
import ys.moire.presentation.ui.viewparts.data.CircumferencePoint

/**
 * Flower shape class.
 */
class Flower(var center: PointF, var length: Float) : BaseType() {

    companion object {
        const val POINT_NUMBER = 6
    }

    val path: Path = Path()

    // Outer circumference
    private lateinit var outer: Array<CircumferencePoint>
    // Middle circumference
    private lateinit var middle: Array<CircumferencePoint>
    // Inner circumference
    private lateinit var inner: Array<CircumferencePoint>

    private var degrees: Float = 0f

    init {
        initCircumferencePoints()
    }

    override fun autoMove(dx :Float) {
        degrees += 2

        if (degrees >= 360) {
            degrees -= 360
        }
        center.x += dx
        setPoint(center)
        setPath()
    }

    override fun addTouchVal(touchDx: Int, touchDy: Int) {
        center.x += touchDx
        center.y += touchDy

        for (i in 0 until POINT_NUMBER) {
            outer[i].point.x += touchDx
            outer[i].point.y += touchDy
            middle[i].point.x += touchDx
            middle[i].point.y += touchDy
            inner[i].point.x += touchDx
            inner[i].point.y += touchDy
        }
        setPath()
    }

    fun checkOutOfRange(centerX: Float) {
        center.x = centerX
        setPoint(center)
        setPath()
    }

    private fun initCircumferencePoints() {
        outer = Array(POINT_NUMBER,
                {CircumferencePoint(PointF(), length, it*60f)})
        middle = Array(POINT_NUMBER,
                {CircumferencePoint(PointF(), length*4/5, it*60f+15)})
        inner = Array(POINT_NUMBER,
                {CircumferencePoint(PointF(), length*3/5, it*60f+30)})
    }

    private fun Float.degreesToRadians(): Double = this * Math.PI / 180

    private fun Float.adjustDegree(): Float {
        if (this > 360) {
            return this - 360
        } else if (this < 0) {
            return 360 + this
        }
        return this
    }

    private fun setPoint(center: PointF){
        for (i in 0 until POINT_NUMBER) {
            val outerDegree = (degrees + outer[i].degree).adjustDegree()
            outer[i].point.x = (center.x + outer[i].radius * Math.cos(outerDegree.degreesToRadians())).toFloat()
            outer[i].point.y = (center.y - outer[i].radius * Math.sin(outerDegree.degreesToRadians())).toFloat()

            val middleDegree = (degrees + middle[i].degree).adjustDegree()
            middle[i].point.x = (center.x + middle[i].radius * Math.cos(middleDegree.degreesToRadians())).toFloat()
            middle[i].point.y = (center.y - middle[i].radius * Math.sin(middleDegree.degreesToRadians())).toFloat()

            val innerDegree = (degrees + inner[i].degree).adjustDegree()
            inner[i].point.x = (center.x + inner[i].radius * Math.cos(innerDegree.degreesToRadians())).toFloat()
            inner[i].point.y = (center.y - inner[i].radius * Math.sin(innerDegree.degreesToRadians())).toFloat()
        }
    }

    private fun setPath(){
        path.reset()
        path.moveTo(inner[POINT_NUMBER-1].point.x, inner[POINT_NUMBER-1].point.y)

        for (i in 0 until POINT_NUMBER) {
            path.cubicTo(outer[i].point.x, outer[i].point.y,
                    middle[i].point.x, middle[i].point.y,
                    inner[i].point.x, inner[i].point.y)
        }
    }
}