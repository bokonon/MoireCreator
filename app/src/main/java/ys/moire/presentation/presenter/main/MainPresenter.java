package ys.moire.presentation.presenter.main;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.RequiredArgsConstructor;
import ys.moire.BuildConfig;
import ys.moire.R;
import ys.moire.domain.usecase.LoadMoireUseCase;
import ys.moire.presentation.presenter.base.BasePresenter;
import ys.moire.presentation.ui.view_parts.MoireView;
import ys.moire.presentation.ui.view_parts.type.BaseTypes;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * MainPresenter.
 */
@RequiredArgsConstructor(suppressConstructorProperties = true)
public class MainPresenter extends BasePresenter {

    private final Context context;

    private final LoadMoireUseCase loadMoireUseCase;

    private MoireView moireView;

    public int getMoireType() {
        return loadMoireUseCase.getMoireType();
    }

    public int getBgColor() {
        return loadMoireUseCase.getBgColor();
    }

    public void setMoireView(final MoireView moireView) {
        this.moireView = moireView;
    }

    public BaseTypes loadTypesData(final String key) {
        return loadMoireUseCase.getTypesData(key);
    }

    public void captureCanvas() {
        // decide stored directory.
        File dir;
        String path = Environment.getExternalStorageDirectory() + "/ys.MoireCreator/";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dir = new File(path);
            dir.mkdirs();
        } else {
            dir = Environment.getDataDirectory();
        }
        // create file name.
        String fileName = getFileName();
        String AttachName = dir.getAbsolutePath() + "/" + fileName;

        File file = new File(path + fileName);
        FileOutputStream fos = null;
        Bitmap bitmap = createBitmap();
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch(FileNotFoundException e) {
            showToast(context.getString(R.string.message_capture_failed));
        } finally {
            bitmap.recycle();
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }
        // reflected in gallery.
        ContentValues values = new ContentValues();
        ContentResolver contentResolver = context.getContentResolver();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put("_data", AttachName);
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        showToast(context.getString(R.string.message_capture_success)
                +"\nFilePath : "+AttachName);
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "FilePath : "+AttachName);
        }
    }

    private Bitmap createBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(moireView.getWidth(), moireView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        moireView.captureCanvas(canvas);
        return bitmap;
    }

    private String getFileName() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return "Moire_" + formatter.format(date) + ".png";
    }

    private void showToast(final String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
