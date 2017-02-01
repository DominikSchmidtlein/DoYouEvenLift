package dominikschmidtlein.trace.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by domin_2o9sb4z on 2016-11-28.
 */
public class TraceTest {

    Trace trace;
    TracePointMap tracePointMap;

    TracePoint p12;
    TracePoint p21;
    TracePoint p32;
    TracePoint p43;
    TracePoint p52;
    TracePoint p61;
    TracePoint p72;

    Connection c12;
    Connection c13;
    Connection c23;
    Connection c34;
    Connection c35;
    Connection c45;
    Connection c56;
    Connection c57;
    Connection c67;

    Connection c15;
    Connection c24;
    Connection c37;
    Connection c46;

    Connection c17;

    @Before
    public void setUp() {
        tracePointMap = new TracePointMap(800, 400, 50);
        p12 = tracePointMap.addTracePoint(new Point(100, 200));
        p21 = tracePointMap.addTracePoint(new Point(200, 100));
        p32 = tracePointMap.addTracePoint(new Point(300, 200));
        p43 = tracePointMap.addTracePoint(new Point(400, 300));
        p52 = tracePointMap.addTracePoint(new Point(500, 200));
        p61 = tracePointMap.addTracePoint(new Point(600, 100));
        p72 = tracePointMap.addTracePoint(new Point(700, 200));

        // base
        c12 = new Connection(p12, p21);
        p12.addConnection(c12);
        p21.addConnection(c12);
        c13 = new Connection(p12, p32);
        p12.addConnection(c13);
        p32.addConnection(c13);
        c23 = new Connection(p21, p32);
        p21.addConnection(c23);
        p32.addConnection(c23);
        c34 = new Connection(p32, p43);
        p32.addConnection(c34);
        p43.addConnection(c34);
        c35 = new Connection(p32, p52);
        p32.addConnection(c35);
        p52.addConnection(c35);
        c45 = new Connection(p43, p52);
        p43.addConnection(c45);
        p52.addConnection(c45);
        c56 = new Connection(p52, p61);
        p52.addConnection(c56);
        p61.addConnection(c56);
        c57 = new Connection(p52, p72);
        p52.addConnection(c57);
        p72.addConnection(c57);
        c67 = new Connection(p61, p72);
        p61.addConnection(c67);
        p72.addConnection(c67);
        // level 2
        c15 = new Connection(p12, p52);
        p12.addConnection(c15);
        p52.addConnection(c15);
        c24 = new Connection(p21, p43);
        p21.addConnection(c24);
        p43.addConnection(c24);
        c37 = new Connection(p32, p72);
        p32.addConnection(c37);
        p72.addConnection(c37);
        c46 = new Connection(p43, p61);
        p43.addConnection(c46);
        p61.addConnection(c46);
        // level 3
        c17 = new Connection(p12, p72);
        p12.addConnection(c17);
        p72.addConnection(c17);

        c13.addSuperConnection(c15);
        c13.addSuperConnection(c17);
        c23.addSuperConnection(c24);
        c34.addSuperConnection(c24);
        c35.addSuperConnection(c15);
        c35.addSuperConnection(c37);
        c45.addSuperConnection(c46);
        c56.addSuperConnection(c46);
        c57.addSuperConnection(c37);
        c57.addSuperConnection(c17);
        c15.addSuperConnection(c17);
        c37.addSuperConnection(c17);

        trace = new Trace(tracePointMap);
    }

//    @Test
//    public void testAddConnectionP2P() {
//        assertNull(p12.connectedTo(p12));
//        assertNotNull(c12);
//        assertNotNull(c13);
//        assertNull(p12.connectedTo(p43));
//        assertNotNull(c15);
//        assertNull(p12.connectedTo(p61));
//        assertNotNull(c17);
//
//        assertNotNull(c21);
//        assertNull(p21.connectedTo(p21));
//        assertNotNull(c23);
//        assertNotNull(c24);
//        assertNull(p21.connectedTo(p52));
//        assertNull(p21.connectedTo(p61));
//        assertNull(p21.connectedTo(p72));
//
//        assertNotNull(c31);
//        assertNotNull(c32);
//        assertNull(p32.connectedTo(p32));
//        assertNotNull(c34);
//        assertNotNull(c35);
//        assertNull(p32.connectedTo(p61));
//        assertNotNull(c37);
//
//        assertNull(p43.connectedTo(p12));
//        assertNotNull(c42);
//        assertNotNull(c43);
//        assertNull(p43.connectedTo(p43));
//        assertNotNull(c45);
//        assertNotNull(c46);
//        assertNull(p43.connectedTo(p72));
//
//        assertNotNull(c51);
//        assertNull(p52.connectedTo(p21));
//        assertNotNull(c53);
//        assertNotNull(c54);
//        assertNull(p52.connectedTo(p52));
//        assertNotNull(c56);
//        assertNotNull(c57);
//
//        assertNull(p61.connectedTo(p12));
//        assertNull(p61.connectedTo(p21));
//        assertNull(p61.connectedTo(p32));
//        assertNotNull(c64);
//        assertNotNull(c65);
//        assertNull(p61.connectedTo(p61));
//        assertNotNull(c67);
//
//        assertNotNull(c71);
//        assertNull(p72.connectedTo(p21));
//        assertNotNull(c73);
//        assertNull(p72.connectedTo(p43));
//        assertNotNull(c75);
//        assertNotNull(c76);
//        assertNull(p72.connectedTo(p72));
//    }

//    @Test
//    public void testAddConnectionC2C() {
//        assertTrue(c12.getSubConnections().isEmpty());
//        assertTrue(c12.getSuperConnections().isEmpty());
//
//        assertTrue(c13.getSubConnections().isEmpty());
//        assertTrue(c13.getSuperConnections().contains(c15));
//        assertTrue(c13.getSuperConnections().contains(c17));
//        assertTrue(c13.getSuperConnections().size() == 2);
//
//        assertTrue(c23.getSubConnections().isEmpty());
//        assertTrue(c23.getSuperConnections().contains(c24));
//        assertTrue(c23.getSuperConnections().size() == 1);
//
//        assertTrue(c34.getSubConnections().isEmpty());
//        assertTrue(c34.getSuperConnections().contains(c24));
//        assertTrue(c34.getSuperConnections().size() == 1);
//
//        assertTrue(c35.getSubConnections().isEmpty());
//        assertTrue(c35.getSuperConnections().contains(c15));
//        assertTrue(c35.getSuperConnections().contains(c37));
//        assertTrue(c35.getSuperConnections().size() == 2);
//
//        assertTrue(c45.getSubConnections().isEmpty());
//        assertTrue(c45.getSuperConnections().contains(c46));
//        assertTrue(c45.getSuperConnections().size() == 1);
//
//        assertTrue(c56.getSubConnections().isEmpty());
//        assertTrue(c56.getSuperConnections().contains(c46));
//        assertTrue(c56.getSuperConnections().size() == 1);
//
//        assertTrue(c57.getSubConnections().isEmpty());
//        assertTrue(c57.getSuperConnections().contains(c17));
//        assertTrue(c57.getSuperConnections().contains(c37));
//        assertTrue(c57.getSuperConnections().size() == 2);
//
//        assertTrue(c67.getSubConnections().isEmpty());
//        assertTrue(c67.getSuperConnections().isEmpty());
//
//        assertTrue(c15.getSubConnections().contains(c13));
//        assertTrue(c15.getSubConnections().contains(c35));
//        assertTrue(c15.getSubConnections().size() == 2);
//        assertTrue(c15.getSuperConnections().contains(c17));
//        assertTrue(c15.getSuperConnections().size() == 1);
//
//        assertTrue(c24.getSubConnections().contains(c23));
//        assertTrue(c24.getSubConnections().contains(c34));
//        assertTrue(c24.getSubConnections().size() == 2);
//        assertTrue(c24.getSuperConnections().isEmpty());
//
//        assertTrue(c37.getSubConnections().contains(c35));
//        assertTrue(c37.getSubConnections().contains(c57));
//        assertTrue(c37.getSubConnections().size() == 2);
//        assertTrue(c37.getSuperConnections().contains(c17));
//        assertTrue(c37.getSuperConnections().size() == 1);
//
//        assertTrue(c46.getSubConnections().contains(c45));
//        assertTrue(c46.getSubConnections().contains(c56));
//        assertTrue(c46.getSubConnections().size() == 2);
//        assertTrue(c46.getSuperConnections().isEmpty());
//
//        assertTrue(c17.getSubConnections().contains(c13));
//        assertTrue(c17.getSubConnections().contains(c15));
//        assertTrue(c17.getSubConnections().contains(c37));
//        assertTrue(c17.getSubConnections().contains(c57));
//        assertTrue(c17.getSubConnections().size() == 4);
//        assertTrue(c17.getSuperConnections().isEmpty());
//    }

