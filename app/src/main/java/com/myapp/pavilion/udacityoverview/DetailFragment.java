package com.myapp.pavilion.udacityoverview;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
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
    TextView tv_title;
    TextView tv_key;
    TextView tv_level;
    TextView tv_summary;
    TextView tv_youtube;
    TextView tv_you;
    Uri muri;

    public DetailFragment() {
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

        Intent i=getActivity().getIntent();
        if(i==null)
        {
            Log.e(LOG_TAG,"intent is null");
            return null;

        }
        muri=i.getData();

        getLoaderManager().restartLoader(DETAIL_LOADER_ID, null, this);






        // }
           /* catch(Exception e)
            {
                e.printStackTrace();
            }*/

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(),muri,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        int id_title = cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_TITLE);
        int id_key = cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_KEY);
        int id_summary= cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_SHORT_SUMMARY);
        int id_youtube = cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_VIDEO);
        int id_level = cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_LEVEL);


        Log.e("id_title",id_title+"");
        Log.e("column_count",cursor.getColumnCount()+"");
        // try {
        cursor.moveToFirst();
        String highAndLow = cursor.getString(id_title);
        String level=cursor.getString(id_level);
        String key=cursor.getString(id_key);
        String summary=cursor.getString(id_summary);
        final String y=cursor.getString(id_youtube);
        tv_title.setTextSize(20);
        Toast.makeText(getActivity(),y,Toast.LENGTH_LONG).show();
        Log.e("title", highAndLow);
        tv_title.setText(highAndLow);
        tv_youtube.setTextColor(Color.RED);
        tv_youtube.setText(y);
        tv_summary.setText(summary);
        tv_level.setText(level);
        tv_key.setText(key);
        tv_you.setTextColor(Color.GREEN);
        tv_you.setText("YOUTUBE TEASER");
        tv_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(y)); // Starts Implicit Activity
                startActivity(i);

            }
        });


    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
}
