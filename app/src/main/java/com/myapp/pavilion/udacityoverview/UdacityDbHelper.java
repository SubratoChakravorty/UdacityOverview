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
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude



        final String SQL_CREATE_COURSE_TABLE = "CREATE TABLE " + CourseEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                CourseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                // the ID of the location entry associated with this weather data


                CourseEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_LEVEL + " TEXT NOT NULL, " +

                CourseEntry.COLUMN_SHORT_SUMMARY + " TEXT NOT NULL, " +
                CourseEntry.COLUMN_KEY + " TEXT NOT NULL, " +

                CourseEntry.COLUMN_VIDEO+ " TEXT NOT NULL " +




                // Set up the location column as a foreign key to location table.

                // To assure the application have just one weather entry per day
                // per location, it's created a UNIQUE constraint with REPLACE strategy

                " );";


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