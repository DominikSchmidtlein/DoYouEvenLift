package com.domkick1.trace;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by domin_2o9sb4z on 2016-02-16.
 */
public class LevelHelper {

    private final String shapeFile = "shapes.json";
    Context context;

    public LevelHelper(Context context) {
        this.context = context;
    }

    public int getLevelCount() {
        try {
            return getJsonFromFile(shapeFile).getJSONArray("levels").length();
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    public LineList getLevelAsLines(int level, int width, int height, int topOffset) {
        return new LineList(
                new PointList(getLevelFromFile(level)).getCenteredPoints(width, height, topOffset),
                false);
    }

    private JSONArray getLevelFromFile(int level) {
        try {
            JSONObject jsonObject = getJsonFromFile(shapeFile);
            return jsonObject.getJSONArray("levels").getJSONArray(level);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    private JSONObject getJsonFromFile(String filename) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream file = assetManager.open(filename);
            byte[] data = new byte[file.available()];
            file.read(data);
            file.close();
            return new JSONObject(new String(data));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void writeJsonToFile(String jsonString) {
        Log.d("DOM", jsonString);
        try {
            Log.d("DOM", Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) + "");

            // check for permission at runtime
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            // create new file in downloads dir with name test.json
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "test.json");

            if (!file.exists())
                file.createNewFile();

            file.setReadable(true, false);

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write("test string".getBytes());
            fileOutputStream.close();

            for (int i = 0; i < Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).listFiles().length; i++)
                Log.d("DOM", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).listFiles()[i].getAbsolutePath() + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void extendAndStoreShapesJson(LineList level) {
        JSONArray newLevel = level.toJsonArray();
        try {
            JSONObject jsonData = getJsonFromFile(shapeFile);
            JSONArray levelArray = jsonData.getJSONArray("levels");
            levelArray.put(newLevel);
            jsonData = new JSONObject();
            jsonData.put("levels", levelArray);
            writeJsonToFile(jsonData.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
