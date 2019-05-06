package com.example.kalyan.timetable;

import android.net.Uri;
import android.provider.BaseColumns;

//시간증가

public class Contract {

    private Contract() {
    }

    /* Inner class that defines the table contents */
    public static class Entry implements BaseColumns {/*here we are writing the single table becoz
    there is only one table i.e pets table  so.... */

        public static final String AUTHORITY = "com.example.kalyan.timetable";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
        public static final String PATH = "chartDB";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH)
                .build();


        public static final String TABLE_NAME = "chart";

        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_DAY = "day";
        public static final String COLUMN_0to1 = "zerotoone";
        public static final String COLUMN_1to2 = "onetotwo";
        public static final String COLUMN_2to3 = "twotothree";
        public static final String COLUMN_3to4 = "threetofour";
        public static final String COLUMN_4to5 = "fourtofive";
        public static final String COLUMN_5to6 = "fivetosix";
        public static final String COLUMN_6to7 = "sixtoseven";
        public static final String COLUMN_7to8 = "seventoeight";
        public static final String COLUMN_8to9 = "eighttonine";
        public static final String COLUMN_9to10 = "ninetoten";
        public static final String COLUMN_10to11 = "tentoeleven";
        public static final String COLUMN_11to12 = "eleventotwelve";
        public static final String COLUMN_12to13 = "twelvetothirteen";
        public static final String COLUMN_13to14 = "thirteentofourteen";
        public static final String COLUMN_14to15 = "fourteentofifteen";
        public static final String COLUMN_15to16 = "fifteentosixteen";
        public static final String COLUMN_16to17 = "sixteentoseventeen";
        public static final String COLUMN_17to18 = "seventeentoeighteen";
        public static final String COLUMN_18to19 = "eighteentonineteen";
        public static final String COLUMN_19to20 = "nineteentotwenty";
        public static final String COLUMN_20to21 = "twentytotwentyone";
        public static final String COLUMN_21to22 = "twentyonetotwentytwo";
        public static final String COLUMN_22to23 = "twentytwototwentythree";
        public static final String COLUMN_23to24 = "twentythreetotwentyfour";
    }
}
