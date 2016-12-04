package dominikschmidtlein.trace.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by domin_2o9sb4z on 2016-02-18.
 */
public class ConnectionTest {

    TracePoint p1;
    TracePoint p2;
    TracePoint p3;
    TracePoint p4;
    TracePoint p5;

    Connection c1;
    Connection c2;
    Connection c3;
    Connection c4;
    Connection c5;
    Connection c6;
    Connection c7;
    Connection c8;
    Connection c9;
    Connection c10;

    @Before
    public void setUp() {
        p1 = new TracePoint(100, 100);
        p2 = new TracePoint(200, 200);
        p3 = new TracePoint(300, 300);
        p4 = new TracePoint(400, 400);
        p5 = new TracePoint(500, 500);

        c1 = new Connection(p1, p2);
        c2 = new Connection(p2, p3);
        c3 = new Connection(p3, p4);
        c4 = new Connection(p4, p5);
        c5 = new Connection(p1, p3);
        c6 = new Connection(p3, p5);
        c7 = new Connection(p2, p4);
        c8 = new Connection(p1, p4);
        c9 = new Connection(p2, p5);
        c10 = new Connection(p1, p5);

        c1.addSuperConnection(c5);

        c2.addSuperConnection(c5);
        c2.addSuperConnection(c7);

        c3.addSuperConnection(c6);
        c3.addSuperConnection(c7);

        c4.addSuperConnection(c6);

        c5.addSuperConnection(c8);

        c6.addSuperConnection(c9);

        c7.addSuperConnection(c8);
        c7.addSuperConnection(c9);

        c8.addSuperConnection(c10);

        c9.addSuperConnection(c10);
    }

    @Test
    public void testSetOccupied1() {
        c1.setOccupied();

        assertTrue(c1.isOccupied());
        assertTrue(c2.isFree());
        assertTrue(c3.isFree());
        assertTrue(c4.isFree());
        assertTrue(c5.isBlocked());
        assertTrue(c6.isFree());
        assertTrue(c7.isFree());
        assertTrue(c8.isBlocked());
        assertTrue(c9.isFree());
        assertTrue(c10.isBlocked());
    }

    @Test
    public void testSetOccupied2() {
        c2.setOccupied();

        assertTrue(c1.isFree());
        assertTrue(c2.isOccupied());
        assertTrue(c3.isFree());
        assertTrue(c4.isFree());
        assertTrue(c5.isBlocked());
        assertTrue(c6.isFree());
        assertTrue(c7.isBlocked());
        assertTrue(c8.isBlocked());
        assertTrue(c9.isBlocked());
        assertTrue(c10.isBlocked());
    }

    @Test
    public void testSetOccupied5() {
        c5.setOccupied();

        assertTrue(c1.isOccupied());
        assertTrue(c2.isOccupied());
        assertTrue(c3.isFree());
        assertTrue(c4.isFree());
        assertTrue(c5.isOccupied());
        assertTrue(c6.isFree());
        assertTrue(c7.isBlocked());
        assertTrue(c8.isBlocked());
        assertTrue(c9.isBlocked());
        assertTrue(c10.isBlocked());
    }

    @Test
    public void testSetOccupied7() {
        c7.setOccupied();

        assertTrue(c1.isFree());
        assertTrue(c2.isOccupied());
        assertTrue(c3.isOccupied());
        assertTrue(c4.isFree());
        assertTrue(c5.isBlocked());
        assertTrue(c6.isBlocked());
        assertTrue(c7.isOccupied());
        assertTrue(c8.isBlocked());
        assertTrue(c9.isBlocked());
        assertTrue(c10.isBlocked());
    }

    @Test
    public void testSetOccupied8() {
        c8.setOccupied();

        assertTrue(c1.isOccupied());
        assertTrue(c2.isOccupied());
        assertTrue(c3.isOccupied());
        assertTrue(c4.isFree());
        assertTrue(c5.isOccupied());
        assertTrue(c6.isBlocked());
        assertTrue(c7.isOccupied());
        assertTrue(c8.isOccupied());
        assertTrue(c9.isBlocked());
        assertTrue(c10.isBlocked());
    }

    @Test
    public void testSetOccupied10() {
        c10.setOccupied();

        assertTrue(c1.isOccupied());
        assertTrue(c2.isOccupied());
        assertTrue(c3.isOccupied());
        assertTrue(c4.isOccupied());
        assertTrue(c5.isOccupied());
        assertTrue(c6.isOccupied());
        assertTrue(c7.isOccupied());
        assertTrue(c8.isOccupied());
        assertTrue(c9.isOccupied());
        assertTrue(c10.isOccupied());
    }

    @Test
    public void testEquals() {
        assertNotEquals(c1, c2);
        assertNotEquals(c1, c5);
        assertNotEquals(c1, c8);
        assertNotEquals(c1, c10);
        assertEquals(c1, c1);
        assertEquals(c1, new Connection(p1, p2));
        assertEquals(c1, new Connection(new TracePoint(100, 100), new TracePoint(200, 200)));
    }

}
