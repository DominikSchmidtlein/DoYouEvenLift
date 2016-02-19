package com.domkick1.trace;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by domin_2o9sb4z on 2016-02-17.
 */
public class LineDictionary extends HashMap<Line, LineList> {

    public LineDictionary(LineList lines) {
        super();
        addLines(lines);
        addCompoundLines(lines);
    }

    /**
     * Adds all lines from lines to this dictionary and sets the value as a list of only the 1 line.
     * Each line is added p1,p2 and p2,p1 (forwards and backwards).
     *
     * @param lines a set of lines to add to this dictionary
     */
    private void addLines(LineList lines) {
        LineList components;
        for (Line line : lines) {
            components = new LineList();
            components.add(line);
            put(line, components);

            components = new LineList();
            components.add(line.getOpposite());
            put(line.getOpposite(), components);
        }
    }

    /**
     * Checks each line in shape to see if it is part of a compound line. If so, the new line is
     * added to the map and added to the queue to be searched for compound lines.
     *
     * @param lines
     */
    private void addCompoundLines(LineList lines) {
        Queue<Line> queue = new LinkedList<>(lines);
        Point commonPoint;

        while (!queue.isEmpty()) {
            Line testLine = queue.poll();
            LineList simpleAndCompoundLines = new LineList(keySet());
            for (Line line : simpleAndCompoundLines) {
                if ((commonPoint = testLine.isTouching(line)) == null)
                    continue; //not touching
                if (!testLine.getP1().equals(commonPoint))
                    testLine = testLine.getOpposite();
                if (!line.getP1().equals(commonPoint))
                    line = line.getOpposite();
                if (!new Vector(testLine).getUnitVector().equals(new Vector(line).getUnitVector().getInverse()))
                    continue; //not same direction or are overlapping

                queue.add(insertCompoundLine(testLine, line));
            }
        }
    }

    /**
     * Takes 2 lines which are known to be part of a component line. It is also known that they are
     * connect at their respective p1's. Method creates the compound line at its components, and
     * then adds it to the map.
     *
     * @param l1 one of two lines that make the compound line
     * @param l2 two of two lines that make the compound line
     * @return the new component line with l1.p2 as its p1 and l2.p2 as p2
     */
    private Line insertCompoundLine(Line l1, Line l2) {
        Line compoundLine1 = new Line(l1.getP2(), l2.getP2());
        Line compoundLine2 = new Line(l2.getP2(), l1.getP2());

        LineList components1 = new LineList();
        LineList components2 = new LineList();

        components1.addAll(get(l1.getOpposite()));
        components1.addAll(get(l2));
        components2.addAll(get(l2.getOpposite()));
        components2.addAll(get(l1));

        put(compoundLine1, components1);
        put(compoundLine2, components2);

        return compoundLine1;
    }


}
