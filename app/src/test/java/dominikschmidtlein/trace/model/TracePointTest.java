package dominikschmidtlein.trace.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by domin_2o9sb4z on 2016-12-04.
 */
public class TracePointTest {

    TracePoint point1;
    TracePoint point2;
    Connection connection;

    @Before
    public void setUp() {
        point1 = new TracePoint(100, 100);
        point2 = new TracePoint(200, 100);
        connection = new Connection(point1, point2);
    }

    @Test
    public void testConnectedTo() {
        assertNotNull(point1.connectedTo(point2));
        assertNotNull(point2.connectedTo(point1));
    }

    @Test
    public void testEquals() {
        assertNotEquals(point1, point2);
        assertEquals(point1, new TracePoint(100, 200));
        assertEquals(point1, point1);
    }

}