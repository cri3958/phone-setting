package com.example.kalyan.timetable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//시간 증가 그러나 적용은 아직..

public class Helper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "timetable.db";

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREAT_TABLE = "CREATE TABLE " + Contract.Entry.TABLE_NAME + "("
                + Contract.Entry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                Contract.Entry.COLUMN_DAY + " TEXT ," +
                Contract.Entry.COLUMN_0to1 + " TEXT ," +
                Contract.Entry.COLUMN_1to2 + " TEXT ," +
                Contract.Entry.COLUMN_2to3 + " TEXT ," +
                Contract.Entry.COLUMN_3to4 + " TEXT ," +
                Contract.Entry.COLUMN_4to5 + " TEXT ," +
                Contract.Entry.COLUMN_5to6 + " TEXT ," +
                Contract.Entry.COLUMN_6to7 + " TEXT ," +
                Contract.Entry.COLUMN_7to8 + " TEXT ," +
                Contract.Entry.COLUMN_8to9 + " TEXT ," +
                Contract.Entry.COLUMN_9to10 + " TEXT ," +
                Contract.Entry.COLUMN_10to11 + " TEXT ," +
                Contract.Entry.COLUMN_11to12 + " TEXT ," +
                Contract.Entry.COLUMN_12to13 + " TEXT ," +
                Contract.Entry.COLUMN_13to14 + " TEXT ," +
                Contract.Entry.COLUMN_14to15 + " TEXT ," +
                Contract.Entry.COLUMN_15to16 + " TEXT ," +
                Contract.Entry.COLUMN_16to17 + " TEXT ," +
                Contract.Entry.COLUMN_17to18 + " TEXT ," +
                Contract.Entry.COLUMN_18to19 + " TEXT ," +
                Contract.Entry.COLUMN_19to20 + " TEXT ," +
                Contract.Entry.COLUMN_20to21 + " TEXT ," +
                Contract.Entry.COLUMN_21to22 + " TEXT ," +
                Contract.Entry.COLUMN_22to23 + " TEXT ," +
                Contract.Entry.COLUMN_23to24 + " TEXT " +");";

        db.execSQL(SQL_CREAT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
