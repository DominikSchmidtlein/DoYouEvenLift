package com.domkick1.trace;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by domin_2o9sb4z on 2016-02-18.
 */
public class LineDictionaryTest {

    LineList lines, compoundLines, tcomp, lcomp, rcomp, bcomp;
    Line tL, bL, lL, rL, tlL, trL, ltL, lbL, rtL, rbL, blL, brL;
    Point tlP, trP, blP, brP, lmP, tmP, rmP, bmP;

    @Before
    public void setUp() {
        lines = new LineList();
        // left
        lines.add(ltL = new Line(tlP = new Point(100, 100), lmP = new Point(100, 500)));
        lines.add(lbL = new Line(lmP, blP = new Point(100, 1000)));

        // bot
        lines.add(blL = new Line(bmP = new Point(600, 1000), blP));
        lines.add(brL = new Line(bmP, brP = new Point(800, 1000)));

        // top
        lines.add(tlL = new Line(tlP, tmP = new Point(400, 150)));
        lines.add(trL = new Line(trP = new Point(1000, 250), tmP));

        // right
        lines.add(rbL = new Line(rmP = new Point(900, 625), brP));
        lines.add(rtL = new Line(trP, rmP));

        compoundLines = new LineList();
        compoundLines.add(tL = new Line(tlP, trP));
        compoundLines.add(lL = new Line(tlP, blP));
        compoundLines.add(rL = new Line(trP, brP));
        compoundLines.add(bL = new Line(blP, brP));

        tcomp = new LineList();
        lcomp = new LineList();
        rcomp = new LineList();
        bcomp = new LineList();

        tcomp.add(tlL);
        tcomp.add(trL);

        lcomp.add(ltL);
        lcomp.add(lbL);

        rcomp.add(rtL);
        rcomp.add(rbL);

        bcomp.add(blL);
        bcomp.add(brL);
    }

    @Test
    public void testConstructor() {
        LineDictionary testDictionary = new LineDictionary(lines, true);

        assertTrue(testDictionary.size() == (8 + 4) * 2);

        // check that all simple lines and their opposites are included and check their values
        for (Line line : lines) {
            assertTrue(testDictionary.containsKey(line));
            assertTrue(testDictionary.containsKey(line.getOpposite()));
            assertTrue(testDictionary.get(line).get(0).equals(line));
            assertTrue(testDictionary.get(line.getOpposite()).get(0).equals(line.getOpposite()));
        }

        // check that all compound lines are contained as keys and their sizes are 2
        for (Line line : compoundLines) {
            assertTrue(testDictionary.containsKey(line));
            assertTrue(testDictionary.containsKey(line.getOpposite()));
            assertTrue(testDictionary.get(line).size() == 2);
            assertTrue(testDictionary.get(line.getOpposite()).size() == 2);
        }

        // check top line components
        // top line
        LineList testLines = testDictionary.get(tL);
        assertTrue(testLines.aDirectionalContains(tlL));
        assertTrue(testLines.aDirectionalContains(trL));
        assertTrue(testLines.get(0).getP2().equals(testLines.get(1).getP1()));

        // left line
        testLines = testDictionary.get(lL);
        assertTrue(testLines.aDirectionalContains(ltL));
        assertTrue(testLines.aDirectionalContains(lbL));
        assertTrue(testLines.get(0).getP2().equals(testLines.get(1).getP1()));

        // right line
        testLines = testDictionary.get(rL);
        assertTrue(testLines.aDirectionalContains(rtL));
        assertTrue(testLines.aDirectionalContains(rbL));
        assertTrue(testLines.get(0).getP2().equals(testLines.get(1).getP1()));

        // bot line
        testLines = testDictionary.get(bL);
        assertTrue(testLines.aDirectionalContains(blL));
        assertTrue(testLines.aDirectionalContains(brL));
        assertTrue(testLines.get(0).getP2().equals(testLines.get(1).getP1()));
    }
}
