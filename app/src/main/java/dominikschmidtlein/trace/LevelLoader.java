package dominikschmidtlein.trace;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by domin_2o9sb4z on 2016-04-20.
 */
public class LevelLoader {

    public static final String levelsKey = "levels";

    private AssetsBoundary assets;
    private Context context;
    private ScreenDimensions dimensions;

    public LevelLoader(Context context, ScreenDimensions dimensions) {
        this.context = context;
        this.dimensions = dimensions;
        assets = new AssetsBoundary(context);
    }

    public LineList getLevel(int key) throws NoMoreLevelsException{
        try {
            if(key == -1)
                throw new NoMoreLevelsException();
            return new LineList(loadLevels().getJSONArray(key), dimensions);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getNumberOfLevels() {
        try {
            return loadLevels().length();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private JSONArray loadLevels() {
        try {
            String levels = assets.read(context.getString(R.string.levels_file));
            return new JSONObject(levels).getJSONArray(levelsKey);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
