package com.domkick1.trace;

import org.junit.Before;
import org.junit.Test;

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


    }

    @Test
    public void testToJsonArray() {


    }

    @Test
    public void testToFloatArray() {


    }

    @Test
    public void testReplaceLastPoint() {


    }

    @Test
    public void testIsNearVertex() {


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
