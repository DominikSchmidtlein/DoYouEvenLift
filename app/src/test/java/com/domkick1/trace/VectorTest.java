package com.domkick1.trace;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by domin_2o9sb4z on 2016-02-22.
 */
public class VectorTest {

    Vector vector, vector2, vector3;

    @Before
    public void setUp() {
        vector = new Vector(5, 7);
        vector2 = new Vector(5, 7);
        vector3 = new Vector(7, 5);
    }

    @Test
    public void testGetInverse() {
        assertTrue(vector.getInverse().equals(new Vector(-5, -7)));
    }

    @Test
    public void testEquals() {
        assertTrue(!vector.equals(null));
        assertTrue(vector.equals(vector));
        assertTrue(vector.equals(vector2));
        assertTrue(!vector.equals(vector3));
    }

    @Test
    public void testGetUnitVector() {
        Vector unitVector = new Vector(vector.getUnitVector().getX(), vector.getUnitVector().getY());
        assertTrue(Math.hypot(unitVector.getX(), unitVector.getY()) - 1 < 0.0001);
        assertTrue(unitVector.equals(new Vector(0.581238193, 0.813733471)));
    }
}
