package com.domkick1.trace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by domin_2o9sb4z on 2016-02-25.
 */
public class LevelState {

    private ArrayList<Integer> nonPlayedLevels;
    private Integer currentLevel;

    public LevelState(int currentLevel, int numberOfLevels) {
        this.currentLevel = currentLevel;
        this.nonPlayedLevels = new ArrayList<>(numberOfLevels);
        for (int i = 0; i < numberOfLevels; i++)
            nonPlayedLevels.add(i);
    }

    public LevelState(JSONObject jObj) {
        try {
            this.currentLevel = jObj.getInt(LevelSaver.currentLevel);
            this.nonPlayedLevels = jsonToIntArray(jObj.getJSONArray(LevelSaver.notPlayedLevels));
        } catch (JSONException e) {
            throw new UnsupportedOperationException();
        }
    }

    public void levelPlayed() {
        nonPlayedLevels.remove(currentLevel);
    }

    public boolean nextLevel() {
        if (nonPlayedLevels.isEmpty())
            return false;
        currentLevel = nonPlayedLevels.get((int) (Math.random() * nonPlayedLevels.size()));
        return true;
    }

    private ArrayList<Integer> jsonToIntArray(JSONArray jArray) {
        ArrayList<Integer> intList = new ArrayList<>(jArray.length());
        for (int i = 0; i < jArray.length(); i++) {
            try {
                intList.add(jArray.getInt(i));
            } catch (JSONException e) {
                return null;
            }
        }
        return intList;
    }

    private JSONArray listToJson(ArrayList<Integer> intList) {
        JSONArray jArray = new JSONArray();
        for (Integer i : intList)
            jArray.put(i);
        return jArray;
    }

    public JSONObject toJson() {
        JSONObject jObj = new JSONObject();
        try {
            jObj.put(LevelSaver.currentLevel, currentLevel);
            jObj.put(LevelSaver.notPlayedLevels, listToJson(nonPlayedLevels));
            return jObj;
        } catch (JSONException e) {
            throw new UnsupportedOperationException();
        }
    }

    public ArrayList<Integer> getNonPlayedLevels() {
        return nonPlayedLevels;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}