    @Test
    public void testNearPoint() {
        assertNotNull(trace.nearPoint(p12));
        assertNotNull(trace.nearPoint(p32));
        assertNotNull(trace.nearPoint(p72));

        assertNotNull(trace.nearPoint(new TracePoint(501, 201)));
        assertNotNull(trace.nearPoint(new TracePoint(601, 99)));
        assertNotNull(trace.nearPoint(new TracePoint(199, 101)));
        assertNotNull(trace.nearPoint(new TracePoint(399, 299)));

        assertNull(trace.nearPoint(new TracePoint(100, 100)));
        assertNull(trace.nearPoint(new TracePoint(300, 100)));
        assertNull(trace.nearPoint(new TracePoint(500, 10)));
        assertNull(trace.nearPoint(new TracePoint(700, 100)));
        assertNull(trace.nearPoint(new TracePoint(250, 201)));
        assertNull(trace.nearPoint(new TracePoint(450, 250)));
    }

    @Test
    public void testIsCompleteTrue() {
        c12.setOccupied();
        c24.setOccupied();
        c45.setOccupied();
        c56.setOccupied();
        c67.setOccupied();
        c17.setOccupied();

        assertTrue(trace.isComplete());
    }

    @Test
    public void testIsCompleteFalse() {
        assertFalse(trace.isComplete());
        c24.setOccupied();
        c45.setOccupied();
        c56.setOccupied();
        c67.setOccupied();
        c17.setOccupied();
        assertFalse(trace.isComplete());
    }

