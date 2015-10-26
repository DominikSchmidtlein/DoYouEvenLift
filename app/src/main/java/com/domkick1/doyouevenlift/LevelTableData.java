package com.domkick1.doyouevenlift;

import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by dominikschmidtlein on 10/26/2015.
 */
public class LevelTableData {

    public LevelTableData(){

    }

    public static abstract class LevelTableInfo implements BaseColumns{

        public static final String TABLE_NAME = "level_table";

        public static final String COLUMN_ID = "level_id";
        public static final String COLUMN_DIFFICULTY = "level_difficulty";
    }
}
