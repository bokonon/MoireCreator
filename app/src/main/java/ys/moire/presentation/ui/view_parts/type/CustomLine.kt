package ys.moire.presentation.ui.view_parts.type

import android.graphics.Path
import android.graphics.PointF
import java.util.*

/**
 * custom line class.
 */
class CustomLine : BaseType() {

    var path: Path

    var pathList: MutableList<PointF>
    //    private final Object lock = new Object();

    init {
        path = Path()
        pathList = ArrayList<PointF>()
    }

    /**
     * scroll custom line automatically.
     * @param dx dx
     */
    public override fun autoMove(dx: Float) {
        for (point in pathList) {
            point.x += dx
        }
        translateToPath()
    }

    /**
     * check if the custom line is out of range.
     * @param layoutWidth screen width
     * *
     * @param slope no use with custom line
     */
    internal fun checkOutOfRange(whichLine: Int, layoutWidth: Int, slope: Int) {
        var underZeroCount = 0
        var overWidthCount = 0
        var diff = 0f
        var minX = 0f
        var maxX = 0f
        for (point in pathList) {
            if (whichLine == LINE_A) {
                if (layoutWidth < point.x) {
                    overWidthCount++
                    if (point.x < minX) {
                        minX = point.x
                    }
                    if (maxX < point.x) {
                        maxX = point.x
                    }
                }
            } else if (whichLine == LINE_B) {
                if (point.x < 0) {
                    underZeroCount++
                    if (point.x < minX) {
                        minX = point.x
                    }
                    if (maxX < point.x) {
                        maxX = point.x
                    }
                }
            }
        }
        diff = maxX - minX
        // less than 0
        if (whichLine == LINE_B && underZeroCount == pathList.size) {
            for (point in pathList) {
                point.x = layoutWidth + point.x
            }
            translateToPath()
        } else if (whichLine == LINE_A && overWidthCount == pathList.size) {
            for (point in pathList) {
                point.x = -diff + point.x
            }
            translateToPath()
        }// more than width
    }

    /**
     * add moving value.
     * @param valX  dx
     * *
     * @param valY  dy
     */
    fun drawOriginalLine(valX: Float, valY: Float, moveCount: Int) {
        if (moveCount == 0) {
            startPath(valX, valY)
        } else {
            addLineToPath(valX, valY)
        }
    }

    private fun startPath(x: Float, y: Float) {
        path.reset()
        path.moveTo(x, y)

        pathList.clear()
        pathList.add(PointF(x, y))
    }

    private fun addLineToPath(x: Float, y: Float) {
        path.lineTo(x, y)
        pathList.add(PointF(x, y))
    }

    private fun translateToPath() {
        var num = 0
        for (point in pathList) {
            if (num == 0) {
                path.reset()
                path.moveTo(point.x, point.y)
            } else {
                path.lineTo(point.x, point.y)
            }
            num++
        }
    }

    companion object {

        private val TAG = "CustomLine"

        private val LINE_A = 0
        private val LINE_B = 1
    }

}
