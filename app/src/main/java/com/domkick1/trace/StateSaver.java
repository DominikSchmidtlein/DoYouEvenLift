package com.domkick1.trace;

import android.content.Context;
import android.util.Log;

/**
 * Created by domin_2o9sb4z on 2016-02-24.
 */
public class StateSaver implements LevelStateChangedListener {

    private InternalMemBoundary internalMem;
    private Context context;

    public StateSaver(Context context) {
        internalMem = new InternalMemBoundary(context);
        this.context = context;
    }

    @Override
    public void onLevelStateChanged(LevelStateChangedEvent event) {
        internalMem.write(context.getString(R.string.level_state_file),
                ((LevelState) event.getSource()).toString());
        Log.d("DOM", event.getSource().toString());
    }
}