    @Test
    public void testReset() {
        c34.setOccupied();
        c45.setOccupied();
        c57.setOccupied();
        c67.setOccupied();
        c56.setOccupied();
        c15.setOccupied();
        c12.setOccupied();
        c23.setOccupied();

        assertTrue(trace.isComplete());
        trace.reset();
        assertFalse(trace.isComplete());
        assertTrue(c12.isFree());
        assertTrue(c13.isFree());
        assertTrue(c23.isFree());
        assertTrue(c34.isFree());
        assertTrue(c35.isFree());
        assertTrue(c45.isFree());
        assertTrue(c56.isFree());
        assertTrue(c57.isFree());
        assertTrue(c67.isFree());
        assertTrue(c15.isFree());
        assertTrue(c24.isFree());
        assertTrue(c37.isFree());
        assertTrue(c46.isFree());
        assertTrue(c17.isFree());
    }

    @Test
    public void testIsLegal() {
        assertTrue(trace.isLegal());
    }

    @Test
    public void testIsLegalFalse() {
        TracePointMap tracePointMap = new TracePointMap(800, 400, 50);
        TracePoint p12 = tracePointMap.addTracePoint(new Point(100, 200));
        TracePoint p21 = tracePointMap.addTracePoint(new Point(200, 100));
        TracePoint p32 = tracePointMap.addTracePoint(new Point(300, 200));
        TracePoint p43 = tracePointMap.addTracePoint(new Point(400, 300));

        Connection c12 = new Connection(p12, p21);
        p12.addConnection(c12);
        p21.addConnection(c12);
        Connection c13 = new Connection(p12, p32);
        p12.addConnection(c13);
        p32.addConnection(c13);
        Connection c14 = new Connection(p12, p43);
        p12.addConnection(c14);
        p43.addConnection(c14);



        Trace testTrace = new Trace(tracePointMap);
        assertFalse(testTrace.isLegal());
    }

    @Test
    public void testIsLegalSplit() {
        TracePointMap tracePointMap = new TracePointMap(800, 400, 50);
        TracePoint p12 = tracePointMap.addTracePoint(new Point(100, 200));
        TracePoint p21 = tracePointMap.addTracePoint(new Point(200, 100));
        TracePoint p32 = tracePointMap.addTracePoint(new Point(300, 200));
        TracePoint p52 = tracePointMap.addTracePoint(new Point(500, 200));
        TracePoint p61 = tracePointMap.addTracePoint(new Point(600, 100));
        TracePoint p72 = tracePointMap.addTracePoint(new Point(700, 200));

        // base
        Connection c12 = new Connection(p12, p21);
        p12.addConnection(c12);
        p21.addConnection(c12);
        Connection c13 = new Connection(p12, p32);
        p12.addConnection(c13);
        p32.addConnection(c13);
        Connection c23 = new Connection(p21, p32);
        p21.addConnection(c23);
        p32.addConnection(c23);
        Connection c56 = new Connection(p52, p61);
        p52.addConnection(c56);
        p61.addConnection(c56);
        Connection c57 = new Connection(p52, p72);
        p52.addConnection(c57);
        p72.addConnection(c57);
        Connection c67 = new Connection(p61, p72);
        p61.addConnection(c67);
        p72.addConnection(c67);

        Trace testTrace = new Trace(tracePointMap);
        assertFalse(testTrace.isLegal());
    }
}