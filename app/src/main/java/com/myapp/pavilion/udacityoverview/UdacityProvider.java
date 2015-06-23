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


     //URI MATCHER MATCHES THE URI
    static UriMatcher buildUriMatcher() {
        // 1) The code passed into the constructor represents the code to return
        // for the root
        // URI. It's common to use NO_MATCH as the code for this case. Add the
        // constructor below.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = UdacityContract.CONTENT_AUTHORITY;

        // 2) Use the addURI function to match each of the types. Use the
        // constants from
        // UdacityContract
        matcher.addURI(authority, UdacityContract.PATH_COURSES, COURSES);
        matcher.addURI(authority, UdacityContract.PATH_COURSES+ "/*",
                COURSE_WITH_KEY);

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

        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {


            case COURSES: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        UdacityContract.CourseEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            }
            // "location"
            case COURSE_WITH_KEY: {
                retCursor = getCourseWithKey(uri,projection,sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }
    private  Cursor getCourseWithKey(Uri uri , String[] projection , String sortOrder ) {
        String[] selectionArgs;
        String selection;
String key= UdacityContract.getKeyFromUri(uri);

        final SQLiteDatabase db=mOpenHelper.getReadableDatabase();
            selection = sKeySelection;
            selectionArgs = new String[] { key };




        return db.query(UdacityContract.CourseEntry.TABLE_NAME,null,sKeySelection,selectionArgs,null,null,sortOrder);


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
