package dominikschmidtlein.trace;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by domin_2o9sb4z on 2016-03-15.
 */
public class AssetsBoundary {

    private final Context context;

    public AssetsBoundary(Context context) {
        this.context = context;
    }

    public String read(String filename) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream file = assetManager.open(filename);
            byte[] data = new byte[file.available()];
            file.read(data);
            file.close();
            return new String(data);
        } catch (FileNotFoundException ee) {
            ee.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
