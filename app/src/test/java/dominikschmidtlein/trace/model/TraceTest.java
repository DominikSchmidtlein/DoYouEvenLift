package dominikschmidtlein.trace.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by domin_2o9sb4z on 2016-11-28.
 */
public class TraceTest {

    Trace trace;
    TracePoint p12;
    TracePoint p21;
    TracePoint p32;
    TracePoint p43;
    TracePoint p52;
    TracePoint p61;
    TracePoint p72;

    @Before
    public void setUp() throws Exception {
        trace = new Trace();
        p12 = new TracePoint(100, 200);
        p21 = new TracePoint(200, 100);
        p32 = new TracePoint(300, 200);
        p43 = new TracePoint(400, 300);
        p52 = new TracePoint(500, 200);
        p61 = new TracePoint(600, 100);
        p72 = new TracePoint(700, 200);
        trace.addPoint(p12);
        trace.addPoint(p21);
        trace.addPoint(p43);
        trace.addPoint(p61);
        trace.addPoint(p72);
        trace.addConnection(p12, p21);
        trace.addConnection(p12, p72);
        trace.addConnection(p21, p43);
        trace.addConnection(p61, p43);
        trace.addConnection(p61, p72);
    }

    @Test
    public void testAddPoint() throws Exception {

    }

    @Test
    public void testAddConnection() {
        p32 = trace.nearPoint(p32);
        p52 = trace.nearPoint(p52);

        assertNotNull(p32);
        assertNotNull(p52);

        assertNull(p12.connectedTo(p12));
        assertNotNull(p12.connectedTo(p21));
        assertNotNull(p12.connectedTo(p32));
        assertNull(p12.connectedTo(p43));
        assertNotNull(p12.connectedTo(p52));
        assertNull(p12.connectedTo(p61));
        assertNotNull(p12.connectedTo(p72));

        assertNotNull(p21.connectedTo(p12));
        assertNull(p21.connectedTo(p21));
        assertNotNull(p21.connectedTo(p32));
        assertNotNull(p21.connectedTo(p43));
        assertNull(p21.connectedTo(p52));
        assertNull(p21.connectedTo(p61));
        assertNull(p21.connectedTo(p72));

        assertNotNull(p32.connectedTo(p12));
        assertNotNull(p32.connectedTo(p21));
        assertNull(p32.connectedTo(p32));
        assertNotNull(p32.connectedTo(p43));
        assertNotNull(p32.connectedTo(p52));
        assertNull(p32.connectedTo(p61));
        assertNotNull(p32.connectedTo(p72));

        assertNull(p43.connectedTo(p12));
        assertNotNull(p43.connectedTo(p21));
        assertNotNull(p43.connectedTo(p32));
        assertNull(p43.connectedTo(p43));
        assertNotNull(p43.connectedTo(p52));
        assertNotNull(p43.connectedTo(p61));
        assertNull(p43.connectedTo(p72));

        assertNotNull(p52.connectedTo(p12));
        assertNull(p52.connectedTo(p21));
        assertNotNull(p52.connectedTo(p32));
        assertNotNull(p52.connectedTo(p43));
        assertNull(p52.connectedTo(p52));
        assertNotNull(p52.connectedTo(p61));
        assertNotNull(p52.connectedTo(p72));

        assertNull(p61.connectedTo(p12));
        assertNull(p61.connectedTo(p21));
        assertNull(p61.connectedTo(p32));
        assertNotNull(p61.connectedTo(p43));
        assertNotNull(p61.connectedTo(p52));
        assertNull(p61.connectedTo(p61));
        assertNotNull(p61.connectedTo(p72));

        assertNotNull(p72.connectedTo(p12));
        assertNull(p72.connectedTo(p21));
        assertNotNull(p72.connectedTo(p32));
        assertNull(p72.connectedTo(p43));
        assertNotNull(p72.connectedTo(p52));
        assertNotNull(p72.connectedTo(p61));
        assertNull(p72.connectedTo(p72));
    }

    @Test
    public void testSuperConnections() {
        
    }

    @Test
    public void testNearPoint() throws Exception {

    }

    @Test
    public void testIsComplete() throws Exception {

    }

    @Test
    public void testReset() throws Exception {

    }
}