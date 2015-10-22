package com.domkick1.doyouevenlift;

/**
 * Created by dominik on 10/08/15.
 */
public class Vector {
    public static final double EPSILON = 0.0001;

    private double x;
    private double y;

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector(Point startPoint, Point endPoint){
        this(endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());
    }

    public Vector(Line line){
        this(line.getP1(), line.getP2());
    }

    public Vector getInverse(){
        return new Vector(x * -1, y * -1);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Vector))
            return false;
        Vector v = (Vector) o;
        if(this.x - v.getX() > EPSILON)
            return false;
        if(this.y - v.getY() > EPSILON)
            return false;
        return true;
    }

    public Vector getUnitVector(){
        double hypotenuse = Math.hypot(this.x,this.y);
        return new Vector(this.x/hypotenuse, this.y/hypotenuse);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
