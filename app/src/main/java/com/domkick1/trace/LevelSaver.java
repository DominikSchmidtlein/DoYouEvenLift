package com.domkick1.trace;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by domin_2o9sb4z on 2016-02-24.
 */
public class LevelSaver {

    private final String levelState = "level_state";
    public static final String currentLevel = "current_level";
    public static final String notPlayedLevels = "not_played_levels";

    private Context context;

    public LevelSaver(Context context) {
        this.context = context;
    }

    public LevelState loadState() {
        return new LevelState(readFromInternal());
    }

    public void saveState(LevelState state) {
        writeToInternal(state.toJson().toString());
    }

    public void resetState(int levels) {
        writeToInternal(new LevelState(0, levels).toJson().toString());
    }

    public JSONObject readFromInternal() {
        try {
            byte[] buf = new byte[(int) new File(context.getFilesDir(), levelState).length()];
            FileInputStream inputStream = context.openFileInput(levelState);
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
            FileOutputStream outputStream = context.openFileOutput(levelState, Context.MODE_PRIVATE);
            outputStream.write(jsonString.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
