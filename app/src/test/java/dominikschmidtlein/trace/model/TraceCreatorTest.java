package dominikschmidtlein.trace.model;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by domin_2o9sb4z on 2017-01-09.
 */
public class TraceCreatorTest {
    TraceCreator traceCreator;
    TracePoint p1;
    TracePoint p2;
    TracePoint p3;
    TracePoint p4;
    TracePoint p5;
    Connection c12;
    Connection c13;
    Connection c14;
    Connection c15;
    Connection c23;
    Connection c24;
    Connection c25;
    Connection c34;
    Connection c35;
    Connection c45;

    @Before
    public void setUp() {
        p1 = new TracePoint(100, 100);
        p2 = new TracePoint(100, 200);
        p3 = new TracePoint(200, 100);
        p4 = new TracePoint(200, 200);
        p5 = new TracePoint(150, 150);

        c12 = new Connection(p1, p2);
        c13 = new Connection(p1, p3);
        c14 = new Connection(p1, p4);
        c15 = new Connection(p1, p5);
        c23 = new Connection(p2, p3);
        c24 = new Connection(p2, p4);
        c25 = new Connection(p2, p5);
        c34 = new Connection(p3, p4);
        c35 = new Connection(p3, p5);
        c45 = new Connection(p4, p5);

        traceCreator = new TraceCreator(300, 300, 20);
        traceCreator.addConnection(c12);
        traceCreator.addConnection(c13);
        traceCreator.addConnection(c24);
        traceCreator.addConnection(c34);
    }

    @Test
    public void testAddConnectionSuper() {
        traceCreator.addConnection(c15);
        traceCreator.addConnection(c25);
        traceCreator.addConnection(c35);
        traceCreator.addConnection(c45);

        checkTrace();
    }

    @Test
    public void testAddConnectionIntersectPointPThenC() {
        traceCreator.addConnection(c25);
        traceCreator.addConnection(c14);
        traceCreator.addConnection(c35);

        checkTrace();
    }

    @Test
    public void testAddConnectionIntersectPointCThenP() {
        traceCreator.addConnection(c14);
        traceCreator.addConnection(c25);
        traceCreator.addConnection(c35);

        checkTrace();
    }

    @Test
    public void testAddConnectionIntersectConnection() {
        traceCreator.addConnection(c14);
        traceCreator.addConnection(c23);

        checkTrace();
    }

    public void checkTrace() {
        assertEquals(p1.connectedTo(p2), c12);
        assertEquals(p1.connectedTo(p3), c13);
        assertEquals(p1.connectedTo(p4), c14);
        assertEquals(p1.connectedTo(p5), c15);
        assertEquals(p2.connectedTo(p3), c23);
        assertEquals(p2.connectedTo(p4), c24);
        assertEquals(p2.connectedTo(p5), c25);
        assertEquals(p3.connectedTo(p4), c34);
        assertEquals(p3.connectedTo(p5), c35);
        assertEquals(p4.connectedTo(p5), c45);

        assertEquals(0, c12.getSubConnectionCount());
        assertEquals(0, c12.getSuperConnectionCount());

        assertEquals(0, c13.getSubConnectionCount());
        assertEquals(0, c13.getSuperConnectionCount());

        assertEquals(2, c14.getSubConnectionCount());
        assertTrue(c14.getSuperConnections().contains(c15));
        assertTrue(c14.getSuperConnections().contains(c45));
        assertEquals(0, c14.getSuperConnectionCount());

        assertEquals(0, c15.getSubConnectionCount());
        assertEquals(1, c15.getSuperConnectionCount());
        assertTrue(c15.getSuperConnections().contains(c14));

        assertEquals(2, c23.getSubConnectionCount());
        assertTrue(c23.getSuperConnections().contains(c25));
        assertTrue(c23.getSuperConnections().contains(c35));
        assertEquals(0, c23.getSuperConnectionCount());

        assertEquals(0, c24.getSubConnectionCount());
        assertEquals(0, c24.getSuperConnectionCount());

        assertEquals(0, c25.getSubConnectionCount());
        assertEquals(1, c25.getSuperConnectionCount());
        assertTrue(c25.getSuperConnections().contains(c23));

        assertEquals(0, c34.getSubConnectionCount());
        assertEquals(0, c34.getSuperConnectionCount());

        assertEquals(0, c35.getSubConnectionCount());
        assertEquals(1, c35.getSuperConnectionCount());
        assertTrue(c35.getSuperConnections().contains(c23));

        assertEquals(0, c45.getSubConnectionCount());
        assertEquals(1, c45.getSuperConnectionCount());
        assertTrue(c45.getSuperConnections().contains(c14));
    }

