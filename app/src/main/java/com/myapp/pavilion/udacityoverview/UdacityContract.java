package com.myapp.pavilion.udacityoverview;

/**
 * Created by Pavilion on 11-04-2015.
 */

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Defines table and column names for the weather database.
 */
public class UdacityContract {
    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.myapp.pavilion.udacityoverview";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_COURSES = "courses";
    public static final String PATH_LOCATION = "location";
    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    };

    /*
        Inner class that defines the table contents of the location table
        Students: This is where you will add the strings.  (Similar to what has been
        done for WeatherEntry)
     */
    public static final class TrackEntry implements BaseColumns {
        public static final String TABLE_NAME = "tracks";
        public static final String COLUMN_NAME = "name";

    /*    public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATION).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
    }*/
    }

    /* Inner class that defines the table contents of the weather table */
    public static final class CourseEntry implements BaseColumns {

        public static final String TABLE_NAME = "courses";



        // Date, stored as long in milliseconds since the epoch
        public static final String COLUMN_TITLE = "title";
        // Weather id as returned by API, to identify the icon to be used


        // Short description and long description of the weather, as provided by API.
        // e.g "clear" vs "sky is clear".
        public static final String COLUMN_LEVEL = "level";

        // Min and max temperatures for the day (stored as floats)
        public static final String COLUMN_KEY = "key";
        public static final String COLUMN_SHORT_SUMMARY = "summary";

        // Humidity is stored as a float representing percentage
        public static final String COLUMN_VIDEO = "youtube_video";
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSES;
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COURSES).build();

        public static Uri buildCourseUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }




        public static Uri buildCourseKeyUri(String key) {
            return CONTENT_URI.buildUpon().appendPath(key).build();

        }

    }









       /* public static Uri buildWeatherLocationWithStartDate(
                String locationSetting, long startDate) {
            long normalizedDate = normalizeDate(startDate);
            return CONTENT_URI.buildUpon().appendPath(locationSetting)
                    .appendQueryParameter(COLUMN_DATE, Long.toString(normalizedDate)).build();
        }

        public static Uri buildWeatherLocationWithDate(String locationSetting, long date) {
            return CONTENT_URI.buildUpon().appendPath(locationSetting)
                    .appendPath(Long.toString(normalizeDate(date))).build();
        }*/

        public static String getKeyFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }




    }


