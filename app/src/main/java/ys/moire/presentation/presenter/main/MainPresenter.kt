package ys.moire.presentation.presenter.main

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import ys.moire.BuildConfig
import ys.moire.R
import ys.moire.common.config.TypeEnum
import ys.moire.domain.usecase.LoadMoireUseCase
import ys.moire.presentation.presenter.base.BasePresenter
import ys.moire.presentation.ui.viewparts.MoireView
import ys.moire.presentation.ui.viewparts.type.BaseTypes
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * MainPresenter.
 */
class MainPresenter(private val context: Context, private val loadMoireUseCase: LoadMoireUseCase) : BasePresenter() {

    companion object {
        private val TAG: String = MainPresenter::class.java.simpleName
    }

    private lateinit var moireView: MoireView

    val moireType: TypeEnum
        get() = loadMoireUseCase.moireType

    val bgColor: Int
        get() = loadMoireUseCase.bgColor

    fun setMoireView(moireView: MoireView) {
        this.moireView = moireView
    }

    fun loadTypesData(key: String): BaseTypes {
        return loadMoireUseCase.getTypesData(key)
    }

    fun captureCanvas() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            values.put(MediaStore.Images.Media.IS_PENDING, 1)

            val contentResolver: ContentResolver = context.contentResolver
            val collection = MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL_PRIMARY)
            val item = contentResolver.insert(collection, values)
            item?.let { it ->
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "Uri : $it")
                }
                val result = storeImage(contentResolver, item)
                if (result) {
                    values.clear()
                    values.put(MediaStore.Images.Media.IS_PENDING, 0)
                    contentResolver.update(item, values, null, null)
                    showToast(context.getString(R.string.message_capture_success))
                } else {
                    showToast(context.getString(R.string.message_capture_failed))
                }
            }
        } else {
            val dir: File
            val path = Environment.getExternalStorageDirectory().toString()
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                dir = File(path)
                dir.mkdirs()
            } else {
                dir = Environment.getDataDirectory()
            }
            val fileName = fileName
            val attachName = dir.absolutePath + "/" + fileName

            val result = storeImage(path, fileName)
            if (result) {
                // reflected in gallery.
                val values = ContentValues()
                val contentResolver = context.contentResolver
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                values.put(MediaStore.Images.Media.TITLE, fileName)
                values.put("_data", attachName)
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

                showToast(context.getString(R.string.message_capture_success)
                        + "\nFilePath : " + attachName)
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "FilePath : $attachName")
                }
            } else {
                showToast(context.getString(R.string.message_capture_failed))
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun createBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(moireView.width, moireView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        moireView.captureCanvas(canvas)
        return bitmap
    }

    private val fileName: String
        get() {
            val formatter = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
            val date = Date()
            return "Moire_" + formatter.format(date) + ".png"
        }

    private fun storeImage(contentResolver: ContentResolver, item: Uri): Boolean {
        val bitmap = createBitmap()
        try {
            contentResolver.openOutputStream(item).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }
        } catch (e: IOException) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, e.message, e)
            }
            return false
        }
        return true
    }

    private fun storeImage(path: String, fileName: String): Boolean {
        val file = File("$path/$fileName")
        var fos: FileOutputStream? = null
        val bitmap = createBitmap()
        try {
            fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            return true
        } catch (e: FileNotFoundException) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, e.message, e)
            }
            return false
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
    }
}