    @Test
    public void testRemoveConnection() {
        traceCreator.addConnection(c14);

        traceCreator.removeConnection(c14);

        traceCreator.addConnection(c23);

        checkTraceRemove();
    }

    @Test
    public void testRemoveConnectionSuper() {
        traceCreator.addConnection(c15);
        traceCreator.addConnection(c25);
        traceCreator.addConnection(c35);
        traceCreator.addConnection(c45);

        traceCreator.removeConnection(c14);

        checkTraceRemove();
    }

    @Test
    public void testRemoveConnectionSub() {
        traceCreator.addConnection(c15);
        traceCreator.addConnection(c25);
        traceCreator.addConnection(c35);
        traceCreator.addConnection(c45);

        traceCreator.removeConnection(c15);
        traceCreator.removeConnection(c45);

        checkTraceRemove();
    }

    public void checkTraceRemove() {
        assertEquals(p1.connectedTo(p2), c12);
        assertEquals(p1.connectedTo(p3), c13);
        assertNull(p1.connectedTo(p4));
        assertNull(p1.connectedTo(p5));
        assertEquals(p2.connectedTo(p3), c23);
        assertEquals(p2.connectedTo(p4), c24);
        assertNull(p2.connectedTo(p5));
        assertEquals(p3.connectedTo(p4), c34);
        assertNull(p3.connectedTo(p5));
        assertNull(p4.connectedTo(p5));

        assertEquals(0, c12.getSubConnectionCount());
        assertEquals(0, c12.getSuperConnectionCount());

        assertEquals(0, c13.getSubConnectionCount());
        assertEquals(0, c13.getSuperConnectionCount());

        assertEquals(0, c14.getSubConnectionCount());
//        assertTrue(c14.getSuperConnections().contains(c15));
//        assertTrue(c14.getSuperConnections().contains(c45));
        assertEquals(0, c14.getSuperConnectionCount());

        assertEquals(0, c15.getSubConnectionCount());
        assertEquals(0, c15.getSuperConnectionCount());
//        assertTrue(c15.getSuperConnections().contains(c14));

        assertEquals(0, c23.getSubConnectionCount());
//        assertTrue(c23.getSuperConnections().contains(c25));
//        assertTrue(c23.getSuperConnections().contains(c35));
        assertEquals(0, c23.getSuperConnectionCount());

        assertEquals(0, c24.getSubConnectionCount());
        assertEquals(0, c24.getSuperConnectionCount());

        assertEquals(0, c25.getSubConnectionCount());
        assertEquals(0, c25.getSuperConnectionCount());
//        assertTrue(c25.getSuperConnections().contains(c23));

        assertEquals(0, c34.getSubConnectionCount());
        assertEquals(0, c34.getSuperConnectionCount());

        assertEquals(0, c35.getSubConnectionCount());
        assertEquals(0, c35.getSuperConnectionCount());
//        assertTrue(c35.getSuperConnections().contains(c23));

        assertEquals(0, c45.getSubConnectionCount());
        assertEquals(0, c45.getSuperConnectionCount());
//        assertTrue(c45.getSuperConnections().contains(c14));
    }
}