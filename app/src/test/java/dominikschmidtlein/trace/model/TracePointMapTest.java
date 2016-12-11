package dominikschmidtlein.trace.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by domin_2o9sb4z on 2016-12-09.
 */
public class TracePointMapTest {

    int RADIUS = 25;
    int WIDTH = 400;
    int HEIGHT = 600;
    int XBINS = 4;
    int YBINS = 4;

    TracePointMap tracePointMap;
    TracePoint pInBin;
    TracePoint pBinBorder;
    TracePoint pMarginBorder;
    TracePoint pMargin;

    @Before
    public void setUp() {
        tracePointMap = new TracePointMap(WIDTH, HEIGHT, RADIUS, XBINS, YBINS);

        pInBin = new TracePoint(WIDTH / XBINS / 2, HEIGHT / YBINS / 2);
        pBinBorder = new TracePoint(WIDTH / XBINS * 2, HEIGHT / YBINS * 2);
        pMarginBorder = new TracePoint(WIDTH / XBINS - RADIUS, HEIGHT / YBINS - RADIUS);
        pMargin = new TracePoint(WIDTH / XBINS - RADIUS / 2, HEIGHT / YBINS - RADIUS / 2);

        tracePointMap.addTracePoint(pInBin);
        tracePointMap.addTracePoint(pBinBorder);
        tracePointMap.addTracePoint(pMarginBorder);
        tracePointMap.addTracePoint(pMargin);
    }

    @Test
    public void testNearPoint() {
        assertNull(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS / 2, HEIGHT / YBINS * 3)));
        assertNull(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS * 3, HEIGHT / YBINS * 3)));
        assertNull(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS * 1.5, HEIGHT / YBINS * 1.5)));
        assertEquals(tracePointMap.nearPoint(pInBin), pInBin);
        assertEquals(tracePointMap.nearPoint(pBinBorder), pBinBorder);
        assertEquals(tracePointMap.nearPoint(pMarginBorder), pMarginBorder);
        assertEquals(tracePointMap.nearPoint(pMargin), pMargin);
        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS / 2 + RADIUS / Math.sqrt(2) - 1, HEIGHT / YBINS / 2 + RADIUS / Math.sqrt(2) - 1)), pInBin);
        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS, HEIGHT / YBINS)), pMargin);
        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS + 1, HEIGHT / YBINS + 1)), pMargin);

        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS - RADIUS * 0.75 - 1, HEIGHT / YBINS - RADIUS * 0.75 - 1)), pMarginBorder);
        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS - RADIUS * 0.75 + 1, HEIGHT / YBINS - RADIUS * 0.75 + 1)), pMargin);
    }

    @Test
    public void testNearPointBinBorder() {
        assertNull(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS * 2 - RADIUS, HEIGHT / YBINS * 2 - RADIUS)));
        assertNull(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS * 2 - RADIUS, HEIGHT / YBINS * 2 + RADIUS)));
        assertNull(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS * 2 + RADIUS, HEIGHT / YBINS * 2 - RADIUS)));
        assertNull(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS * 2 + RADIUS, HEIGHT / YBINS * 2 + RADIUS)));

        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS * 2, HEIGHT / YBINS * 2 - 1)), pBinBorder);
        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS * 2, HEIGHT / YBINS * 2 + 1)), pBinBorder);
        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS * 2 - 1, HEIGHT / YBINS * 2)), pBinBorder);
        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS * 2 + 1, HEIGHT / YBINS * 2)), pBinBorder);

        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS * 2 - 1, HEIGHT / YBINS * 2 - 1)), pBinBorder);
        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS * 2 - 1, HEIGHT / YBINS * 2 + 1)), pBinBorder);
        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS * 2 + 1, HEIGHT / YBINS * 2 - 1)), pBinBorder);
        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS * 2 + 1, HEIGHT / YBINS * 2 + 1)), pBinBorder);
    }

    @Test
    public void testNearPointMargin() {
        assertNull(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS + RADIUS / 2, HEIGHT / YBINS - RADIUS * 1.5)));
        assertNull(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS + RADIUS / 2, HEIGHT / YBINS + RADIUS / 2)));
        assertNull(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS - RADIUS * 1.5, HEIGHT / YBINS + RADIUS / 2)));

        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS, HEIGHT / YBINS)), pMargin);
        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS - 1, HEIGHT / YBINS - 1)), pMargin);
        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS - 1, HEIGHT / YBINS + 1)), pMargin);
        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS + 1, HEIGHT / YBINS - 1)), pMargin);
        assertEquals(tracePointMap.nearPoint(new TracePoint(WIDTH / XBINS + 1, HEIGHT / YBINS + 1)), pMargin);
    }

    @Test
    public void testCalculateBin() {
        assertEquals(tracePointMap.calculateBin(0, 0), 0);
        assertEquals(tracePointMap.calculateBin(100, 75), 1);
        assertEquals(tracePointMap.calculateBin(299, 149), 2);
        assertEquals(tracePointMap.calculateBin(301, 149), 3);
        assertEquals(tracePointMap.calculateBin(50, 225), 4);
        assertEquals(tracePointMap.calculateBin(101, 151), 5);
        assertEquals(tracePointMap.calculateBin(201, 299), 6);
        assertEquals(tracePointMap.calculateBin(400, 150), 7);
        assertEquals(tracePointMap.calculateBin(0, 300), 8);
        assertEquals(tracePointMap.calculateBin(100, 449), 9);
        assertEquals(tracePointMap.calculateBin(299, 300), 10);
        assertEquals(tracePointMap.calculateBin(400, 375), 11);
        assertEquals(tracePointMap.calculateBin(0, 600), 12);
        assertEquals(tracePointMap.calculateBin(100, 600), 13);
        assertEquals(tracePointMap.calculateBin(299, 600), 14);
        assertEquals(tracePointMap.calculateBin(400, 600), 15);
    }

}