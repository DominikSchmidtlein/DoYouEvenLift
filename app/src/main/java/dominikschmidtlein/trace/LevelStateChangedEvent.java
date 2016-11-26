package dominikschmidtlein.trace;

import java.util.EventObject;

/**
 * Created by domin_2o9sb4z on 2016-03-17.
 */
public class LevelStateChangedEvent extends EventObject {

    /**
     * Constructs a new instance of this class.
     *
     * @param source the object which fired the event.
     */
    public LevelStateChangedEvent(Object source) {
        super(source);
    }

}
