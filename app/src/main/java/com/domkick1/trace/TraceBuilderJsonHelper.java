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

//    private JsonHelper jsonHelper;
//
//    private JSONObject jsonLevelsObject;
//
//    private final String shapeFile = "shapes.json";
//
//    public TraceBuilderJsonHelper(Context context) {
//        jsonHelper = new JsonHelper(context);
//        jsonLevelsObject = jsonHelper.readFromInternal();
//        Log.d("DOM", "Internal didnt' exist: " + (jsonLevelsObject == null));
//        if (jsonLevelsObject == null)
//            jsonLevelsObject = jsonHelper.getJsonFromAssets(shapeFile);
//        Log.d("DOM", jsonLevelsObject.toString());
//    }
//
//    public void generateUpdatedJson(LineList level) {
//        appendLevelToJson(level);
//        generateJsonFile();
//
//    }
//
//    private void appendLevelToJson(LineList level) {
//        try {
//            JSONArray levelsArray = jsonLevelsObject.getJSONArray("levels").put(level.toJsonArray());
//            jsonLevelsObject = new JSONObject();
//            jsonLevelsObject.put("levels", levelsArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            throw new UnsupportedOperationException();
//        }
//    }
//
//    private void generateJsonFile() {
//        jsonHelper.writeToInternal(jsonLevelsObject.toString());
//        jsonHelper.writeToDownloads(jsonLevelsObject.toString(), shapeFile);
//    }
//
//    public void loadLevelsFromAssets() {
//        jsonLevelsObject = jsonHelper.getJsonFromAssets(shapeFile);
//    }

}
