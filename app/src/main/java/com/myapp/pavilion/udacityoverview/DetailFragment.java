package com.myapp.pavilion.udacityoverview;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Pavilion on 12-04-2015.
 */

public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    final int DETAIL_LOADER_ID=1;
    static final String DETAIL_URI = "URI";
    TextView tv_title;
    TextView tv_key;
    TextView tv_level;
    TextView tv_summary;
    TextView tv_youtube;
    TextView tv_you;
    TextView tv_homeurl;
    Uri muri;
    String mDetail;
    private ShareActionProvider mShareActionProvider;
    String DETAIL_SHARE_HASHTAG="#UdaCityOverView";
    String DETAIL_API_HASHTAG="#Udacity API";
    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        tv_title=(TextView)rootView.findViewById(R.id.tv_title);
tv_you=(TextView)rootView.findViewById(R.id.tv_youtube);
        tv_key=(TextView)rootView.findViewById(R.id.tv_key);
        tv_level=(TextView)rootView.findViewById(R.id.tv_level);
        tv_summary=(TextView)rootView.findViewById(R.id.tv_summary);
        tv_youtube=(TextView)rootView.findViewById(R.id.tv_youtube_url);
        tv_homeurl=(TextView)rootView.findViewById(R.id.tv_homepage_url);


        Bundle arguments = getArguments();

        if (arguments != null) {
            muri = arguments.getParcelable(DetailFragment.DETAIL_URI);
            //Toast.makeText(getActivity(), "detail fragment" + muri.toString(), Toast.LENGTH_SHORT).show();
        }
       else
        {
            Toast.makeText(getActivity(),"argument null",Toast.LENGTH_SHORT).show();
        }
        getLoaderManager().restartLoader(DETAIL_LOADER_ID, null, this);






        // }
           /* catch(Exception e)
            {
                e.printStackTrace();
            }*/

        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.fragment_detail, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // If onLoadFinished happens before this, we can go ahead and set the share intent now.
        if (mDetail != null) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        }
    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mDetail + DETAIL_SHARE_HASHTAG);
        return shareIntent;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.v(LOG_TAG, "In onCreateLoader");
        Intent intent = getActivity().getIntent();
        if (intent == null || intent.getData() == null) {
            return null;
        }

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        if ( null != muri ) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(getActivity(),muri,null,null,null,null);
        }
        return null;

    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        int id_title = cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_TITLE);
        int id_key = cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_KEY);
        int id_summary= cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_SHORT_SUMMARY);
        int id_youtube = cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_VIDEO);
        int id_level = cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_LEVEL);
int id_home=cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_HOMEPAGE);

        Log.e("id_homepage",id_home+"");
        Log.e("column_count",cursor.getColumnCount()+"");
        // try {
        cursor.moveToFirst();
        String highAndLow = cursor.getString(id_title);
        String level=cursor.getString(id_level);
        String key=cursor.getString(id_key);
        String summary=cursor.getString(id_summary);
        final String y=cursor.getString(id_youtube);
        final String home_url=cursor.getString(id_home);
        tv_title.setTextSize(20);
        //Toast.makeText(getActivity(),y,Toast.LENGTH_LONG).show();
        Log.e("title", highAndLow);
        tv_title.setText(highAndLow);
        tv_homeurl.setText(home_url);
        tv_homeurl.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        tv_homeurl.setTextColor(Color.RED);
       if(y.contentEquals("")) {

           tv_youtube.setText("Not Available");

       }
        else
       {
           tv_youtube.setText(y);
           tv_youtube.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

       }
        tv_summary.setText(summary);
        tv_level.setText(level);
        tv_key.setText(key);


        tv_you.setText("YOUTUBE TEASER :");
        if(!y.contentEquals(""))
        {
            tv_youtube.setTextColor(Color.RED);
            tv_youtube.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(y)); // Starts Implicit Activity
                    startActivity(i);

                }
            });
        }


        tv_homeurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(home_url)); // Starts Implicit Activity
                startActivity(i);

            }
        });
        mDetail = "Learn the Awesome course of "+highAndLow+" at "+home_url;

        // If onCreateOptionsMenu has already happened, we need to update the share intent now.
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
}
