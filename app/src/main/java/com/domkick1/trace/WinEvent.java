package com.domkick1.trace;

import java.util.EventObject;

/**
 * Created by domin_2o9sb4z on 2016-02-02.
 */
public class WinEvent extends EventObject {

    /**
     * Constructs a new instance of this class.
     *
     * @param source the object which fired the event.
     */
    public WinEvent(Object source) {
        super(source);
    }
}
