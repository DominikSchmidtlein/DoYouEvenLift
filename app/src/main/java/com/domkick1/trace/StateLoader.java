package com.domkick1.trace;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by domin_2o9sb4z on 2016-03-19.
 */
public class StateLoader {

    public static final String totalLevelsKey = "totallevels";
    public static final String currentLevelKey = "currentlevel";
    public static final String remainingLevelsKey = "remaininglevels";
    public static final String levelsKey = "levels";

    private final AssetsBoundary assetsBoundary;
    private final Context context;
    private final InternalMemBoundary internalMemBoundary;
    private final ScreenDimensions dimensions;

    public StateLoader(Context context, ScreenDimensions dimensions) {
        this.context = context;
        this.dimensions = dimensions;
        internalMemBoundary = new InternalMemBoundary(context);
        assetsBoundary = new AssetsBoundary(context);
    }

    public LevelState loadState() {
        try {
            // load levels
            String levelsString = assetsBoundary.read(context.getString(R.string.levels_file));
            LevelList levelList = new LevelList(new JSONObject(levelsString), dimensions);

            // load state
            String stateString = internalMemBoundary.read(context.getString(R.string.level_state_file));

            if (stateString == null)
                return new LevelState(levelList);

            Integer totalLevels = new JSONObject(stateString).getInt(totalLevelsKey);
            Integer currentLevel = new JSONObject(stateString).getInt(currentLevelKey);
            RemainingLevels remainingLevels = new RemainingLevels(new JSONObject(stateString));

            return new LevelState(currentLevel, totalLevels, remainingLevels, levelList);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
