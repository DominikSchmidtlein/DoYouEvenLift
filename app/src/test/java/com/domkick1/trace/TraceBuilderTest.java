package com.domkick1.trace;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Created by domin_2o9sb4z on 2016-02-18.
 */
public class TraceBuilderTest {

    private TraceBuilder traceBuilder;

    @Before
    public void setUp() {
        traceBuilder = new TraceBuilder(null, new android.graphics.Point(600, 1025), 300, TraceBuilder.Mode.ISOMETRIC);
    }

    @Test
    public void testGenerateSquarePoints() {
        PointList points = new PointList(new float[]{
                100, 400, 300, 400, 500, 400,
                100, 600, 300, 600, 500, 600,
                100, 800, 300, 800, 500, 800});
        assertTrue(points.equals(traceBuilder.generateSquarePoints(200, 600, 1025, 300)));
    }

    @Test
    public void testGenerateIsometricPoints() {
        float r3 = (float) Math.sqrt(3);
        float h0 = 400;
        float h1 = 400 + 100 * r3;
        float h2 = 400 + 200 * r3;

        PointList points = new PointList(new float[]{
                100, h0, 300, h0, 500, h0,
                200, h1, 400, h1,
                100, h2, 300, h2, 500, h2
        });

        PointList points2 = traceBuilder.generateIsometricPoints(200, 600, 800, 300);

        assertTrue(points.equals(points2));
    }

    @Test
    public void testGetXOffset() {
        assertTrue(traceBuilder.getXOffset(400, 173, 200, 300) == 0);
        assertTrue(traceBuilder.getXOffset(573, 173, 200, 300) == 100);
        assertTrue(traceBuilder.getXOffset(919, 173, 200, 300) == 100);
    }
}
