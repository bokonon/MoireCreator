package ys.moire.presentation.ui.viewparts.type

import android.graphics.Path
import android.graphics.PointF
import java.lang.Math.PI

/**
 * octagon class.
 */
class Octagon : BaseType() {

    lateinit var path: Path

    lateinit var centerPoint: PointF

    var length: Float = 0f

    private var degrees: Int = 0

    private lateinit var leftTopPoint: PointF

    private lateinit var leftBottomPoint: PointF

    private lateinit var topLeftPoint: PointF

    private lateinit var topRightPoint: PointF

    private lateinit var rightTopPoint: PointF

    private lateinit var rightBottomPoint: PointF

    private lateinit var bottomRightPoint: PointF

    private lateinit var bottomLeftPoint: PointF

    private val leftTopDegreesMargin:Float = 250f

    private val leftBottomDegreesMargin:Float = 290f

    private val topLeftDegreeMargin:Float = -20f

    private val topRightDegreeMargin:Float = 20f

    private val rightTopDegreeMargin:Float = 70f

    private val rightBottomDegreeMargin:Float = 110f

    private val bottomRightDegreeMargin:Float = 160f

    private val bottomLeftDegreeMargin:Float = 200f

    fun init(centerPoint: PointF, length: Float){
        this.centerPoint = centerPoint
        this.length = length

        leftBottomPoint = getPoint(centerPoint, length, (degrees + leftTopDegreesMargin).adjustDegree())
        leftTopPoint = getPoint(centerPoint, length, (degrees + leftBottomDegreesMargin).adjustDegree())

        topLeftPoint = getPoint(centerPoint, length, (degrees + topLeftDegreeMargin).adjustDegree())
        topRightPoint = getPoint(centerPoint, length, (degrees + topRightDegreeMargin).adjustDegree())

        rightTopPoint = getPoint(centerPoint, length, (degrees + rightTopDegreeMargin).adjustDegree())
        rightBottomPoint = getPoint(centerPoint, length, (degrees + rightBottomDegreeMargin).adjustDegree())

        bottomRightPoint = getPoint(centerPoint, length, (degrees + bottomRightDegreeMargin).adjustDegree())
        bottomLeftPoint = getPoint(centerPoint, length, (degrees + bottomLeftDegreeMargin).adjustDegree())

        path = Path()
        setPath()
    }

    override fun autoMove(dx :Float){
        degrees += 2

        if (degrees >= 360) {
            degrees = degrees - 360
        }
        centerPoint.x += dx
        leftBottomPoint = getPoint(centerPoint, length, (degrees + leftTopDegreesMargin).adjustDegree())
        leftTopPoint = getPoint(centerPoint, length, (degrees + leftBottomDegreesMargin).adjustDegree())
        topLeftPoint = getPoint(centerPoint, length, (degrees + topLeftDegreeMargin).adjustDegree())
        topRightPoint = getPoint(centerPoint, length, (degrees + topRightDegreeMargin).adjustDegree())
        rightTopPoint = getPoint(centerPoint, length, (degrees + rightTopDegreeMargin).adjustDegree())
        rightBottomPoint = getPoint(centerPoint, length, (degrees + rightBottomDegreeMargin).adjustDegree())
        bottomRightPoint = getPoint(centerPoint, length, (degrees + bottomRightDegreeMargin).adjustDegree())
        bottomLeftPoint = getPoint(centerPoint, length, (degrees + bottomLeftDegreeMargin).adjustDegree())

        setPath()
    }

    override fun addTouchVal(touchDx: Int, touchDy: Int){
        centerPoint.x += touchDx
        centerPoint.y += touchDy

        leftTopPoint.x += touchDx
        leftTopPoint.y += touchDy
        leftBottomPoint.x += touchDx
        leftBottomPoint.y += touchDy

        topLeftPoint.x += touchDx
        topLeftPoint.y += touchDy
        topRightPoint.x += touchDx
        topRightPoint.y += touchDy

        rightTopPoint.x += touchDx
        rightTopPoint.y += touchDy
        rightBottomPoint.x += touchDx
        rightBottomPoint.y += touchDy

        bottomRightPoint.x += touchDx
        bottomRightPoint.y += touchDy
        bottomLeftPoint.x += touchDx
        bottomLeftPoint.y += touchDy

        setPath()
    }

    fun checkOutOfRange(centerX: Float){
        centerPoint.x = centerX
        leftBottomPoint.x = getPointX(centerX, length, (degrees + leftTopDegreesMargin).adjustDegree())
        leftTopPoint.x = getPointX(centerX, length, (degrees + leftBottomDegreesMargin).adjustDegree())
        topLeftPoint.x = getPointX(centerX, length, (degrees + topLeftDegreeMargin).adjustDegree())
        topRightPoint.x = getPointX(centerX, length, (degrees + topRightDegreeMargin).adjustDegree())
        rightTopPoint.x = getPointX(centerX, length, (degrees + rightTopDegreeMargin).adjustDegree())
        rightBottomPoint.x = getPointX(centerX, length, (degrees + rightBottomDegreeMargin).adjustDegree())
        bottomRightPoint.x = getPointX(centerX, length, (degrees + bottomRightDegreeMargin).adjustDegree())
        bottomLeftPoint.x = getPointX(centerX, length, (degrees + bottomLeftDegreeMargin).adjustDegree())

        setPath()
    }

    private fun getPoint(centerPoint: PointF, length: Float, degrees: Float): PointF {
        return PointF((centerPoint.x + length * Math.cos(degrees.degreesToRadians())).toFloat(),
                (centerPoint.y - length * Math.sin(degrees.degreesToRadians())).toFloat())
    }

    private fun getPointX(centerPointX: Float, length: Float, degrees: Float): Float =
            (centerPointX + length * Math.cos(degrees.degreesToRadians())).toFloat()

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
        path.moveTo(leftBottomPoint.x, leftBottomPoint.y)
        path.lineTo(leftTopPoint.x, leftTopPoint.y)
        path.lineTo(topLeftPoint.x, topLeftPoint.y)
        path.lineTo(topRightPoint.x, topRightPoint.y)
        path.lineTo(rightTopPoint.x, rightTopPoint.y)
        path.lineTo(rightBottomPoint.x, rightBottomPoint.y)
        path.lineTo(bottomRightPoint.x, bottomRightPoint.y)
        path.lineTo(bottomLeftPoint.x, bottomLeftPoint.y)
        path.lineTo(leftBottomPoint.x, leftBottomPoint.y)
    }

}