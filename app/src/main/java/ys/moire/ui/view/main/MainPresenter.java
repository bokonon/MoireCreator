package ys.moire.ui.view.main;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ys.moire.BuildConfig;
import ys.moire.R;
import ys.moire.domain.SharedPreferencesManager;
import ys.moire.type.BaseTypes;
import ys.moire.ui.base.BasePresenter;
import ys.moire.ui.view.moire.MoireView;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * MainPresenter.
 */

public class MainPresenter extends BasePresenter<MainMvpView> {

    private final Context activityContext;
    private MoireView moireView;
    private SharedPreferencesManager preferencesManager;

    public MainPresenter(final Context activityContext) {
        this.activityContext = activityContext;
        preferencesManager = new SharedPreferencesManager(activityContext);
    }

    @Override
    public void onCreate(MainMvpView mvpView) {
        super.onCreate(mvpView);
    }

    public int getMoireType() {
        return preferencesManager.getMoireType();
    }

    public int getBgColor() {
        return preferencesManager.getBgColor();
    }

    public void setMoireView(final MoireView moireView) {
        this.moireView = moireView;
    }

    public BaseTypes loadTypesData(final String key) {
        return preferencesManager.loadTypesData(key);
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
            getMvpView().showToast(activityContext.getString(R.string.message_capture_failed));
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
        ContentResolver contentResolver = activityContext.getContentResolver();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put("_data", AttachName);
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        getMvpView().showToast(activityContext.getString(R.string.message_capture_success)
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
}
