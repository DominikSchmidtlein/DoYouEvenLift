package com.domkick1.trace;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by domin_2o9sb4z on 2016-03-18.
 */
public class RemainingLevels extends ArrayList<Integer> {

    public RemainingLevels(int size) {
        super(size);
    }

    public RemainingLevels(@NonNull JSONObject statejson) {
        try {
            JSONArray jArray = statejson.getJSONArray(StateLoader.remainingLevelsKey);
            ensureCapacity(jArray.length());
            for (int i = 0; i < jArray.length(); i++)
                add(jArray.getInt(i));
        } catch (JSONException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    public JSONArray toJson() {
        JSONArray jArray = new JSONArray();
        for (Integer i : this)
            jArray.put(i);
        return jArray;
    }

    /**
     * Randomly selects one of the indices from remaining levels
     *
     * @return
     */
    public Integer chooseLevel() {
        if (isEmpty())
            return null;
        return get((int) (Math.random() * size()));
    }

    public void initializeRemainingLevels(int size) {
        clear();
        ensureCapacity(size);
        for (int i = 0; i < size; i++)
            add(i);
    }

}
