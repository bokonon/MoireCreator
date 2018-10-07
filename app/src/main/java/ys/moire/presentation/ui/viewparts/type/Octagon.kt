package ys.moire.presentation.ui.viewparts.type

import android.graphics.Path
import android.graphics.PointF
import ys.moire.presentation.ui.viewparts.data.CircumferencePoint
import java.lang.Math.PI

/**
 * octagon class.
 */
class Octagon(var center: PointF, var length: Float) : BaseType() {

    companion object {
        const val POINT_NUMBER = 8
    }

    val path: Path = Path()

    // Outer circumference
    private lateinit var outer: Array<CircumferencePoint>

    private var degrees: Int = 0

    init {
        initCircumferencePoints()
        setPoint(center)
        setPath()
    }

    override fun autoMove(dx :Float){
        degrees += 2

        if (degrees >= 360) {
            degrees -= 360
        }
        center.x += dx
        setPoint(center)
        setPath()
    }

    override fun addTouchVal(touchDx: Int, touchDy: Int){
        center.x += touchDx
        center.y += touchDy

        for (i in 0 until POINT_NUMBER) {
            outer[i].point.x += touchDx
            outer[i].point.y += touchDy
        }

        setPath()
    }

    fun checkOutOfRange(centerX: Float){
        center.x = centerX

        setPointX(center)
        setPath()
    }

    private fun initCircumferencePoints() {
        outer = Array(POINT_NUMBER
        ) {CircumferencePoint(PointF(), length, it*45f)}
    }

    private fun setPoint(center: PointF){
        for (i in 0 until POINT_NUMBER) {
            val d = (degrees + outer[i].degree).adjustDegree()
            outer[i].point.x = (center.x + outer[i].radius * Math.cos(d.degreesToRadians())).toFloat()
            outer[i].point.y = (center.y - outer[i].radius * Math.sin(d.degreesToRadians())).toFloat()
        }
    }
    private fun setPointX(center: PointF) {
        for (i in 0 until POINT_NUMBER) {
            outer[i].point.x = (center.x + outer[i].radius * Math.cos(outer[i].degree.degreesToRadians())).toFloat()
        }
    }

    private fun Float.degreesToRadians(): Double = this * PI / 180

    private fun Float.adjustDegree(): Float {
        if (this > 360) {
            return this - 360
        } else if (this < 0) {
            return 360 + this
        }
        return this
    }

    private fun setPath(){
        path.reset()
        path.moveTo(outer[0].point.x, outer[0].point.y)
        for (i in 1 until POINT_NUMBER) {
            path.lineTo(outer[i].point.x, outer[i].point.y)
        }
        path.lineTo(outer[0].point.x, outer[0].point.y)

    }

}