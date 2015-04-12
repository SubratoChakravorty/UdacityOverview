package com.myapp.pavilion.udacityoverview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myapp.pavilion.udacityoverview.UdacityContract.CourseEntry;

/**
 * Created by Pavilion on 11-04-2015.
 */
public class UdacityDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "udacity.db";

    public UdacityDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


//Table name

        final String SQL_CREATE_COURSE_TABLE = "CREATE TABLE " + CourseEntry.TABLE_NAME + " (" +
                

                // should be sorted accordingly.
              //column name in the table

                CourseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                // the ID of the location entry associated with this weather data


                CourseEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_LEVEL + " TEXT NOT NULL, " +

                CourseEntry.COLUMN_SHORT_SUMMARY + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_KEY + " TEXT NOT NULL, " +

                CourseEntry.COLUMN_VIDEO+ " TEXT NOT NULL, " +" UNIQUE ("+CourseEntry.COLUMN_KEY+") ON CONFLICT REPLACE"+  " );";









        sqLiteDatabase.execSQL(SQL_CREATE_COURSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CourseEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}