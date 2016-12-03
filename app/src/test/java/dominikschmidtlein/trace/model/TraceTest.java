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

    Connection c12;
    Connection c21;
    Connection c13;
    Connection c31;
    Connection c23;
    Connection c32;
    Connection c34;
    Connection c43;
    Connection c35;
    Connection c53;
    Connection c45;
    Connection c54;
    Connection c56;
    Connection c65;
    Connection c57;
    Connection c75;
    Connection c67;
    Connection c76;

    Connection c15;
    Connection c51;
    Connection c24;
    Connection c42;
    Connection c37;
    Connection c73;
    Connection c46;
    Connection c64;

    Connection c17;
    Connection c71;

    @Before
    public void setUp() {
        trace = new Trace();
        p12 = new TracePoint(100, 200);
        p21 = new TracePoint(200, 100);
        p32 = new TracePoint(300, 200);
        p43 = new TracePoint(400, 300);
        p52 = new TracePoint(500, 200);
        p61 = new TracePoint(600, 100);
        p72 = new TracePoint(700, 200);
        trace.addConnection(p12, p21);
        trace.addConnection(p12, p72);
        trace.addConnection(p21, p43);
        trace.addConnection(p61, p43);
        trace.addConnection(p61, p72);

        p12 = trace.nearPoint(p12);
        p21 = trace.nearPoint(p21);
        p32 = trace.nearPoint(p32);
        p43 = trace.nearPoint(p43);
        p52 = trace.nearPoint(p52);
        p61 = trace.nearPoint(p61);
        p72 = trace.nearPoint(p72);

        // base
        c12 = p12.connectedTo(p21);
        c21 = p21.connectedTo(p12);

        c13 = p12.connectedTo(p32);
        c31 = p32.connectedTo(p12);

        c23 = p21.connectedTo(p32);
        c32 = p32.connectedTo(p21);

        c34 = p32.connectedTo(p43);
        c43 = p43.connectedTo(p32);

        c35 = p32.connectedTo(p52);
        c53 = p52.connectedTo(p32);

        c45 = p43.connectedTo(p52);
        c54 = p52.connectedTo(p43);

        c56 = p52.connectedTo(p61);
        c65 = p61.connectedTo(p52);

        c57 = p52.connectedTo(p72);
        c75 = p72.connectedTo(p52);

        c67 = p61.connectedTo(p72);
        c76 = p72.connectedTo(p61);

        // level 2
        c15 = p12.connectedTo(p52);
        c51 = p52.connectedTo(p12);

        c24 = p21.connectedTo(p43);
        c42 = p43.connectedTo(p21);

        c37 = p32.connectedTo(p72);
        c73 = p72.connectedTo(p32);

        c46 = p43.connectedTo(p61);
        c64 = p61.connectedTo(p43);

        // level 3
        c17 = p12.connectedTo(p72);
        c71 = p72.connectedTo(p12);
    }

    @Test
    public void testAddConnectionP2P() {
        assertNull(p12.connectedTo(p12));
        assertNotNull(c12);
        assertNotNull(c13);
        assertNull(p12.connectedTo(p43));
        assertNotNull(c15);
        assertNull(p12.connectedTo(p61));
        assertNotNull(c17);

        assertNotNull(c21);
        assertNull(p21.connectedTo(p21));
        assertNotNull(c23);
        assertNotNull(c24);
        assertNull(p21.connectedTo(p52));
        assertNull(p21.connectedTo(p61));
        assertNull(p21.connectedTo(p72));

        assertNotNull(c31);
        assertNotNull(c32);
        assertNull(p32.connectedTo(p32));
        assertNotNull(c34);
        assertNotNull(c35);
        assertNull(p32.connectedTo(p61));
        assertNotNull(c37);

        assertNull(p43.connectedTo(p12));
        assertNotNull(c42);
        assertNotNull(c43);
        assertNull(p43.connectedTo(p43));
        assertNotNull(c45);
        assertNotNull(c46);
        assertNull(p43.connectedTo(p72));

        assertNotNull(c51);
        assertNull(p52.connectedTo(p21));
        assertNotNull(c53);
        assertNotNull(c54);
        assertNull(p52.connectedTo(p52));
        assertNotNull(c56);
        assertNotNull(c57);

        assertNull(p61.connectedTo(p12));
        assertNull(p61.connectedTo(p21));
        assertNull(p61.connectedTo(p32));
        assertNotNull(c64);
        assertNotNull(c65);
        assertNull(p61.connectedTo(p61));
        assertNotNull(c67);

        assertNotNull(c71);
        assertNull(p72.connectedTo(p21));
        assertNotNull(c73);
        assertNull(p72.connectedTo(p43));
        assertNotNull(c75);
        assertNotNull(c76);
        assertNull(p72.connectedTo(p72));
    }

    @Test
    public void testAddConnectionC2C() {
        assertTrue(c12.getSubConnections().isEmpty());
        assertTrue(c12.getSuperConnections().isEmpty());

        assertTrue(c13.getSubConnections().isEmpty());
        assertTrue(c13.getSuperConnections().contains(c15));
        assertTrue(c13.getSuperConnections().size() == 1);

        assertTrue(c23.getSubConnections().isEmpty());
        assertTrue(c23.getSuperConnections().contains(c24));
        assertTrue(c23.getSuperConnections().size() == 1);

        assertTrue(c34.getSubConnections().isEmpty());
        assertTrue(c34.getSuperConnections().contains(c24));
        assertTrue(c34.getSuperConnections().size() == 1);

        assertTrue(c35.getSubConnections().isEmpty());
        assertTrue(c35.getSuperConnections().contains(c15));
        assertTrue(c35.getSuperConnections().contains(c37));
        assertTrue(c35.getSuperConnections().size() == 2);

        assertTrue(c45.getSubConnections().isEmpty());
        assertTrue(c45.getSuperConnections().contains(c46));
        assertTrue(c45.getSuperConnections().size() == 1);

        assertTrue(c56.getSubConnections().isEmpty());
        assertTrue(c56.getSuperConnections().contains(c46));
        assertTrue(c56.getSuperConnections().size() == 1);

        assertTrue(c57.getSubConnections().isEmpty());
        assertTrue(c57.getSuperConnections().contains(c37));
        assertTrue(c57.getSuperConnections().size() == 1);

        assertTrue(c67.getSubConnections().isEmpty());
        assertTrue(c67.getSuperConnections().isEmpty());

        assertTrue(c15.getSubConnections().contains(c13));
        assertTrue(c15.getSubConnections().contains(c35));
        assertTrue(c15.getSubConnections().size() == 2);
        assertTrue(c15.getSuperConnections().contains(c17));
        assertTrue(c15.getSuperConnections().size() == 1);

        assertTrue(c24.getSubConnections().contains(c23));
        assertTrue(c24.getSubConnections().contains(c34));
        assertTrue(c24.getSubConnections().size() == 2);
        assertTrue(c24.getSuperConnections().isEmpty());

        assertTrue(c37.getSubConnections().contains(c35));
        assertTrue(c37.getSubConnections().contains(c57));
        assertTrue(c37.getSubConnections().size() == 2);
        assertTrue(c37.getSuperConnections().contains(c17));
        assertTrue(c37.getSuperConnections().size() == 1);

        assertTrue(c46.getSubConnections().contains(c45));
        assertTrue(c46.getSubConnections().contains(c56));
        assertTrue(c46.getSubConnections().size() == 2);
        assertTrue(c46.getSuperConnections().isEmpty());

        assertTrue(c17.getSubConnections().contains(c15));
        assertTrue(c17.getSubConnections().contains(c37));
        assertTrue(c17.getSubConnections().size() == 2);
        assertTrue(c17.getSuperConnections().isEmpty());
    }

    @Test
    public void testNearPoint() {
        assertNotNull(trace.nearPoint(p12));
        assertNotNull(trace.nearPoint(p32));
        assertNotNull(trace.nearPoint(p72));

        assertNotNull(trace.nearPoint(new TracePoint(501, 201)));
        assertNotNull(trace.nearPoint(new TracePoint(601, 99)));
        assertNotNull(trace.nearPoint(new TracePoint(199, 101)));
        assertNotNull(trace.nearPoint(new TracePoint(399, 299)));

        assertNull(trace.nearPoint(new TracePoint(200, 150)));
        assertNull(trace.nearPoint(new TracePoint(400, 250)));
        assertNull(trace.nearPoint(new TracePoint(600, 150)));
        assertNull(trace.nearPoint(new TracePoint(150, 150)));
        assertNull(trace.nearPoint(new TracePoint(400, 200)));
        assertNull(trace.nearPoint(new TracePoint(550, 150)));
    }

    @Test
    public void testIsComplete() throws Exception {
        c12.setOccupied();
        c24.setOccupied();
        c45.setOccupied();
        c56.setOccupied();
        c67.setOccupied();
        c71.setOccupied();

        assertTrue(trace.isComplete());
    }

    @Test
    public void testReset() throws Exception {

    }

    @Test
    public void testIsLegal() {
        assertTrue(trace.isLegal());

        // 4 points with odd # connections
        Trace trace1 = new Trace();
        trace1.addConnection(p12, p21);
        trace1.addConnection(p12, p32);
        trace1.addConnection(p12, p43);
        assertFalse(trace1.isLegal());

        // 2 separate shapes
        Trace trace2 = new Trace();
        trace2.addConnection(p12, p21);
        trace2.addConnection(p12, p32);
        trace2.addConnection(p32, p21);
        trace2.addConnection(p52, p61);
        trace2.addConnection(p52, p72);
        trace2.addConnection(p72, p61);
        assertFalse(trace2.isLegal());

    }
}