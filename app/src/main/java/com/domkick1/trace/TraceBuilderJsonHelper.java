package com.domkick1.trace;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by domin_2o9sb4z on 2016-02-17.
 */
public class TraceBuilderJsonHelper {

    private JsonHelper jsonHelper;

    private JSONObject jsonLevelsObject;

    private final String shapeFile = "shapes.json";
    private final String pointsFile = "points.json";

    public TraceBuilderJsonHelper(Context context) {
        jsonHelper = new JsonHelper(context);
        jsonLevelsObject = jsonHelper.readFromInternal();
        Log.d("DOM", "Internal didnt' exist: " + (jsonLevelsObject == null));
        if (jsonLevelsObject == null)
            jsonLevelsObject = jsonHelper.getJsonFromAssets(shapeFile);
        Log.d("DOM", jsonLevelsObject.toString());
    }

    public PointList getGridAsPoints(TraceBuilder.Mode mode, int width, int height, int topOffset) {
        return new PointList(getGridByMode(mode)).getCenteredPoints(width, height, topOffset);
    }

    private JSONArray getGridByMode(TraceBuilder.Mode mode) {
        try {
            JSONObject jsonFromFile = jsonHelper.getJsonFromAssets(pointsFile);
            return jsonFromFile.getJSONArray(mode.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    public void generateUpdatedJson(LineList level) {
        appendLevelToJson(level);
        generateJsonFile();

    }

    private void appendLevelToJson(LineList level) {
        try {
            JSONArray levelsArray = jsonLevelsObject.getJSONArray("levels").put(level.toJsonArray());
            jsonLevelsObject = new JSONObject();
            jsonLevelsObject.put("levels", levelsArray);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    private void generateJsonFile() {
        jsonHelper.writeToInternal(jsonLevelsObject.toString());
        jsonHelper.writeToDownloads(jsonLevelsObject.toString(), shapeFile);
    }

    public void loadLevelsFromAssets() {
        jsonLevelsObject = jsonHelper.getJsonFromAssets(shapeFile);
    }

}
