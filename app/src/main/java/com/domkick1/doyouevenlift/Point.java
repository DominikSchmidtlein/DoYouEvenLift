package com.domkick1.doyouevenlift;

/**
 * Created by dominik on 03/08/15.
 */
public class Point {
    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Point))
            return false;
        Point p = (Point) o;
        if(p.getX() != this.getX())
            return false;
        if(p.getY() != this.getY())
            return false;
        return true;
    }

    public float getX(){
        return this.x;
    }
    public float getY(){
        return this.y;
    }
    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }
}
