package dominikschmidtlein.trace;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by domin_2o9sb4z on 2016-03-19.
 */
public class StateLoader {

    public static final String currentLevelKey = "currentlevel";
    public static final String playedLevels = "playedlevels";

    private final Context context;
    private final InternalMemBoundary internalMemBoundary;

    public StateLoader(Context context) {
        this.context = context;
        internalMemBoundary = new InternalMemBoundary(context);
    }

    public LevelState loadState(int totalLevels) {
        try {
            // load state
            String stateString = internalMemBoundary.read(context.getString(R.string.level_state_file));

            // parse current level and played levels
            Integer currentLevel = new JSONObject(stateString).getInt(currentLevelKey);
            JArrayList playedLevels = stringToList(stateString);

            return new LevelState(currentLevel, totalLevels, playedLevels);
        } catch (FileNotFoundException fnf) {
            // first time app is opened
            return new LevelState(0, totalLevels, new JArrayList(totalLevels));
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    private JArrayList stringToList(String state) {
        try {
            JSONArray jArray = new JSONObject(state).getJSONArray(StateLoader.playedLevels);
            JArrayList list = new JArrayList(jArray.length());
            for (int i = 0; i < jArray.length(); i++)
                list.add(jArray.getInt(i));
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

}
