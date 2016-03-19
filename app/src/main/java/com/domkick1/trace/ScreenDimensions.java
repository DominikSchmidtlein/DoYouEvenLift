package com.domkick1.trace;

/**
 * Created by domin_2o9sb4z on 2016-03-17.
 */
public class ScreenDimensions {

    private int width;
    private int height;
    private int topOffset;

    public ScreenDimensions(int w, int h, int tO) {
        width = w;
        height = h;
        topOffset = tO;
    }

    public int getHeight() {
        return height;
    }

    public int getTopOffset() {
        return topOffset;
    }

    public int getWidth() {
        return width;
    }
}
