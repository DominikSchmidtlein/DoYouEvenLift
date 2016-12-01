package dominikschmidtlein.trace.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by domin_2o9sb4z on 2016-02-18.
 */
public class ConnectionTest {

    TracePoint p1;
    TracePoint p2;

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
        p2 = new TracePoint(200, 100);

        c1 = new Connection(p1, p2);
        c2 = new Connection(null, null);
        c3 = new Connection(null, null);
        c4 = new Connection(null, null);
        c5 = new Connection(null, null);
        c6 = new Connection(null, null);
        c7 = new Connection(null, null);
        c8 = new Connection(null, null);
        c9 = new Connection(null, null);
        c10 = new Connection(null, null);

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
    public void testConstructor() {
        assertNotNull(p1.connectedTo(p2));
        assertNotNull(p2.connectedTo(p1));
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



}
