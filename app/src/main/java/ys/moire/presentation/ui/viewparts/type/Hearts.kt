package ys.moire.presentation.ui.viewparts.type

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import ys.moire.BuildConfig

/**
 * Heart set class.
 */
class Hearts : BaseTypes() {

    /** Heart array  */
    private lateinit var heart: Array<Heart>

    override fun init(whichLine: Int,
             layoutWidth: Int,
             layoutHeight: Int) {

        heart = if (whichLine == BaseTypes.LINE_A) {
            Array(number, {
                Heart(0f,
                        layoutHeight.toFloat() * 5 / 18,
                        layoutWidth.toFloat() / number * (it + 1),
                        layoutHeight.toFloat() * 2 / 3 / number * (it + 1))
            })
        } else {
            Array(number, {
                Heart(layoutWidth.toFloat(),
                        layoutHeight.toFloat() * 2 / 3,
                        layoutWidth.toFloat() / number * (it + 1),
                        layoutHeight.toFloat() * 2 / 3 / number * (it + 1))
            })
        }
    }

    public override fun checkOutOfRange(layoutWidth: Int) {
        if (heart[number - 1].rightTop[0].x < 0) {
            // disappear for less than 0
            for (i in 0 until number) {
                heart[i].moveX(
                        layoutWidth + layoutWidth.toFloat() / 2,
                        heart[i].centerTop.y + (heart[i].height / 2 - heart[i].height / 5))
            }
        } else if (layoutWidth < heart[number - 1].leftTop[1].x) {
            // disappear for more than width
            for (i in 0 until number) {
                heart[i].moveX(
                        - layoutWidth.toFloat() / 2,
                        heart[i].centerTop.y + (heart[i].height / 2 - heart[i].height / 5))
            }
        }
    }

    public override fun move(whichLine: Int) {
        if (onTouch) {
            return
        }
        if (whichLine == BaseTypes.LINE_A) {
            for (i in 0 until number) {
                heart[i].autoMove(dx)
            }
        } else if (whichLine == BaseTypes.LINE_B) {
            for (i in 0 until number) {
                heart[i].autoMove(-dx)
            }
        }
    }

    public override fun draw(canvas: Canvas) {
        for (i in 0 until number) {
            if (BuildConfig.DEBUG) {
                // make first line to blue color for only debug
                if (i == 0) {
                    val p = Paint()
                    p.style = Paint.Style.STROKE
                    p.color = Color.BLUE
                    p.strokeWidth = thick.toFloat()
                    canvas.drawPath(heart[i].path, p)
                    continue
                }
                // make last line to red color for only debug build
                if (i == number - 1) {
                    val p = Paint()
                    p.style = Paint.Style.STROKE
                    p.color = Color.RED
                    p.strokeWidth = thick.toFloat()
                    canvas.drawPath(heart[i].path, p)
                    continue
                }
            }
            canvas.drawPath(heart[i].path, paint)
        }
    }

    /**
     * add touch value.
     * @param valX dx
     */
    public override fun addTouchVal(valX: Int, valY: Int) {
        for (i in 0 until number) {
            heart[i].addTouchVal(valX, valY)
        }
    }

    companion object {
        private const val TAG = "hearts"
    }
}