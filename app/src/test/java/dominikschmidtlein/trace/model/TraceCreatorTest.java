package dominikschmidtlein.trace.model;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by domin_2o9sb4z on 2017-01-09.
 */
public class TraceCreatorTest {
    TraceCreator traceCreator;
    Trace trace;
    Point p1;
    Point p2;
    Point p3;
    Point p4;
    Point p5;

    @Before
    public void setUp() {
        p1 = new Point(100, 100);
        p2 = new Point(100, 200);
        p3 = new Point(200, 100);
        p4 = new Point(200, 200);
        p5 = new Point(150, 150);

        traceCreator = new TraceCreator(300, 300, 20);
        trace = traceCreator.getTrace();
        traceCreator.createConnection(p1, p2);
        traceCreator.createConnection(p1, p3);
        traceCreator.createConnection(p2, p4);
        traceCreator.createConnection(p3, p4);
    }

    @Test
    public void testAddConnectionSuper() {
        traceCreator.createConnection(p1, p5);
        traceCreator.createConnection(p2, p5);
        traceCreator.createConnection(p3, p5);
        traceCreator.createConnection(p4, p5);

        checkTrace();
    }

    @Test
    public void testAddConnectionIntersectPointPThenC() {
        traceCreator.createConnection(p2, p5);
        traceCreator.createConnection(p1, p4);
        traceCreator.createConnection(p3, p5);

        checkTrace();
    }

    @Test
    public void testAddConnectionIntersectPointCThenP() {
        traceCreator.createConnection(p1, p4);
        traceCreator.createConnection(p2, p5);
        traceCreator.createConnection(p3, p5);

        checkTrace();
    }

    @Test
    public void testAddConnectionIntersectConnection() {
        traceCreator.createConnection(p1, p4);
        traceCreator.createConnection(p2, p3);

        checkTrace();
    }

    public void checkTrace() {
        TracePoint tp1 = trace.nearPoint(p1);
        TracePoint tp2 = trace.nearPoint(p2);
        TracePoint tp3 = trace.nearPoint(p3);
        TracePoint tp4 = trace.nearPoint(p4);
        TracePoint tp5 = trace.nearPoint(p5);

        Connection c12 = tp1.connectedTo(tp2);
        Connection c13 = tp1.connectedTo(tp3);
        Connection c14 = tp1.connectedTo(tp4);
        Connection c15 = tp1.connectedTo(tp5);
        Connection c23 = tp2.connectedTo(tp3);
        Connection c24 = tp2.connectedTo(tp4);
        Connection c25 = tp2.connectedTo(tp5);
        Connection c34 = tp3.connectedTo(tp4);
        Connection c35 = tp3.connectedTo(tp5);
        Connection c45 = tp4.connectedTo(tp5);

        assertNotNull(c12);
        assertNotNull(c13);
        assertNotNull(c14);
        assertNotNull(c15);
        assertNotNull(c23);
        assertNotNull(c24);
        assertNotNull(c25);
        assertNotNull(c34);
        assertNotNull(c35);
        assertNotNull(c45);

        assertEquals(0, c12.getSubConnectionCount());
        assertEquals(0, c12.getSuperConnectionCount());

        assertEquals(0, c13.getSubConnectionCount());
        assertEquals(0, c13.getSuperConnectionCount());

        assertEquals(2, c14.getSubConnectionCount());
        assertTrue(c14.getSubConnections().contains(c15));
        assertTrue(c14.getSubConnections().contains(c45));
        assertEquals(0, c14.getSuperConnectionCount());

        assertEquals(0, c15.getSubConnectionCount());
        assertEquals(1, c15.getSuperConnectionCount());
        assertTrue(c15.getSuperConnections().contains(c14));

        assertEquals(2, c23.getSubConnectionCount());
        assertTrue(c23.getSubConnections().contains(c25));
        assertTrue(c23.getSubConnections().contains(c35));
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
        traceCreator.createConnection(p1, p4);

        traceCreator.removeConnection(p1, p4);

        traceCreator.createConnection(p2, p3);

        checkTraceRemove();
    }

    @Test
    public void testRemoveConnectionSuper() {
        traceCreator.createConnection(p1, p5);
        traceCreator.createConnection(p2, p5);
        traceCreator.createConnection(p3, p5);
        traceCreator.createConnection(p4, p5);

        traceCreator.removeConnection(p1, p4);

        checkTraceRemove();
    }

    @Test
    public void testRemoveConnectionSub() {
        traceCreator.createConnection(p1, p5);
        traceCreator.createConnection(p2, p5);
        traceCreator.createConnection(p3, p5);
        traceCreator.createConnection(p4, p5);

        traceCreator.removeConnection(p1, p5);
        traceCreator.removeConnection(p4, p5);

        checkTraceRemove();
    }

    public void checkTraceRemove() {
        TracePoint tp1 = trace.nearPoint(p1);
        TracePoint tp2 = trace.nearPoint(p2);
        TracePoint tp3 = trace.nearPoint(p3);
        TracePoint tp4 = trace.nearPoint(p4);
        TracePoint tp5 = trace.nearPoint(p5);

        assertNull(tp5);

        Connection c12 = tp1.connectedTo(tp2);
        Connection c13 = tp1.connectedTo(tp3);
        Connection c14 = tp1.connectedTo(tp4);
        Connection c23 = tp2.connectedTo(tp3);
        Connection c24 = tp2.connectedTo(tp4);
        Connection c34 = tp3.connectedTo(tp4);

        assertNotNull(c12);
        assertNotNull(c13);
        assertNull(c14);
        assertNotNull(c23);
        assertNotNull(c24);
        assertNotNull(c34);

        assertEquals(0, c12.getSubConnectionCount());
        assertEquals(0, c12.getSuperConnectionCount());

        assertEquals(0, c13.getSubConnectionCount());
        assertEquals(0, c13.getSuperConnectionCount());

        assertEquals(0, c23.getSubConnectionCount());
        assertEquals(0, c23.getSuperConnectionCount());

        assertEquals(0, c24.getSubConnectionCount());
        assertEquals(0, c24.getSuperConnectionCount());

        assertEquals(0, c34.getSubConnectionCount());
        assertEquals(0, c34.getSuperConnectionCount());
    }
}