package ys.moire.presentation.ui.viewparts

import android.content.Context
import android.graphics.*
import android.view.SurfaceHolder
import android.view.SurfaceView
import ys.moire.R
import ys.moire.common.sensor.SensorEL

/**
 * about moire view class
 * description screen
 */
class AboutView(context: Context,
                /** SensorEventListener  */
                private val sensorEL: SensorEL, private val layoutWidth: Float, private val layoutHeight: Float) : SurfaceView(context), SurfaceHolder.Callback {
    /** char image bitmap  */
    private val bmp = arrayOfNulls<Bitmap>(145)
    /** char image point  */
    private val bmpPoint = arrayOfNulls<PointF>(145)
    /** paint  */
    private var paint: Paint? = null

    init {
        holder.addCallback(this)
    }

    fun drawFrame() {
        // get canvas
        val canvas = holder.lockCanvas()
        if (canvas != null) {
            // draw canvas
            canvas.drawColor(Color.WHITE)
            for (i in bmp.indices) {
                val r = Math.random().toFloat() * 5
                val abDx = VAL / 2f * (r / 5)
                bmpPoint[i]!!.set(bmpPoint[i]!!.x - sensorEL.sensorX * abDx, bmpPoint[i]!!.y + sensorEL.sensorY * abDx)
                checkInFrame(i, abDx)
                canvas.drawBitmap(bmp[i], bmpPoint[i]!!.x, bmpPoint[i]!!.y, paint)
            }
            holder.unlockCanvasAndPost(canvas)
        }
    }

    // called when SurfaceView is created.
    override fun surfaceCreated(holder: SurfaceHolder) {

        val tArray = resources.obtainTypedArray(R.array.chara_items)
        for (i in 0 until tArray.length()) {
            val resource = tArray.getResourceId(i, 0)
            bmp[i] = BitmapFactory.decodeResource(resources, resource)
        }
        // init char point
        // line1
        for (i in 0..14) {
            if (i == 0) {
                bmpPoint[i] = PointF(layoutWidth / 17 * (i + 1) + 20, layoutHeight / 10 - bmp[i]!!.getHeight())
            } else if (i == 2) {
                bmpPoint[i] = PointF(layoutWidth / 17 * (i + 1) - 10, layoutHeight / 10 - bmp[i]!!.getHeight())
            } else if (i == 4) {
                bmpPoint[i] = PointF(layoutWidth / 17 * (i + 1) + 10, layoutHeight / 10 - bmp[i]!!.getHeight())
            } else if (i == 8) {
                bmpPoint[i] = PointF(layoutWidth / 17 * (i + 1) + 5, layoutHeight / 10 - bmp[i]!!.getHeight() * 4 / 5)
            } else {
                bmpPoint[i] = PointF(layoutWidth / 17 * (i + 1) + 5, layoutHeight / 10 - bmp[i]!!.getHeight())
            }
        }
        // line2
        for (i in 15..32) {
            if (i == 15 || i == 21) {
                bmpPoint[i] = PointF(layoutWidth / 19 * (i - 15) + 10, layoutHeight * 2 / 10 - bmp[i]!!.getHeight())
            } else {
                bmpPoint[i] = PointF(layoutWidth / 19 * (i - 15) + 5, layoutHeight * 2 / 10 - bmp[i]!!.getHeight())
            }
        }
        // line3
        for (i in 33..48) {
            if (i == 33) {
                bmpPoint[i] = PointF(layoutWidth / 18 * (i - 33) + 5, layoutHeight * 3 / 10 - bmp[i]!!.getHeight() * 4 / 5)
            } else {
                bmpPoint[i] = PointF(layoutWidth / 18 * (i - 33) + 5, layoutHeight * 3 / 10 - bmp[i]!!.getHeight())
            }
        }
        // line4
        for (i in 49..64) {
            if (i == 57) {
                bmpPoint[i] = PointF(layoutWidth / 16 * (i - 49) + 15, layoutHeight * 4 / 10 - bmp[i]!!.getHeight() * 4 / 5)
            } else if (i == 49 || i == 58) {
                bmpPoint[i] = PointF(layoutWidth / 16 * (i - 49) + 15, layoutHeight * 4 / 10 - bmp[i]!!.getHeight())
            } else if (i == 61) {
                bmpPoint[i] = PointF(layoutWidth / 16 * (i - 49), layoutHeight * 4 / 10 - bmp[i]!!.getHeight())
            } else {
                bmpPoint[i] = PointF(layoutWidth / 16 * (i - 49) + 5, layoutHeight * 4 / 10 - bmp[i]!!.getHeight())
            }
        }
        // line5
        for (i in 65..77) {
            if (i == 65) {
                bmpPoint[i] = PointF(layoutWidth / 15 * (i - 65) + 10, layoutHeight * 5 / 10 - bmp[i]!!.getHeight())
            } else if (i == 69) {
                bmpPoint[i] = PointF(layoutWidth / 15 * (i - 65) + 5, layoutHeight * 5 / 10 - bmp[i]!!.getHeight() * 4 / 5)
            } else {
                bmpPoint[i] = PointF(layoutWidth / 15 * (i - 65) + 5, layoutHeight * 5 / 10 - bmp[i]!!.getHeight())
            }
        }
        // line6
        for (i in 78..98) {
            if (i == 95) {
                bmpPoint[i] = PointF(layoutWidth / 21 * (i - 78) + 5, layoutHeight * 6 / 10 - bmp[i]!!.getHeight() * 4 / 5)
            } else {
                bmpPoint[i] = PointF(layoutWidth / 21 * (i - 78) + 5, layoutHeight * 6 / 10 - bmp[i]!!.getHeight())
            }
        }
        // line7
        for (i in 99..115) {
            if (i == 102) {
                bmpPoint[i] = PointF(layoutWidth / 20 * (i - 98) - 15, layoutHeight * 7 / 10 - bmp[i]!!.getHeight())
            } else if (i == 110) {
                bmpPoint[i] = PointF(layoutWidth / 20 * (i - 98) + 5, layoutHeight * 7 / 10 - bmp[i]!!.getHeight() * 4 / 5)
            } else {
                bmpPoint[i] = PointF(layoutWidth / 20 * (i - 98) + 5, layoutHeight * 7 / 10 - bmp[i]!!.getHeight())
            }
        }
        // line8
        for (i in 116..133) {
            if (i == 116 || i == 117 || i == 118 || i == 126) {
                bmpPoint[i] = PointF(layoutWidth / 20 * (i - 116) + 10, layoutHeight * 8 / 10 - bmp[i]!!.getHeight())
            } else if (i == 119 || i == 123) {
                bmpPoint[i] = PointF(layoutWidth / 20 * (i - 116) + 5, layoutHeight * 8 / 10 - bmp[i]!!.getHeight() * 4 / 5)
            } else {
                bmpPoint[i] = PointF(layoutWidth / 20 * (i - 116) + 5, layoutHeight * 8 / 10 - bmp[i]!!.getHeight())
            }
        }
        // line9
        for (i in 134..144) {
            if (i == 134) {
                bmpPoint[i] = PointF(layoutWidth / 15 * (i - 130) - 5, layoutHeight * 9 / 10 - bmp[i]!!.getHeight())
            } else if (i == 139 || i == 140) {
                bmpPoint[i] = PointF(layoutWidth / 15 * (i - 130) + 10, layoutHeight * 9 / 10 - bmp[i]!!.getHeight())
            } else {
                bmpPoint[i] = PointF(layoutWidth / 15 * (i - 130) + 5, layoutHeight * 9 / 10 - bmp[i]!!.getHeight())
            }
        }
        paint = Paint()
        drawFrame()
    }

    // called when SurfaceView is changed.
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        drawFrame()
    }

    // called when SurfaceView is destroyed.
    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }

    private fun checkInFrame(i: Int, ab_dx: Float) {
        if (bmpPoint[i]!!.x - sensorEL.sensorX * ab_dx < 0) {
            bmpPoint[i]!!.set(0f, bmpPoint[i]!!.y)
        } else if (layoutWidth < bmpPoint[i]!!.x - sensorEL.sensorX * ab_dx + bmp[i]!!.width) {
            bmpPoint[i]!!.set(layoutWidth - bmp[i]!!.width, bmpPoint[i]!!.y)
        }
        if (bmpPoint[i]!!.y + sensorEL.sensorY * ab_dx < 0) {
            bmpPoint[i]!!.set(bmpPoint[i]!!.x, 0f)
        } else if (layoutHeight < bmpPoint[i]!!.y + sensorEL.sensorY * ab_dx + bmp[i]!!.height.toFloat()) {
            bmpPoint[i]!!.set(bmpPoint[i]!!.x, layoutHeight - bmp[i]!!.height)
        }
    }

    companion object {

        /** move value  */
        private const val VAL = 8
    }
}