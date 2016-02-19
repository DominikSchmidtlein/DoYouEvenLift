package com.domkick1.trace;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Created by domin_2o9sb4z on 2016-02-18.
 */
public class LineListTest {

    private LineList lines1, lines2, lines3, lines4;

    private Line line1, line2, line3, line4;
    private Point point1a, point1b, point2a, point2b, point3a, point3b;

    @Before
    public void setUp() {
        lines1 = new LineList(3);
        lines1.add(line1 = new Line(point1a = new Point(100, 100), point2a = new Point(200, 800)));
        lines1.add(line2 = new Line(point2a, point3a = new Point(1000, 0)));
        lines1.add(line3 = new Line(point3a, point1a));

        lines2 = new LineList(2);
        lines2.add(line1.getOpposite());
        lines2.add(line4 = new Line(point1b = new Point(666, 666), point2b = new Point(700, 200)));

        lines3 = new LineList(1);
        lines3.add(line4);

        lines4 = new LineList(4);
        lines4.add(line1);
        lines4.add(line1.getOpposite());
        lines4.add(line2);
        lines4.add(line2);
    }

    @Test
    public void testFloatConstructor() {
        float[] flts = new float[]{point1b.getX(), point1b.getY(), point2b.getX(), point2b.getY()};
        assertTrue(lines3.equals(new LineList(flts)));
    }

    @Test
    public void testPointListConstructor() throws Exception {
        PointList pointsDoubled = new PointList();
        pointsDoubled.add(line1.getP1());
        pointsDoubled.add(line1.getP2());
        pointsDoubled.add(line2.getP1());
        pointsDoubled.add(line2.getP2());

        PointList pointsSingle = new PointList(pointsDoubled);
        pointsSingle.remove(line1.getP2());

        LineList lines = new LineList(lines1);
        lines.remove(line3);

        assertTrue(new LineList(pointsDoubled, false).equals(new LineList(pointsSingle, true)));
        assertTrue(new LineList(pointsDoubled, false).equals(lines));
    }

    @Test
    public void testToFloatArray() {
        float[] floats = {point2a.getX(), point2a.getY(), point1a.getX(), point1a.getY(),
                point1b.getX(), point1b.getY(), point2b.getX(), point2b.getY()};
        assertTrue(Arrays.equals(floats, lines2.toFloatArray()));
    }

    @Test
    public void testReplaceLastPoint() {
        LineList lines = new LineList(lines3);
        lines.replaceLastPoint(point3a);
        assertTrue(lines.get(0).equals(new Line(point1b, point3a)));
    }

    @Test
    public void testIsNearVertex() {
        assertTrue(lines1.isNearVertex(point1a, 5).equals(point1a));
        assertTrue(lines1.isNearVertex(point1b, 80) == null);
    }

    @Test
    public void testADirectionalContains() {
        assertTrue(lines1.aDirectionalContains(line1));
        assertTrue(lines1.aDirectionalContains(line1.getOpposite()));
        assertTrue(!lines1.aDirectionalContains(line4));
    }

    @Test
    public void testGetWithoutDuplicates() {
        assertTrue(lines1.getWithoutDuplicates().equals(lines1));
        assertTrue(lines4.getWithoutDuplicates().isEmpty());
    }

    @Test
    public void testGetOddlyOccurringPoints() {
        assertTrue(lines1.getOddlyOccurringPoints().isEmpty());
        assertTrue(lines2.getOddlyOccurringPoints().size() == 4);
    }

    @Test
    public void testAddLines() {
        LineList lineList = new LineList();
        lineList.add(line4);
        lineList.addLines(lines2);
        assertTrue(lineList.equals(lines2));
    }

    @Test
    public void testIsOccupied() {
        assertTrue(!lines1.isOccupied(null));
        assertTrue(lines1.isOccupied(lines1));
        assertTrue(lines1.isOccupied(lines2));
        assertTrue(!lines1.isOccupied(lines3));
    }
}
