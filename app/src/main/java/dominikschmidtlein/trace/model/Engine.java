package dominikschmidtlein.trace.model;

/**
 * Created by domin_2o9sb4z on 2016-11-24.
 */
public interface Engine {

    boolean down(TracePoint touchPoint);
    boolean move(TracePoint touchPoint);
    boolean up(TracePoint touchPoint);

}
