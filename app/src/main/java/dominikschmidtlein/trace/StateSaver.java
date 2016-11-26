package dominikschmidtlein.trace;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by domin_2o9sb4z on 2016-02-24.
 */
public class StateSaver implements LevelStateChangedListener {

    private final InternalMemBoundary internalMem;
    private final Context context;

    public StateSaver(Context context) {
        internalMem = new InternalMemBoundary(context);
        this.context = context;
    }

    @Override
    public void onLevelStateChanged(LevelStateChangedEvent event) {
        try {
            internalMem.write(context.getString(R.string.level_state_file), event.getSource().toString());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "state save unsuccessful", Toast.LENGTH_LONG).show();
        }
    }
}
