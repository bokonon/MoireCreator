package ys.moire.presentation.presenter.main

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

import lombok.RequiredArgsConstructor
import ys.moire.BuildConfig
import ys.moire.R
import ys.moire.domain.usecase.LoadMoireUseCase
import ys.moire.presentation.presenter.base.BasePresenter
import ys.moire.presentation.ui.view_parts.MoireView
import ys.moire.presentation.ui.view_parts.type.BaseTypes

import com.google.android.gms.wearable.DataMap.TAG

/**
 * MainPresenter.
 */
class MainPresenter(private val context: Context?, private val loadMoireUseCase: LoadMoireUseCase?) : BasePresenter() {

    private var moireView: MoireView? = null

    val moireType: Int
        get() = loadMoireUseCase!!.moireType

    val bgColor: Int
        get() = loadMoireUseCase!!.bgColor

    fun setMoireView(moireView: MoireView) {
        this.moireView = moireView
    }

    fun loadTypesData(key: String): BaseTypes {
        return loadMoireUseCase!!.getTypesData(key)
    }

    fun captureCanvas() {
        // decide stored directory.
        val dir: File
        val path = Environment.getExternalStorageDirectory().toString() + "/ys.MoireCreator/"
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            dir = File(path)
            dir.mkdirs()
        } else {
            dir = Environment.getDataDirectory()
        }
        // create file name.
        val fileName = fileName
        val AttachName = dir.absolutePath + "/" + fileName

        val file = File(path + fileName)
        var fos: FileOutputStream? = null
        val bitmap = createBitmap()
        try {
            fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: FileNotFoundException) {
            showToast(context!!.getString(R.string.message_capture_failed))
        } finally {
            bitmap.recycle()
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    Log.e(TAG, e.message, e)
                }

            }
        }
        // reflected in gallery.
        val values = ContentValues()
        val contentResolver = context!!.contentResolver
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.TITLE, fileName)
        values.put("_data", AttachName)
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        showToast(context.getString(R.string.message_capture_success)
                + "\nFilePath : " + AttachName)
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "FilePath : " + AttachName)
        }
    }

    private fun createBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(moireView!!.width, moireView!!.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        moireView!!.captureCanvas(canvas)
        return bitmap
    }

    private val fileName: String
        get() {
            val formatter = SimpleDateFormat("yyyyMMddHHmmss")
            val date = Date()
            return "Moire_" + formatter.format(date) + ".png"
        }

    private fun showToast(message: String) {
        Toast.makeText(context!!.applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
