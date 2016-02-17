package com.domkick1.trace;

import java.util.ArrayList;

/**
 * Created by dominik on 28/07/15.
 */
public class Levels {
//    public static final String NAME = "Levels";
//
//    public final float[][] LEVELS = new float[][]{
//            new float[]{400, 620, 600, 620, 600, 620, 800, 620, 800, 620, 700, 446, 700, 446, 600, 620, 600, 620, 500, 446, 500, 446, 400, 620},
//
//            new float[]{100, 793, 300, 446, 300, 446, 500, 793, 500, 793, 600, 966, 600, 966, 700, 1139, 700, 1139, 900, 793, 900, 793, 800, 620, 800, 620, 700, 793, 700, 793, 600, 966, 600, 966, 500, 1139, 500, 1139, 300, 793, 300, 793, 200, 966, 200, 966, 100, 793, 100, 793, 300, 793, 300, 793, 500, 793, 500, 793, 700, 793, 700, 793, 900, 793,},
//            new float[]{100, 100, 500, 500, 500, 500, 100, 500, 100, 500, 100, 100},
//            new float[]{100, 1100, 300, 1100, 300, 1100, 900, 1100, 900, 1100, 500, 300, 500, 300, 100, 1100, 100, 1100, 300, 900, 300, 900, 700, 900, 700, 900, 500, 700, 500, 700, 300, 900, 300, 900, 300, 1100,},
//
//            new float[]{600, 620, 400, 273, 400, 273, 300, 446, 300, 446, 600, 620, 600, 620, 200, 620, 200, 620, 300, 793, 300, 793, 600, 620, 600, 620, 400, 966, 400, 966, 600, 966, 600, 966, 600, 620, 600, 620, 800, 966, 800, 966, 900, 793, 900, 793, 600, 620, 600, 620, 1000, 620, 1000, 620, 900, 446, 900, 446, 600, 620, 600, 620, 800, 273, 800, 273, 600, 273, 600, 273, 600, 620,},
//            new float[]{100, 1100, 500, 500, 500, 500, 900, 1100, 900, 1100, 100, 700, 100, 700, 900, 700, 900, 700, 100, 1100,},
//
//            new float[]{300, 700, 700, 700, 700, 700, 500, 500, 500, 500, 300, 700, 300, 700, 300, 1100, 300, 1100, 700, 1100, 700, 1100, 700, 700,},
//
//            new float[]{300, 1300, 500, 1500, 500, 1500, 700, 1300, 700, 1300, 300, 1300, 300, 1300, 100, 1100, 100, 1100, 900, 1100, 900, 1100, 700, 900, 700, 900, 300, 900, 300, 900, 100, 700, 100, 700, 300, 700, 300, 700, 900, 700, 900, 700, 700, 500, 700, 500, 100, 500, 100, 500, 300, 300, 300, 300, 900, 300, 900, 300, 700, 100, 700, 100, 500, 100, 500, 100, 300, 300, 300, 300, 700, 500, 700, 500, 900, 300, 900, 300, 900, 700, 900, 700, 900, 900, 900, 900, 700, 900, 700, 900, 100, 1100, 100, 1100, 100, 900, 100, 900, 300, 900, 300, 900, 100, 1300, 100, 1300, 100, 1500, 100, 1500, 500, 1500, 500, 1500, 900, 1500, 900, 1500, 900, 1300, 900, 1300, 700, 1300, 700, 1300, 900, 1100, 900, 1100, 900, 900, 900, 900, 300, 700, 300, 700, 100, 500, 100, 500, 100, 300, 100, 300, 100, 100, 100, 100, 300, 100, 300, 100, 100, 300, 100, 300, 300, 300, 300, 300, 300, 100, 300, 100, 500, 100, 500, 100, 700, 500, 700, 500, 300, 700,},
//            new float[]{500, 793, 300, 793, 300, 793, 400, 966, 400, 966, 500, 793, 500, 793, 600, 966, 600, 966, 700, 793, 700, 793, 500, 793, 500, 793, 600, 620, 600, 620, 400, 620, 400, 620, 500, 793, 500, 793, 500, 1139, 500, 1139, 700, 1139, 700, 1139, 900, 793, 900, 793, 800, 620, 800, 620, 700, 446, 700, 446, 300, 446, 300, 446, 200, 620, 200, 620, 100, 793, 100, 793, 300, 1139, 300, 1139, 500, 1139, 500, 1139, 500, 1486, 500, 1486, 900, 1486, 900, 1486, 800, 1312, 800, 1312, 200, 1312, 200, 1312, 100, 1486, 100, 1486, 500, 1486,},
//            new float[]{400, 620, 200, 620, 200, 620, 300, 793, 300, 793, 500, 793, 500, 793, 700, 793, 700, 793, 900, 793, 900, 793, 1000, 620, 1000, 620, 800, 620, 800, 620, 900, 446, 900, 446, 700, 446, 700, 446, 800, 620, 800, 620, 600, 620, 600, 620, 700, 446, 700, 446, 500, 446, 500, 446, 600, 620, 600, 620, 400, 620, 400, 620, 500, 446, 500, 446, 300, 446, 300, 446, 400, 620, 400, 620, 500, 793, 500, 793, 600, 620, 600, 620, 700, 793, 700, 793, 800, 620,},
//
//            new float[]{200, 620, 200, 966, 200, 966, 800, 966, 800, 966, 800, 620, 800, 620, 500, 793, 500, 793, 200, 620, 200, 620, 800, 620,},
//            new float[]{400, 273, 300, 446, 300, 446, 400, 620, 400, 620, 300, 793, 300, 793, 400, 966, 400, 966, 300, 1139, 300, 1139, 500, 1139, 500, 1139, 400, 1312, 400, 1312, 600, 1312, 600, 1312, 700, 1139, 700, 1139, 800, 1312, 800, 1312, 1000, 966, 1000, 966, 900, 793, 900, 793, 800, 966, 800, 966, 700, 793, 700, 793, 800, 620, 800, 620, 1000, 620, 1000, 620, 900, 446, 900, 446, 700, 446, 700, 446, 800, 273, 800, 273, 600, 273, 600, 273, 700, 446, 700, 446, 500, 446, 500, 446, 600, 273, 600, 273, 400, 273, 400, 273, 500, 446, 500, 446, 400, 620, 400, 620, 600, 620, 600, 620, 700, 793, 700, 793, 600, 966, 600, 966, 400, 966, 400, 966, 500, 1139, 500, 1139, 700, 1139, 700, 1139, 800, 966, 800, 966, 600, 966, 600, 966, 500, 793, 500, 793, 600, 620,},
//            new float[]{300, 1139, 400, 1312, 400, 1312, 500, 1139, 500, 1139, 400, 966, 400, 966, 300, 1139, 300, 1139, 500, 1139, 500, 1139, 700, 1139, 700, 1139, 600, 966, 600, 966, 500, 1139, 500, 1139, 600, 1312, 600, 1312, 900, 1139, 900, 1139, 800, 966, 800, 966, 700, 1139, 700, 1139, 700, 793, 700, 793, 900, 793, 900, 793, 800, 620, 800, 620, 600, 620, 600, 620, 500, 793, 500, 793, 700, 793, 700, 793, 600, 966, 600, 966, 500, 793, 500, 793, 300, 793, 300, 793, 400, 620, 400, 620, 500, 793, 500, 793, 500, 446, 500, 446, 700, 446, 700, 446, 600, 620, 600, 620, 700, 793, 700, 793, 800, 620, 800, 620, 900, 446, 900, 446, 800, 273, 800, 273, 700, 446, 700, 446, 800, 620,},
//            new float[]{400, 966, 500, 793, 500, 793, 600, 966, 600, 966, 700, 793, 700, 793, 600, 620, 600, 620, 400, 620, 400, 620, 300, 793, 300, 793, 400, 966, 400, 966, 600, 966,},
//            new float[]{200, 966, 400, 620, 400, 620, 500, 793, 500, 793, 600, 966, 600, 966, 400, 966, 400, 966, 500, 793, 500, 793, 600, 620, 600, 620, 800, 966, 800, 966, 600, 1312, 600, 1312, 500, 1139, 500, 1139, 400, 966, 400, 966, 200, 966, 200, 966, 400, 1312, 400, 1312, 500, 1139, 500, 1139, 600, 966, 600, 966, 800, 966,},
//            new float[]{300, 793, 400, 620, 400, 620, 800, 620, 800, 620, 900, 793, 900, 793, 300, 793, 300, 793, 400, 966, 400, 966, 800, 966, 800, 966, 900, 793,},
//            new float[]{300, 793, 400, 620, 400, 620, 500, 793, 500, 793, 400, 966, 400, 966, 400, 620, 400, 620, 600, 620, 600, 620, 600, 966, 600, 966, 500, 793, 500, 793, 600, 620, 600, 620, 700, 793, 700, 793, 600, 966, 600, 966, 400, 966, 400, 966, 300, 793,},
//            new float[]{400, 966, 500, 1139, 500, 1139, 600, 966, 600, 966, 700, 793, 700, 793, 800, 620, 800, 620, 600, 620, 600, 620, 500, 793, 500, 793, 400, 966, 400, 966, 600, 966, 600, 966, 500, 793, 500, 793, 700, 793, 700, 793, 600, 620, 600, 620, 500, 446, 500, 446, 400, 620, 400, 620, 300, 793, 300, 793, 200, 966, 200, 966, 400, 966, 400, 966, 300, 793, 300, 793, 500, 793, 500, 793, 400, 620, 400, 620, 600, 620,},
//            new float[]{300, 793, 700, 793, 700, 793, 900, 793, 900, 793, 800, 620, 800, 620, 700, 793, 700, 793, 600, 966, 600, 966, 500, 793, 500, 793, 400, 620, 400, 620, 300, 793, 300, 793, 400, 966, 400, 966, 500, 793, 500, 793, 600, 620, 600, 620, 800, 966, 800, 966, 900, 793,},
//            new float[]{700, 793, 600, 620, 600, 620, 400, 620, 400, 620, 500, 446, 500, 446, 600, 620, 600, 620, 500, 793, 500, 793, 400, 620, 400, 620, 300, 793, 300, 793, 400, 966, 400, 966, 500, 793, 500, 793, 600, 966, 600, 966, 700, 793, 700, 793, 500, 793, 500, 793, 300, 793,},
//            new float[]{600, 620, 400, 620, 400, 620, 300, 793, 300, 793, 400, 966, 400, 966, 600, 966, 600, 966, 700, 793, 700, 793, 600, 620, 600, 620, 700, 446, 700, 446, 300, 446, 300, 446, 200, 620, 200, 620, 100, 793, 100, 793, 300, 1139, 300, 1139, 700, 1139, 700, 1139, 900, 793, 900, 793, 800, 620, 800, 620, 700, 446, 700, 446, 800, 273, 800, 273, 200, 273, 200, 273, 100, 100, 100, 100, 900, 100, 900, 100, 800, 273,},
//            new float[]{100, 793, 300, 1139, 300, 1139, 900, 1139, 900, 1139, 800, 966, 800, 966, 1000, 620, 1000, 620, 900, 446, 900, 446, 800, 620, 800, 620, 600, 966, 600, 966, 400, 966, 400, 966, 600, 620, 600, 620, 700, 446, 700, 446, 800, 273, 800, 273, 1000, 273, 1000, 273, 900, 100, 900, 100, 700, 100, 700, 100, 500, 446, 500, 446, 400, 620, 400, 620, 300, 793, 300, 793, 200, 620, 200, 620, 300, 446, 300, 446, 500, 100, 500, 100, 100, 100, 100, 100, 200, 273, 200, 273, 100, 446, 100, 446, 100, 793, 100, 793, 100, 1486, 100, 1486, 300, 1486, 300, 1486, 200, 1312, 200, 1312, 1000, 1312, 1000, 1312, 900, 1486, 900, 1486, 300, 1486,},
//            new float[]{200, 273, 300, 446, 300, 446, 400, 273, 400, 273, 200, 273, 200, 273, 500, 100, 500, 100, 800, 273, 800, 273, 700, 446, 700, 446, 900, 446, 900, 446, 800, 273, 800, 273, 800, 620, 800, 620, 700, 793, 700, 793, 600, 620, 600, 620, 800, 620, 800, 620, 500, 793, 500, 793, 400, 966, 400, 966, 600, 966, 600, 966, 500, 793, 500, 793, 500, 1139, 500, 1139, 700, 1139, 700, 1139, 600, 1312, 600, 1312, 500, 1139, 500, 1139, 800, 1312, 800, 1312, 900, 1139, 900, 1139, 1000, 1312, 1000, 1312, 800, 1312, 800, 1312, 800, 966, 800, 966, 900, 793, 900, 793, 1000, 966, 1000, 966, 800, 966, 800, 966, 300, 1139, 300, 1139, 200, 1312, 200, 1312, 100, 1139, 100, 1139, 300, 1139, 300, 1139, 300, 1486, 300, 1486, 400, 1312, 400, 1312, 500, 1486, 500, 1486, 300, 1486, 300, 1486, 600, 1312, 600, 1312, 200, 966, 200, 966, 100, 793, 100, 793, 300, 793, 300, 793, 200, 966, 200, 966, 200, 620, 200, 620, 400, 620, 400, 620, 300, 446, 300, 446, 500, 446, 500, 446, 400, 620, 400, 620, 400, 273, 400, 273, 100, 446, 100, 446, 300, 446, 300, 446, 200, 620,},
//            new float[]{500, 100, 300, 446, 300, 446, 500, 446, 500, 446, 700, 446, 700, 446, 500, 100, 500, 100, 500, 446, 500, 446, 600, 620, 600, 620, 500, 793, 500, 793, 400, 620, 400, 620, 600, 620, 600, 620, 800, 620, 800, 620, 700, 793, 700, 793, 600, 966, 600, 966, 800, 966, 800, 966, 700, 793, 700, 793, 500, 793, 500, 793, 600, 966, 600, 966, 400, 966, 400, 966, 500, 793, 500, 793, 300, 793, 300, 793, 400, 620, 400, 620, 500, 446,},
//            new float[]{300, 1139, 200, 1312, 200, 1312, 400, 1312, 400, 1312, 300, 1139, 300, 1139, 200, 966, 200, 966, 100, 793, 100, 793, 300, 793, 300, 793, 200, 966, 200, 966, 400, 966, 400, 966, 500, 1139, 500, 1139, 600, 966, 600, 966, 400, 966, 400, 966, 500, 793, 500, 793, 400, 620, 400, 620, 600, 620, 600, 620, 500, 793, 500, 793, 700, 793, 700, 793, 800, 966, 800, 966, 900, 793, 900, 793, 700, 793, 700, 793, 800, 620, 800, 620, 900, 446, 900, 446, 700, 446, 700, 446, 800, 620, 800, 620, 500, 446, 500, 446, 600, 273, 600, 273, 400, 273, 400, 273, 500, 446, 500, 446, 300, 446, 300, 446, 200, 620, 200, 620, 100, 446, 100, 446, 300, 446, 300, 446, 200, 273, 200, 273, 300, 100, 300, 100, 100, 100, 100, 100, 200, 273,},
//            new float[]{1000, 620, 800, 620, 800, 620, 200, 620, 200, 620, 100, 793, 100, 793, 900, 793, 900, 793, 1000, 620, 1000, 620, 900, 446, 900, 446, 100, 446, 100, 446, 200, 273, 200, 273, 400, 273, 400, 273, 600, 273, 600, 273, 800, 273, 800, 273, 1000, 273, 1000, 273, 900, 446, 900, 446, 900, 793, 900, 793, 1000, 966, 1000, 966, 800, 966, 800, 966, 200, 966, 200, 966, 100, 1139, 100, 1139, 900, 1139, 900, 1139, 1000, 966, 1000, 966, 1000, 1312, 1000, 1312, 900, 1486, 900, 1486, 100, 1486, 100, 1486, 200, 1312, 200, 1312, 1000, 1312, 1000, 1312, 900, 1139, 900, 1139, 800, 966, 800, 966, 900, 793, 900, 793, 800, 620, 800, 620, 900, 446, 900, 446, 900, 100, 900, 100, 700, 100, 700, 100, 500, 100, 500, 100, 300, 100, 300, 100, 100, 100, 100, 100, 200, 273, 200, 273, 300, 100, 300, 100, 400, 273, 400, 273, 500, 100, 500, 100, 600, 273, 600, 273, 700, 100, 700, 100, 800, 273, 800, 273, 900, 100, 900, 100, 1000, 273,},
//            new float[]{500, 793, 600, 620, 600, 620, 700, 793, 700, 793, 500, 793, 500, 793, 600, 966, 600, 966, 700, 793, 700, 793, 900, 793, 900, 793, 800, 620, 800, 620, 700, 793, 700, 793, 800, 966, 800, 966, 600, 966, 600, 966, 500, 1139, 500, 1139, 400, 966, 400, 966, 600, 966, 600, 966, 700, 1139, 700, 1139, 500, 1139, 500, 1139, 400, 1312, 400, 1312, 300, 1139, 300, 1139, 400, 966, 400, 966, 300, 793, 300, 793, 500, 793, 500, 793, 400, 966, 400, 966, 200, 966, 200, 966, 100, 793, 100, 793, 300, 793, 300, 793, 400, 620, 400, 620, 200, 620, 200, 620, 300, 446, 300, 446, 400, 620, 400, 620, 500, 446, 500, 446, 600, 620, 600, 620, 400, 620, 400, 620, 500, 793,},
//            new float[]{200, 966, 100, 1139, 100, 1139, 900, 1139, 900, 1139, 800, 966, 800, 966, 200, 966, 200, 966, 300, 793, 300, 793, 700, 793, 700, 793, 600, 620, 600, 620, 400, 620, 400, 620, 500, 446, 500, 446, 600, 620,},
//            new float[]{200, 1312, 600, 1312, 600, 1312, 1000, 1312, 1000, 1312, 1000, 966, 1000, 966, 600, 966, 600, 966, 600, 1312, 600, 1312, 700, 1139, 700, 1139, 600, 966, 600, 966, 500, 793, 500, 793, 500, 446, 500, 446, 900, 446, 900, 446, 900, 793, 900, 793, 700, 793, 700, 793, 800, 620, 800, 620, 600, 620, 600, 620, 700, 793, 700, 793, 500, 793, 500, 793, 200, 1312, 200, 1312, 500, 1139, 500, 1139, 200, 966, 200, 966, 400, 620, 400, 620, 500, 793,},
//            new float[]{800, 273, 900, 446, 900, 446, 1000, 273, 1000, 273, 900, 100, 900, 100, 100, 100, 100, 100, 200, 273, 200, 273, 100, 446, 100, 446, 200, 620, 200, 620, 100, 793, 100, 793, 200, 966, 200, 966, 100, 1139, 100, 1139, 200, 1312, 200, 1312, 100, 1486, 100, 1486, 900, 1486, 900, 1486, 1000, 1312, 1000, 1312, 900, 1139, 900, 1139, 1000, 966, 1000, 966, 900, 793, 900, 793, 1000, 620, 1000, 620, 800, 620, 800, 620, 700, 793, 700, 793, 800, 966, 800, 966, 700, 1139, 700, 1139, 800, 1312, 800, 1312, 400, 1312, 400, 1312, 300, 1139, 300, 1139, 400, 966, 400, 966, 300, 793, 300, 793, 400, 620, 400, 620, 300, 446, 300, 446, 400, 273, 400, 273, 800, 273, 800, 273, 700, 446, 700, 446, 500, 446, 500, 446, 600, 620, 600, 620, 500, 793, 500, 793, 600, 966, 600, 966, 400, 1312,},
//            new float[]{400, 1312, 300, 1139, 300, 1139, 200, 1312, 200, 1312, 400, 1312, 400, 1312, 300, 1486, 300, 1486, 100, 1486, 100, 1486, 100, 1139, 100, 1139, 200, 966, 200, 966, 400, 966, 400, 966, 600, 1312, 600, 1312, 700, 1486, 700, 1486, 900, 1486, 900, 1486, 1000, 1312, 1000, 1312, 800, 1312, 800, 1312, 1000, 966, 1000, 966, 900, 793, 900, 793, 700, 1139, 700, 1139, 600, 966, 600, 966, 700, 793, 700, 793, 500, 793, 500, 793, 400, 620, 400, 620, 300, 446, 300, 446, 200, 620, 200, 620, 300, 793, 300, 793, 100, 793, 100, 793, 100, 100, 100, 100, 700, 100, 700, 100, 800, 273, 800, 273, 200, 273, 200, 273, 500, 446, 500, 446, 600, 620, 600, 620, 700, 446, 700, 446, 800, 620, 800, 620, 1000, 620, 1000, 620, 900, 446, 900, 446, 1000, 273, 1000, 273, 900, 100, 900, 100, 800, 273, 800, 273, 1000, 273,},
//            new float[]{400, 1312, 300, 1139, 300, 1139, 200, 1312, 200, 1312, 400, 1312, 400, 1312, 300, 1486, 300, 1486, 100, 1486, 100, 1486, 100, 1139, 100, 1139, 200, 966, 200, 966, 400, 966, 400, 966, 600, 1312, 600, 1312, 700, 1486, 700, 1486, 900, 1486, 900, 1486, 1000, 1312, 1000, 1312, 800, 1312, 800, 1312, 1000, 966, 1000, 966, 900, 793, 900, 793, 700, 1139, 700, 1139, 600, 966, 600, 966, 700, 793, 700, 793, 500, 793, 500, 793, 400, 620, 400, 620, 300, 446, 300, 446, 200, 620, 200, 620, 300, 793, 300, 793, 100, 793, 100, 793, 100, 100, 100, 100, 700, 100, 700, 100, 800, 273, 800, 273, 200, 273, 200, 273, 500, 446, 500, 446, 600, 620, 600, 620, 700, 446, 700, 446, 800, 620, 800, 620, 1000, 620, 1000, 620, 900, 446, 900, 446, 1000, 273, 1000, 273, 900, 100, 900, 100, 800, 273, 800, 273, 1000, 273,},
//            new float[]{200, 966, 100, 793, 100, 793, 200, 620, 200, 620, 200, 966, 200, 966, 300, 1139, 300, 1139, 300, 446, 300, 446, 400, 273, 400, 273, 400, 1312, 400, 1312, 500, 1486, 500, 1486, 500, 100, 500, 100, 600, 273, 600, 273, 600, 1312, 600, 1312, 700, 1486, 700, 1486, 700, 446, 700, 446, 800, 620, 800, 620, 800, 1312, 800, 1312, 900, 1486, 900, 1486, 900, 793, 900, 793, 1000, 966, 1000, 966, 1000, 1312, 1000, 1312, 900, 1486,},
//            new float[]{300, 793, 500, 1139, 500, 1139, 800, 620, 800, 620, 900, 446,},
//            new float[]{300, 1139, 200, 1312, 200, 1312, 1000, 1312, 1000, 1312, 900, 1139, 900, 1139, 700, 1139, 700, 1139, 700, 100, 700, 100, 300, 446, 300, 446, 500, 446, 500, 446, 500, 1139, 500, 1139, 300, 1139,},
//            new float[]{500, 446, 400, 620, 400, 620, 300, 446, 300, 446, 500, 446, 500, 446, 400, 273, 400, 273, 600, 273, 600, 273, 500, 446, 500, 446, 700, 446, 700, 446, 600, 620, 600, 620, 500, 446, 500, 446, 500, 1139, 500, 1139, 400, 966, 400, 966, 300, 1139, 300, 1139, 500, 1139, 500, 1139, 400, 1312, 400, 1312, 600, 1312, 600, 1312, 500, 1139, 500, 1139, 700, 1139, 700, 1139, 600, 966, 600, 966, 500, 1139, 500, 1139, 800, 966, 800, 966, 700, 793, 700, 793, 800, 620, 800, 620, 900, 793, 900, 793, 800, 966, 800, 966, 800, 620, 800, 620, 500, 446, 500, 446, 200, 620, 200, 620, 200, 966, 200, 966, 100, 793, 100, 793, 200, 620, 200, 620, 300, 793, 300, 793, 200, 966, 200, 966, 500, 1139,},
//            new float[]{600, 273, 500, 446, 500, 446, 400, 273, 400, 273, 600, 273, 600, 273, 800, 273, 800, 273, 700, 446, 700, 446, 600, 273, 600, 273, 600, 620, 600, 620, 400, 620, 400, 620, 500, 446, 500, 446, 600, 620, 600, 620, 800, 620, 800, 620, 700, 446, 700, 446, 600, 620, 600, 620, 500, 793, 500, 793, 400, 620, 400, 620, 400, 966, 400, 966, 500, 793, 500, 793, 600, 966, 600, 966, 700, 793, 700, 793, 800, 620, 800, 620, 800, 966, 800, 966, 700, 793, 700, 793, 600, 620, 600, 620, 600, 966, 600, 966, 500, 1139, 500, 1139, 400, 966, 400, 966, 600, 966, 600, 966, 700, 1139, 700, 1139, 800, 966, 800, 966, 600, 966, 600, 966, 600, 1312, 600, 1312, 500, 1139, 500, 1139, 400, 1312, 400, 1312, 600, 1312, 600, 1312, 800, 1312, 800, 1312, 700, 1139, 700, 1139, 600, 1312,},
//            new float[]{200, 273, 800, 273, 800, 273, 700, 446, 700, 446, 600, 620, 600, 620, 500, 793, 500, 793, 200, 1312, 200, 1312, 800, 1312, 800, 1312, 500, 793, 500, 793, 400, 620, 400, 620, 300, 446, 300, 446, 200, 273,},
//            new float[]{800, 966, 900, 793, 900, 793, 700, 793, 700, 793, 800, 966, 800, 966, 600, 966, 600, 966, 700, 793, 700, 793, 800, 620, 800, 620, 600, 620, 600, 620, 700, 793, 700, 793, 500, 793, 500, 793, 600, 620, 600, 620, 700, 446, 700, 446, 500, 446, 500, 446, 600, 620, 600, 620, 400, 620, 400, 620, 500, 446, 500, 446, 600, 273, 600, 273, 400, 273, 400, 273, 300, 446, 300, 446, 500, 446, 500, 446, 400, 273, 400, 273, 200, 273, 200, 273, 300, 446, 300, 446, 200, 620, 200, 620, 400, 620, 400, 620, 300, 793, 300, 793, 500, 793, 500, 793, 400, 966, 400, 966, 600, 966, 600, 966, 500, 1139, 500, 1139, 700, 1139, 700, 1139, 800, 966, 800, 966, 900, 1139, 900, 1139, 700, 1139, 700, 1139, 800, 1312, 800, 1312, 900, 1139,},
//            new float[]{400, 273, 300, 100, 300, 100, 200, 273, 200, 273, 400, 273, 400, 273, 500, 100, 500, 100, 600, 273, 600, 273, 400, 273, 400, 273, 300, 446, 300, 446, 500, 446, 500, 446, 400, 273, 400, 273, 700, 446, 700, 446, 800, 620, 800, 620, 900, 446, 900, 446, 700, 446, 700, 446, 800, 273, 800, 273, 600, 273, 600, 273, 700, 446, 700, 446, 500, 446, 500, 446, 600, 620, 600, 620, 700, 446, 700, 446, 700, 793, 700, 793, 800, 620, 800, 620, 900, 793, 900, 793, 700, 793, 700, 793, 800, 966, 800, 966, 600, 966, 600, 966, 700, 793, 700, 793, 500, 793, 500, 793, 600, 620, 600, 620, 700, 793, 700, 793, 400, 966, 400, 966, 600, 966, 600, 966, 500, 1139, 500, 1139, 400, 966, 400, 966, 300, 1139, 300, 1139, 200, 966, 200, 966, 400, 966, 400, 966, 300, 793, 300, 793, 500, 793, 500, 793, 400, 966, 400, 966, 400, 1312, 400, 1312, 200, 1312, 200, 1312, 300, 1139, 300, 1139, 400, 1312, 400, 1312, 300, 1486, 300, 1486, 500, 1486, 500, 1486, 400, 1312, 400, 1312, 600, 1312, 600, 1312, 500, 1139, 500, 1139, 400, 1312,},
//            new float[]{300, 1139, 500, 1139, 500, 1139, 700, 1139, 700, 1139, 900, 1139, 900, 1139, 800, 966, 800, 966, 600, 966, 600, 966, 700, 1139, 700, 1139, 800, 966, 800, 966, 900, 793, 900, 793, 700, 793, 700, 793, 500, 793, 500, 793, 600, 966, 600, 966, 400, 966, 400, 966, 500, 1139, 500, 1139, 400, 1312, 400, 1312, 300, 1139, 300, 1139, 400, 966, 400, 966, 300, 793, 300, 793, 500, 793, 500, 793, 600, 620, 600, 620, 700, 446, 700, 446, 800, 273, 800, 273, 400, 273, 400, 273, 500, 446, 500, 446, 600, 620, 600, 620, 800, 620, 800, 620, 700, 446, 700, 446, 500, 446, 500, 446, 400, 620, 400, 620, 600, 620, 600, 620, 700, 793, 700, 793, 800, 966,},
//            new float[]{200, 620, 500, 100, 500, 100, 800, 620, 800, 620, 200, 620, 200, 620, 500, 1139, 500, 1139, 800, 620, 800, 620, 900, 793, 900, 793, 500, 1486, 500, 1486, 100, 793, 100, 793, 200, 620, 200, 620, 500, 446, 500, 446, 800, 620, 800, 620, 500, 793, 500, 793, 200, 620, 200, 620, 100, 446, 100, 446, 300, 100, 300, 100, 500, 100, 500, 100, 700, 100, 700, 100, 900, 446, 900, 446, 800, 620,},
//            new float[]{500, 1139, 300, 1139, 300, 1139, 300, 446, 300, 446, 700, 446, 700, 446, 700, 1139, 700, 1139, 500, 1139, 500, 1139, 400, 966, 400, 966, 600, 966, 600, 966, 500, 793, 500, 793, 400, 620, 400, 620, 600, 620, 600, 620, 500, 793, 500, 793, 400, 966, 400, 966, 300, 1139, 300, 1139, 400, 1312, 400, 1312, 500, 1139, 500, 1139, 600, 966, 600, 966, 700, 1139, 700, 1139, 600, 1312, 600, 1312, 500, 1139,},
//            new float[]{800, 620, 600, 620, 600, 620, 400, 620, 400, 620, 200, 620, 200, 620, 100, 793, 100, 793, 300, 793, 300, 793, 500, 793, 500, 793, 700, 793, 700, 793, 900, 793, 900, 793, 1000, 620, 1000, 620, 900, 446, 900, 446, 800, 620, 800, 620, 900, 793, 900, 793, 800, 966, 800, 966, 600, 966, 600, 966, 400, 966, 400, 966, 200, 966, 200, 966, 300, 793, 300, 793, 200, 620, 200, 620, 300, 446, 300, 446, 400, 620, 400, 620, 500, 793, 500, 793, 600, 966, 600, 966, 700, 793, 700, 793, 600, 620, 600, 620, 500, 446, 500, 446, 700, 446, 700, 446, 800, 620, 800, 620, 700, 793, 700, 793, 800, 966, 800, 966, 600, 1312, 600, 1312, 400, 966, 400, 966, 300, 1139, 300, 1139, 200, 966,},
//    };
//
//    private final float[] SQUARE_POINTS = new float[]{
//            100, 100, 300, 100, 500, 100, 700, 100, 900, 100,
//            100, 300, 300, 300, 500, 300, 700, 300, 900, 300,
//            100, 500, 300, 500, 500, 500, 700, 500, 900, 500,
//            100, 700, 300, 700, 500, 700, 700, 700, 900, 700,
//            100, 900, 300, 900, 500, 900, 700, 900, 900, 900,
//            100, 1100, 300, 1100, 500, 1100, 700, 1100, 900, 1100,
//            100, 1300, 300, 1300, 500, 1300, 700, 1300, 900, 1300,
//            100, 1500, 300, 1500, 500, 1500, 700, 1500, 900, 1500,
//            100, 1700, 300, 1700, 500, 1700, 700, 1700, 900, 1700};
//
//    private final float[] ISOMETRIC_POINTS = new float[]{
//            100, 100, 300, 100, 500, 100, 700, 100, 900, 100,
//            200, 273, 400, 273, 600, 273, 800, 273, 1000, 273,
//            100, 446, 300, 446, 500, 446, 700, 446, 900, 446,
//            200, 620, 400, 620, 600, 620, 800, 620, 1000, 620,
//            100, 793, 300, 793, 500, 793, 700, 793, 900, 793,
//            200, 966, 400, 966, 600, 966, 800, 966, 1000, 966,
//            100, 1139, 300, 1139, 500, 1139, 700, 1139, 900, 1139,
//            200, 1312, 400, 1312, 600, 1312, 800, 1312, 1000, 1312,
//            100, 1486, 300, 1486, 500, 1486, 700, 1486, 900, 1486,
//            200, 1659, 400, 1659, 600, 1659, 800, 1659, 1000, 1659,};
//
//    public ArrayList<Line> getLevelAsLines(int level, int width, int height, int topOffset) {
//        float[] centeredLines = centerPoints(LEVELS[level], width, height, topOffset);
//
//        ArrayList<Line> lines = new ArrayList<>(centeredLines.length / 4);
//        for (int i = 0; i < centeredLines.length; i += 4)
//            lines.add(new Line(centeredLines[i], centeredLines[i + 1], centeredLines[i + 2], centeredLines[i + 3]));
//        return lines;
//    }
//
//    public ArrayList<Point> getGridAsPoints(TraceBuilder.Mode mode, int width, int height, int topOffset) {
//        float[] centeredPoints = centerPoints(getGridByMode(mode), width, height, topOffset);
//
//        ArrayList<Point> points = new ArrayList<>(centeredPoints.length / 2);
//        for (int i = 0; i < centeredPoints.length; i += 2)
//            points.add(new Point(centeredPoints[i], centeredPoints[i + 1]));
//        return points;
//    }
//
//    private static float[] getGridByMode(TraceBuilder.Mode mode) {
//        return (mode == TraceBuilder.Mode.ISOMETRIC) ? ISOMETRIC_POINTS : SQUARE_POINTS;
//    }
//
//    private static float[] centerPoints(float[] points, int width, int height, int topOffset) {
//        float left = width;
//        float top = height;
//        float right = 0;
//        float bot = 0;
//
//        for (int i = 0; i < points.length; i += 2) {
//            left = (points[i] < left) ? points[i] : left;
//            right = (points[i] > right) ? points[i] : right;
//            top = (points[i + 1] < top) ? points[i + 1] : top;
//            bot = (points[i + 1] > bot) ? points[i + 1] : bot;
//        }
//
//        float x = (width - right - left) / 2;
//        float y = (height - topOffset - top - bot) / 2;
//
//        float[] centeredPoints = new float[points.length];
//        for (int i = 0; i < points.length; i += 2) {
//            centeredPoints[i] = points[i] + x;
//            centeredPoints[i + 1] = points[i + 1] + y;
//        }
//        return centeredPoints;
//    }
}