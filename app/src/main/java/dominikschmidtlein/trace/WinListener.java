package dominikschmidtlein.trace;

import java.util.EventListener;

/**
 * Created by domin_2o9sb4z on 2016-02-02.
 */
public interface WinListener extends EventListener {

    void onWin(WinEvent winEvent);

}
