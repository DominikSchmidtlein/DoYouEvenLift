package com.domkick1.trace;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by domin_2o9sb4z on 2016-03-18.
 */
public class LevelList extends ArrayList<LineList> {

    public LevelList(@NonNull JSONObject jsonObject, @NonNull ScreenDimensions dimensions) {
        super();
        try {
            JSONArray levels = jsonObject.getJSONArray(StateLoader.levelsKey);
            ensureCapacity(levels.length());
            for (int i = 0; i < levels.length(); i++)
                add(new LineList(levels.getJSONArray(i), dimensions));
        } catch (JSONException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    public JSONArray toJson() {
        throw new UnsupportedOperationException();
    }

}
