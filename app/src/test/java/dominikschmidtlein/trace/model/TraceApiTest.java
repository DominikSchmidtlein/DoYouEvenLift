package dominikschmidtlein.trace.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by domin_2o9sb4z on 2017-01-09.
 */
public class TraceApiTest {
    TraceApi traceApi;
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
        traceApi = new TraceApi();
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

        traceApi.addConnection(c12);
        traceApi.addConnection(c13);
        traceApi.addConnection(c24);
        traceApi.addConnection(c34);
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
    }

    @Test
    public void testAddConnectionSuper() {
        traceApi.addConnection(c15);
        traceApi.addConnection(c25);
        traceApi.addConnection(c35);
        traceApi.addConnection(c45);

        checkTrace();
    }

    @Test
    public void testAddConnectionIntersectPointPThenC() {
        traceApi.addConnection(c25);
        traceApi.addConnection(c14);
        traceApi.addConnection(c35);

        checkTrace();
    }

    @Test
    public void testAddConnectionIntersectPointCThenP() {
        traceApi.addConnection(c14);
        traceApi.addConnection(c25);
        traceApi.addConnection(c35);

        checkTrace();
    }

    @Test
    public void testAddConnectionIntersectConnection() {
        traceApi.addConnection(c14);
        traceApi.addConnection(c23);

        checkTrace();
    }

    @Test
    public void testRemoveConnection() {
        traceApi.addConnection(c15);
        traceApi.addConnection(c25);
        traceApi.addConnection(c35);
        traceApi.addConnection(c45);

        traceApi.removeConnection(c12);

        assertNull(p1.connectedTo(p2));
        assertEquals(p1.connectedTo(p3), c13);
        assertEquals(p1.connectedTo(p4), c14);
        assertEquals(p1.connectedTo(p5), c15);
        assertEquals(p2.connectedTo(p3), c23);
        assertEquals(p2.connectedTo(p4), c24);
        assertEquals(p2.connectedTo(p5), c25);
        assertEquals(p3.connectedTo(p4), c34);
        assertEquals(p3.connectedTo(p5), c35);
        assertEquals(p4.connectedTo(p5), c45);
    }

    @Test
    public void testRemoveConnectionSuper() {
        traceApi.addConnection(c15);
        traceApi.addConnection(c25);
        traceApi.addConnection(c35);
        traceApi.addConnection(c45);

        traceApi.removeConnection(c14);

        assertEquals(p1.connectedTo(p2), c12);
        assertEquals(p1.connectedTo(p3), c13);
        assertNull(p1.connectedTo(p4));
        assertNull(p1.connectedTo(p5));
        assertEquals(p2.connectedTo(p3), c23);
        assertEquals(p2.connectedTo(p4), c24);
        assertEquals(p2.connectedTo(p5), c25);
        assertEquals(p3.connectedTo(p4), c34);
        assertEquals(p3.connectedTo(p5), c35);
        assertNull(p4.connectedTo(p5));
    }

}