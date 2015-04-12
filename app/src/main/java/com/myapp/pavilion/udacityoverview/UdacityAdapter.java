package com.myapp.pavilion.udacityoverview;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Pavilion on 12-04-2015.
 */
public class UdacityAdapter extends CursorAdapter{

        public UdacityAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        /**
         * Prepare the weather high/lows for presentation.
         */

        /*
            This is ported from FetchWeatherTask --- but now we go straight from the cursor to the
            string.
         */
        private String convertCursorRowToUXFormat(Cursor cursor) {
            // get row indices for our cursor
            int id_title = cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_KEY);

            String highAndLow = cursor.getString(id_title);

            return highAndLow;

        }

        /*
            Remember that these views are reused as needed.
         */
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_courses, parent, false);

            return view;
        }

        /*
            This is where we fill-in the views with the contents of the cursor.
         */
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // our view is pretty simple here --- just a text view
            // we'll keep the UI functional with a simple (and slow!) binding.

            TextView tv = (TextView)view;
            tv.setText(convertCursorRowToUXFormat(cursor));
        }
    }

