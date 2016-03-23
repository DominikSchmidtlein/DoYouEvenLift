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

    public LevelList(int size) {
        super(size);
    }

    public LevelList(@NonNull JSONObject jsonObject, @NonNull ScreenDimensions dimensions) {
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

}
