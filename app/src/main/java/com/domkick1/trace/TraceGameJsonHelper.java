package com.domkick1.trace;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by domin_2o9sb4z on 2016-02-17.
 */
public class TraceGameJsonHelper {

    private JsonHelper jsonHelper;
    private final String shapeFile = "shapes.json";

    public TraceGameJsonHelper(Context context) {
        jsonHelper = new JsonHelper(context);
    }

    public int getLevelCount() {
        try {
            return jsonHelper.getJsonFromAssets(shapeFile).getJSONArray("levels").length();
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
            JSONObject jsonObject = jsonHelper.getJsonFromAssets(shapeFile);
            return jsonObject.getJSONArray("levels").getJSONArray(level);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

}
