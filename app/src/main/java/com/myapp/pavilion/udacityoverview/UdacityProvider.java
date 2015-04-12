package com.myapp.pavilion.udacityoverview;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Pavilion on 12-04-2015.
 */
public class UdacityProvider extends ContentProvider{

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private UdacityDbHelper mOpenHelper;

    static final int COURSES = 100;
    static final int COURSE_WITH_KEY = 101;



    // location.location_setting = ?
    private static final String sKeySelection = UdacityContract.CourseEntry.TABLE_NAME
            + "."
            + UdacityContract.CourseEntry.COLUMN_KEY
            + " = ? ";

    // location.location_setting = ? AND date >= ?
 /*   private static final String sLocationSettingWithStartDateSelection = WeatherContract.LocationEntry.TABLE_NAME
            + "."
            + WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING
            + " = ? AND " + WeatherContract.WeatherEntry.COLUMN_DATE + " >= ? ";

    // location.location_setting = ? AND date = ?
    private static final String sLocationSettingAndDaySelection = WeatherContract.LocationEntry.TABLE_NAME
            + "."
            + WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING
            + " = ? AND " + WeatherContract.WeatherEntry.COLUMN_DATE + " = ? ";*/

  /*  private Cursor getWeatherByLocationSetting(Uri uri, String[] projection,
                                               String sortOrder) {
        String locationSetting = WeatherContract.WeatherEntry
                .getLocationSettingFromUri(uri);
        long startDate = WeatherContract.WeatherEntry.getStartDateFromUri(uri);

        String[] selectionArgs;
        String selection;

        if (startDate == 0) {
            selection = sLocationSettingSelection;
            selectionArgs = new String[] { locationSetting };
        } else {
            selectionArgs = new String[] { locationSetting,
                    Long.toString(startDate) };
            selection = sLocationSettingWithStartDateSelection;
        }

        return sWeatherByLocationSettingQueryBuilder.query(
                mOpenHelper.getReadableDatabase(), projection, selection,
                selectionArgs, null, null, sortOrder);
    }

    private Cursor getWeatherByLocationSettingAndDate(Uri uri,
                                                      String[] projection, String sortOrder) {
        String locationSetting = WeatherContract.WeatherEntry
                .getLocationSettingFromUri(uri);
        long date = WeatherContract.WeatherEntry.getDateFromUri(uri);

        return sWeatherByLocationSettingQueryBuilder.query(
                mOpenHelper.getReadableDatabase(), projection,
                sLocationSettingAndDaySelection, new String[] {
                        locationSetting, Long.toString(date) }, null, null,
                sortOrder);
    }*/

    /*
     * Students: Here is where you need to create the UriMatcher. This
     * UriMatcher will match each URI to the WEATHER, WEATHER_WITH_LOCATION,
     * WEATHER_WITH_LOCATION_AND_DATE, and LOCATION integer constants defined
     * above. You can test this by uncommenting the testUriMatcher test within
     * TestUriMatcher.
     */
    static UriMatcher buildUriMatcher() {
        // 1) The code passed into the constructor represents the code to return
        // for the root
        // URI. It's common to use NO_MATCH as the code for this case. Add the
        // constructor below.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = UdacityContract.CONTENT_AUTHORITY;

        // 2) Use the addURI function to match each of the types. Use the
        // constants from
        // WeatherContract to help define the types to the UriMatcher.
        matcher.addURI(authority, UdacityContract.PATH_COURSES, COURSES);
        matcher.addURI(authority, UdacityContract.PATH_COURSES+ "/*",
                COURSE_WITH_KEY);
       ///matcher.addURI(authority, WeatherContract.PATH_WEATHER + "/*/#",
              //  WEATHER_WITH_LOCATION_AND_DATE);
       // matcher.addURI(authority, WeatherContract.PATH_LOCATION, LOCATION);

        // 3) Return the new matcher!
        return matcher;
    }

    /*
     * Students: We've coded this for you. We just create a new WeatherDbHelper
     * for later use here.
     */
    @Override
    public boolean onCreate() {
        mOpenHelper = new UdacityDbHelper(getContext());
        return true;
    }

    /*
     * Students: Here's where you'll code the getType function that uses the
     * UriMatcher. You can test this by uncommenting testGetType in
     * TestProvider.
     */
    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case COURSES:
                return UdacityContract.CourseEntry.CONTENT_TYPE;
            case COURSE_WITH_KEY:
                return UdacityContract.CourseEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what
        // kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            // "weather"
            case COURSES: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        UdacityContract.CourseEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            }
            // "location"
            case COURSE_WITH_KEY: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        UdacityContract.CourseEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    /*
     * Student: Add the ability to insert Locations to the implementation of
     * this function.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri ;

        switch (match) {
            case COURSES: {

                long _id = db.insert(UdacityContract.CourseEntry.TABLE_NAME, null,
                        values);
                if (_id > 0)
                    returnUri = UdacityContract.CourseEntry.buildCourseUri(_id);
                else
                    throw new android.database.SQLException(
                            "Failed to insert row into " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db=mOpenHelper.getReadableDatabase();
        final int match=sUriMatcher.match(uri);
        int rowsDeleted;
        // Student: Start by getting a writable database

        // Student: Use the uriMatcher to match the WEATHER and LOCATION URI's
        // we are going to
        // handle. If it doesn't match these, throw an
        // UnsupportedOperationException.
        if(selection==null)selection="1";
        switch (match)
        {
            case COURSES:
            {
                rowsDeleted=db.delete(UdacityContract.CourseEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("unknown uri :"+uri);
        }
        if(rowsDeleted!=0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return rowsDeleted;


        // Student: A null value deletes all rows. In my implementation of this,
        // I only notified
        // the uri listeners (using the content resolver) if the rowsDeleted !=
        // 0 or the selection
        // is null.
        // Oh, and you should notify the listeners here.

        // Student: return the actual rows deleted

    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // Student: This is a lot like the delete function. We return the number
        // of rows impacted
        // by the update.
        return 0;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case COURSES:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(
                                UdacityContract.CourseEntry.TABLE_NAME, null,
                                value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    // You do not need to call this method. This is a method specifically to
    // assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
