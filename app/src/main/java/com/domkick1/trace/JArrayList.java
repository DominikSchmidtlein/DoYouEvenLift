package com.domkick1.trace;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by domin_2o9sb4z on 2016-03-18.
 */
public class JArrayList extends ArrayList<Integer> {

    public JArrayList(int size) {
        super(size);
    }

    public JSONArray toJson() {
        JSONArray jArray = new JSONArray();
        for (Integer i : this)
            jArray.put(i);
        return jArray;
    }

    public void initializeRemainingLevels(int size) {
        clear();
        ensureCapacity(size);
        for (int i = 0; i < size; i++)
            add(i);
    }

}
