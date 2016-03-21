package com.domkick1.trace;

import java.util.EventListener;

/**
 * Created by domin_2o9sb4z on 2016-03-17.
 */
public interface LevelStateChangedListener extends EventListener {

    void onLevelStateChanged(LevelStateChangedEvent event);

}
