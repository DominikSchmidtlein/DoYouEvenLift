package com.domkick1.trace;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by domin_2o9sb4z on 2016-02-25.
 */
public class LevelState {


    private final LevelList levels;
    private final RemainingLevels remainingLevels;
    private Integer currentLevel;

    private final List<LevelStateChangedListener> levelStateChangedListeners = new ArrayList<>();

    /**
     * Picks random level from all available levels
     * @param levels all available levels
     */
    public LevelState(@NonNull LevelList levels) {
        this(0, new RemainingLevels(levels.size()), levels);
        resetLevels();
    }

    public LevelState(@NonNull Integer currentLevel, @NonNull RemainingLevels remainingLevels, @NonNull LevelList levels) {
        this.currentLevel = currentLevel;
        this.remainingLevels = remainingLevels;
        this.levels = levels;
    }

    public void setLevelChangedListener(LevelStateChangedListener listener) {
        levelStateChangedListeners.add(listener);
        listener.onLevelStateChanged(new LevelStateChangedEvent(this));
    }

    private void notifyLevelStateChangedListeners() {
        for (LevelStateChangedListener listener : levelStateChangedListeners)
            listener.onLevelStateChanged(new LevelStateChangedEvent(this));
    }

    /**
     * Removes the current level from remaining levels. Then chooses a new level randomly. Notifies
     * listeners that the state has changed.
     *
     * @return returns the next level or null if no levels remain
     */
    public LineList nextLevel() {
        if (!remainingLevels.remove(currentLevel))
            return null;
        LineList nextLevel = chooseLevel();
        notifyLevelStateChangedListeners();
        return nextLevel;
    }

    /**
     * Sets up remaining levels to contain all indicies. Then randomly selects new level. Notifies
     * listeners that state has changed.
     *
     * @return
     */
    public LineList resetLevels() {
        initializeRemainingLevels();
        LineList nextLevel = chooseLevel();
        notifyLevelStateChangedListeners();
        return nextLevel;
    }

    /**
     * Randomly selects one of the indices from remaining levels
     *
     * @return
     */
    private LineList chooseLevel() {
        if (remainingLevels.isEmpty())
            return null;
        currentLevel = remainingLevels.get((int) (Math.random() * remainingLevels.size()));
        return levels.get(currentLevel);
    }

    private void initializeRemainingLevels() {
        remainingLevels.clear();
        remainingLevels.ensureCapacity(levels.size());
        for (int i = 0; i < levels.size(); i++)
            remainingLevels.add(i);
    }

    public LineList getLevel() {
        return levels.get(currentLevel.intValue());
    }

    public JSONObject toJson() {
        JSONObject jObj = new JSONObject();
        try {
            jObj.put(StateLoader.currentLevelKey, currentLevel);
            jObj.put(StateLoader.remainingLevelsKey, remainingLevels.toJson());
            return jObj;
        } catch (JSONException e) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
}
