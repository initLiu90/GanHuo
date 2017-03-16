package com.example.lzp.ganhuo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lzp.ganhuo.data.today.TodayPersistenceContract;

/**
 * Created by lzp on 2017/3/14.
 */

public class GHDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "ganhuo.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TodayPersistenceContract.TodayEntry.TABLE_NAME + " (" +
                    TodayPersistenceContract.TodayEntry._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    TodayPersistenceContract.TodayEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    TodayPersistenceContract.TodayEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    TodayPersistenceContract.TodayEntry.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                    TodayPersistenceContract.TodayEntry.COLUMN_NAME_AUTHER + TEXT_TYPE + COMMA_SEP +
                    TodayPersistenceContract.TodayEntry.COLUMN_NAME_IMAGEURL + TEXT_TYPE + COMMA_SEP +
                    TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE + TEXT_TYPE +
                    " )";

    public GHDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TodayPersistenceContract.TodayEntry.TABLE_NAME);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
