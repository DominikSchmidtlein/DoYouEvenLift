package dominikschmidtlein.trace;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by domin_2o9sb4z on 2016-02-25.
 */
public class LevelState {

    private final JArrayList playedLevels;
    private final JArrayList remainingLevels;
    private Integer currentLevel;

    private final List<LevelStateChangedListener> levelStateChangedListeners = new ArrayList<>();

    public LevelState(int currentLevel, int totalLevels, JArrayList playedLevels) {
        this.currentLevel = currentLevel;
        this.playedLevels = playedLevels;
        remainingLevels = new JArrayList(totalLevels - playedLevels.size());
        for (int i = 0; i < totalLevels; i++)
            if (!playedLevels.contains(i))
                remainingLevels.add(i);
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
     * Removes the current level from remaining levels. Then chooses a new level. Notifies
     * listeners that the state has changed.
     *
     * @return returns the next level or null if no levels remain
     */
    public Integer nextLevel() {
        levelCompleted();
        chooseLevel();
        notifyLevelStateChangedListeners();
        return currentLevel;
    }

    private void chooseLevel() {
        currentLevel = remainingLevels.isEmpty() ? -1 : remainingLevels.get(0);
    }

    private void levelCompleted() {
        if (currentLevel < 0)
            return;
        remainingLevels.remove(currentLevel);
        playedLevels.add(currentLevel);
    }

    public Integer resetState(int numberOfLevels) throws NoMoreLevelsException {
        remainingLevels.initializeRemainingLevels(numberOfLevels);
        playedLevels.clear();
        currentLevel = null;
        nextLevel();
        notifyLevelStateChangedListeners();
        return currentLevel;
    }

    public JSONObject toJson() {
        JSONObject jObj = new JSONObject();
        try {
            jObj.put(StateLoader.currentLevelKey, currentLevel);
            jObj.put(StateLoader.playedLevels, playedLevels.toJson());
            return jObj;
        } catch (JSONException e) {
            throw new UnsupportedOperationException();
        }
    }

    public JArrayList getRemainingLevels() {
        return remainingLevels;
    }

    public Integer getCurrentLevel() {
        return currentLevel;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
}
