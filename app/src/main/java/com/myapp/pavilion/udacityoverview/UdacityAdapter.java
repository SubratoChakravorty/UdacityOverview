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
private static int i;
        public UdacityAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }
    public static class ViewHolder {

        public final TextView titleView;
        public final TextView levelView;
        public final TextView tvCount;



        public ViewHolder(View view) {

            titleView = (TextView) view.findViewById(R.id.list_item_title);
           levelView = (TextView) view.findViewById(R.id.list_item_level);
            tvCount=(TextView)view.findViewById(R.id.tvcount);


        }

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
            ViewHolder viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
i=1;
            return view;
        }

        /*
            This is where we fill-in the views with the contents of the cursor.
         */
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // our view is pretty simple here --- just a text view
            // we'll keep the UI functional with a simple (and slow!) binding.
            ViewHolder viewHolder=(ViewHolder)view.getTag();
            int id_title=cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_TITLE);
            int id_key = cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_KEY);

i++;
            viewHolder.titleView.setText(cursor.getString(id_title));
            viewHolder.levelView.setText(cursor.getString(id_key));



        }
    }

