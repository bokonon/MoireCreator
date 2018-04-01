package ys.moire.presentation.ui.viewparts.type

import android.graphics.Path
import android.graphics.PointF
import java.util.*

/**
 * custom line class.
 */
class CustomLine : BaseType() {

    val path: Path = Path()

    private val pathList: MutableList<PointF> = ArrayList()
    //    private final Object lock = new Object();

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
     */
    internal fun checkParcellingOutOfRange(whichLine: Int, layoutWidth: Int) {
        var underZeroCount = 0
        var overWidthCount = 0
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
        val diff = maxX - minX
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

        private const val TAG = "CustomLine"

        private const val LINE_A = 0
        private const val LINE_B = 1
    }

}
