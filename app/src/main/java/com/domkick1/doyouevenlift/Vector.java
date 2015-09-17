package com.domkick1.doyouevenlift;

/**
 * Created by dominik on 10/08/15.
 */
public class Vector {

    private int x;
    private int y;

    public Vector(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Vector(Line line){
        this((int)(line.getP2().getX() - line.getP1().getX()),(int)(line.getP2().getY() - line.getP1().getY()));
    }

    public Vector(Point startPoint, Point endPoint){
        this(new Line(startPoint,endPoint));
    }

    public Vector getInverse(){
        return new Vector(x * -1,y * -1);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(!(o instanceof Vector))
            return false;
        Vector v = (Vector) o;
        if(this.x != v.getX())
            return false;
        if(this.y != v.getY())
            return false;
        return true;
    }

    public Vector getUnitVector(){
        double hypotenuse = Math.hypot(this.x,this.y);
        return new Vector((int)Math.round(this.x/hypotenuse),(int)Math.round(this.y/hypotenuse));
    }

    private int GCD(int n1, int n2){
        for(int i = 2; i <= n1; i ++)
            if(n1 % i == 0 && n2 % i == 0)
                return i * GCD(n1/i,n2/i);
        return 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
