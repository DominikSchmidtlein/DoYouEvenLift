package com.domkick1.doyouevenlift;

/**
 * Created by dominik on 05/08/15.
 */
public class Line {
    public static final double INFINITY = 99999;

    private Point p1;
    private Point p2;

    public Line(Point p1, Point p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    public Line(float p1x,float p1y,float p2x, float p2y){
        this(new Point(p1x,p1y),new Point(p2x,p2y));
    }

    public Line(Line l1, Line l2){
        this(l1.getP2(),l2.getP2());
    }

    public double getSlope(){
        double rise = p2.getY() - p1.getY();
        double run = p2.getX() - p1.getX();
        if(run == 0)
            return this.INFINITY;
        return rise/run;
    }

    public double getIsometricSlope(double xOffset, double yOffset, double columnWidth, double rowHeight){
        double rise = p2.getY() - p1.getY();
        double p1IsometricX = p1.getX() - xOffset - (p1.getY()-yOffset)/rowHeight*(columnWidth/2);
        double p2IsometricX = p2.getX() - xOffset - (p2.getY()-yOffset)/rowHeight*(columnWidth/2);
        double isometricRun = p2IsometricX - p1IsometricX;
        if(isometricRun == 0)
            return this.INFINITY;
        return rise/isometricRun;
    }

    public Point isTouching(Line line){
        if(line == null)
            return null;
        if(p1.equals(line.getP1()) || p1.equals(line.getP2()))
            return p1;
        if(p2.equals(line.getP1()) || p2.equals(line.getP2()))
            return p2;
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Line))
            return false;
        Line l = (Line) o;
        if(!this.p1.equals(l.getP1()))
            return false;
        if(!this.p2.equals(l.getP2()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Math.round(p1.getX() * p1.getY() * p2.getX() * p2.getY()/1000000-2147483647);
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }
}
