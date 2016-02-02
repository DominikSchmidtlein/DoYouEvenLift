package com.domkick1.trace;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.domkick1.trace.LevelTableData.LevelTableInfo;

/**
 * Created by dominikschmidtlein on 10/26/2015.
 */
public class LevelDatabaseOperations extends SQLiteOpenHelper {
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Levels.db";

    private static final String CREATE_ENTRIES = "CREATE_TABLE " + LevelTableInfo.TABLE_NAME +
            " (" + LevelTableInfo.COLUMN_ID + " INTEGER PRIMARY KEY," +
            LevelTableInfo.COLUMN_DIFFICULTY + INTEGER_TYPE + " )";

    private static final String DELETE_ENTRIES = "DROP TABLE IF EXISTS " + LevelTableInfo.TABLE_NAME;


    public LevelDatabaseOperations(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database OPS", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRIES);
        Log.d("Database OPS", "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_ENTRIES);
        onCreate(db);
    }
}
