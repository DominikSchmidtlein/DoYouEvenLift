package com.domkick1.trace;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by domin_2o9sb4z on 2016-02-17.
 */
public class JsonHelper {

    private String tempLevelsFile = "temp_levels.json";

    private Context context;

    public JsonHelper(Context context) {
        this.context = context;
    }

    public JSONObject getJsonFromAssets(String filename) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream file = assetManager.open(filename);
            byte[] data = new byte[file.available()];
            file.read(data);
            file.close();
            return new JSONObject(new String(data));
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    public JSONObject readFromInternal() {
        try {
            byte[] buf = new byte[(int) new File(context.getFilesDir(), tempLevelsFile).length()];
            FileInputStream inputStream = context.openFileInput(tempLevelsFile);
            inputStream.read(buf);
            inputStream.close();
            return new JSONObject(new String(buf));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeToInternal(String jsonString) {
        try {
            FileOutputStream outputStream = context.openFileOutput(tempLevelsFile, Context.MODE_PRIVATE);
            outputStream.write(jsonString.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToDownloads(String jsonString, String filename) {
        for (int i = 0; i < jsonString.length(); i += 1024)
            Log.d("DOM", jsonString.substring(i, (i + 1024 < jsonString.length()) ? i + 1024 : jsonString.length()));
//        Log.d("DOM", "Is external writable: " + Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED));
//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
//        Log.d("DOM", "File exists: " + file.exists() + ", File path: " + file.getAbsolutePath());
//        if (!file.exists())
//            file.mkdirs();
//        file = new File(file, filename);
//        Log.d("DOM", "Readable: " + file.canRead() + ", Writable: " + file.canWrite() + ", Hidden: " + file.isHidden());
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
//            bufferedOutputStream.write(jsonString.getBytes());
//            bufferedOutputStream.close();
//            fileOutputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
