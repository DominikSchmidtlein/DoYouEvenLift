package dominikschmidtlein.trace.model;

/**
 * Created by domin_2o9sb4z on 2016-11-24.
 */
public class BuilderEngine implements Engine {

    /**
     * Check if touchPoint is near a point on the grid. If so, initiate line. If not, ignore touch.
     * @param touchPoint
     * @return
     */
    @Override
    public boolean down(TracePoint touchPoint) {
        return false;
    }

    /**
     * Check if touchPoint is near a point on the grid. If so, check to see if points are already
     * connected. If not, create new connection between points. Update current line either way.
     * @param touchPoint
     * @return
     */
    @Override
    public boolean move(TracePoint touchPoint) {
        return false;
    }

    /**
     * Remove current line.
     * @param touchPoint
     * @return
     */
    @Override
    public boolean up(TracePoint touchPoint) {
        return false;
    }

}
