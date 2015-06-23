package com.myapp.pavilion.udacityoverview;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.myapp.pavilion.udacityoverview.UdacityContract.CourseEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

/**
 * Created by Pavilion on 11-04-2015.
 */
public class FetchCourseTask extends AsyncTask<Void,Void,Void> {
    private static final String LOG_TAG =FetchCourseTask.class.getSimpleName() ;
    Context mcontext;

int x=0;
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(x==1)
        {


        Toast.makeText(mcontext,"Data Refreshed",Toast.LENGTH_SHORT).show();
    }
    }


    public FetchCourseTask(Context c)
{
    mcontext=c;

}

      private void getCourseDataFromJson(String course)throws JSONException {


        // Now we have a String representing the complete data in JSON Format.
        // Fortunately parsing is easy:  constructor takes the JSON string and converts it
        // into an Object hierarchy for us.

        // These are the names of the JSON objects that need to be extracted.
        final String TITLE = "title";
        final String KEY = "key";
        final String SUMMARY = "short_summary";

        final String HOMEPAGE="homepage";
        final String VIDEO = "teaser_video";
        final String VIDEO_URL = "youtube_url";
        final String LEVEL = "level";




        final String COURSES = "courses";


        try {

            JSONObject courseJson = new JSONObject(course);
            JSONArray coursesArray = courseJson.getJSONArray(COURSES);
            Vector<ContentValues> cVVector = new Vector<ContentValues>(coursesArray.length());
            for (int i = 0; i < coursesArray.length(); i++) {

                JSONObject obj = coursesArray.getJSONObject(i);
                String title = obj.getString(TITLE);

                String key = obj.getString(KEY);
                String home=obj.getString(HOMEPAGE);
                String summary = obj.getString(SUMMARY);
                String level = obj.getString(LEVEL);
                JSONObject video_object = obj.getJSONObject(VIDEO);
                String video_url = video_object.getString(VIDEO_URL);
                ContentValues values = new ContentValues();
                values.put(CourseEntry.COLUMN_TITLE, title);
                values.put(CourseEntry.COLUMN_LEVEL, level);
                values.put(CourseEntry.COLUMN_SHORT_SUMMARY, summary);
                values.put(CourseEntry.COLUMN_VIDEO, video_url);
                values.put(CourseEntry.COLUMN_KEY,key);
                values.put(CourseEntry.COLUMN_HOMEPAGE,home);
                cVVector.add(values);

            }
            int inserted = 0;
            // add to database
            if (cVVector.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                inserted = mcontext.getContentResolver().bulkInsert(CourseEntry.CONTENT_URI, cvArray);
            }

            Log.d(LOG_TAG, "FetchCourseTask Complete. " + inserted + " Inserted");
            x=0;
            x++;






        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }


    }

    // New data is back from the server.  Hooray!



    @Override
    protected Void doInBackground(Void... params) {




        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;



        try {
            // Construct the URL for the Udacity query

            final String URL =
                    "https://www.udacity.com/public-api/v0/courses";



            Uri builtUri = Uri.parse(URL);

            URL url = new URL(builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
            Log.e(LOG_TAG, forecastJsonStr);
            getCourseDataFromJson(forecastJsonStr);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return null;
    }
}

