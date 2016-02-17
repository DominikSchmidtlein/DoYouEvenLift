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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by domin_2o9sb4z on 2016-02-16.
 */
public class LevelHelper {

    private final String shapeFile = "shapes.json";
    private final String pointsFile = "points.json";
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

    public void addLevelToShapes(ArrayList<Line> level) {

        JSONArray levelAsJson = new JSONArray(Arrays.asList(linesAsFloats(level)));

        try {
            JSONObject jsonData = getJsonFromFile(shapeFile);
            JSONArray levelArray = jsonData.getJSONArray("levels");
            levelArray.put(levelAsJson);
            writeJsonToFile(levelArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Line> getLevelAsLines(int level, int width, int height, int topOffset) {
        float[] centeredLines = centerPoints(getLevelFromFile(level), width, height, topOffset);

        ArrayList<Line> lines = new ArrayList<>(centeredLines.length / 4);
        for (int i = 0; i < centeredLines.length; i += 4)
            lines.add(new Line(centeredLines[i], centeredLines[i + 1], centeredLines[i + 2], centeredLines[i + 3]));
        return lines;
    }

    public ArrayList<Point> getGridAsPoints(TraceBuilder.Mode mode, int width, int height, int topOffset) {
        float[] centeredPoints = centerPoints(getGridByMode(mode), width, height, topOffset);

        ArrayList<Point> points = new ArrayList<>(centeredPoints.length / 2);
        for (int i = 0; i < centeredPoints.length; i += 2)
            points.add(new Point(centeredPoints[i], centeredPoints[i + 1]));
        return points;
    }

    private float[] getGridByMode(TraceBuilder.Mode mode) {
        return (mode == TraceBuilder.Mode.ISOMETRIC) ? getIsometricPoints() : getSquarePoints();
    }

    private float[] getIsometricPoints() {
        try {
            JSONObject jsonFromFile = getJsonFromFile(pointsFile);
            JSONArray isometricPoints = jsonFromFile.getJSONObject("points").getJSONArray("isometric");
            return jsonToArray(isometricPoints);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    private float[] getSquarePoints() {
        try {
            JSONObject jsonFromFile = getJsonFromFile(pointsFile);
            JSONArray squarePoints = jsonFromFile.getJSONObject("points").getJSONArray("square");
            return jsonToArray(squarePoints);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    private float[] centerPoints(float[] points, int width, int height, int topOffset) {
        float left = width;
        float top = height;
        float right = 0;
        float bot = 0;

        for (int i = 0; i < points.length; i += 2) {
            left = (points[i] < left) ? points[i] : left;
            right = (points[i] > right) ? points[i] : right;
            top = (points[i + 1] < top) ? points[i + 1] : top;
            bot = (points[i + 1] > bot) ? points[i + 1] : bot;
        }

        float x = (width - right - left) / 2;
        float y = (height - topOffset - top - bot) / 2;

        float[] centeredPoints = new float[points.length];
        for (int i = 0; i < points.length; i += 2) {
            centeredPoints[i] = points[i] + x;
            centeredPoints[i + 1] = points[i + 1] + y;
        }
        return centeredPoints;
    }

    private float[] jsonToArray(JSONArray jsonArray) throws JSONException {
        float[] floats = new float[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++)
            floats[i] = jsonArray.getInt(i);
        return floats;
    }

    private float[] getLevelFromFile(int level) {
        JSONObject jsonObject = getJsonFromFile(shapeFile);
        try {
            JSONArray jsonLevels = jsonObject.getJSONArray("levels");
            JSONArray jsonLevel = jsonLevels.getJSONArray(level);
            return jsonToArray(jsonLevel);
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

    protected float[] linesAsFloats(ArrayList<Line> lines) {
        float[] floats = new float[lines.size() * 4];
        int i = 0;
        for (Line line : lines) {
            floats[i] = line.getP1().getX();
            floats[i + 1] = line.getP1().getY();
            floats[i + 2] = line.getP2().getX();
            floats[i + 3] = line.getP2().getY();
            i += 4;
        }
        return floats;
    }
}
