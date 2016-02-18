package com.domkick1.trace;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by domin_2o9sb4z on 2016-01-31.
 */
public abstract class Trace extends Observable {

    protected android.graphics.Point size;
    protected int actionBarHeight;

    public Trace(Context context, android.graphics.Point screenSize, int actionBarHeight) {
        this.size = screenSize;
        this.actionBarHeight = actionBarHeight;
    }

    public boolean onTouch(View v, MotionEvent event) {
        boolean ret = handleTouch(v, event);
        setChanged();
        notifyObservers();
        return ret;
    }

    protected abstract boolean handleTouch(View v, MotionEvent event);

}
