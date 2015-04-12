package com.myapp.pavilion.udacityoverview;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Pavilion on 12-04-2015.
 */
public class CourseFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private final int COURSE_LOADER_ID=0;
    public CourseFragment() {
        setHasOptionsMenu(true);
    }

    private UdacityAdapter mUdacitytAdapter;
    public static final String KEY_COURSE="key";

    @Override
    public void onStart() {
        super.onStart();
        Log.e("onStart", "fetchCoursetask");
        FetchCourseTask f=new FetchCourseTask(getActivity());
        f.execute();
        getLoaderManager().restartLoader(COURSE_LOADER_ID,null,this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(COURSE_LOADER_ID,null,this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] data = {
                "Mon 6/23 - Sunny - 31/17",
                "Tue 6/24 - Foggy - 21/8",
                "Wed 6/25 - Cloudy - 22/17",
                "Thurs 6/26 - Rainy - 18/11",
                "Fri 6/27 - Foggy - 21/10",
                "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
                "Sun 6/29 - Sunny - 20/7"
        };


        Cursor cur=getActivity().getContentResolver().query(UdacityContract.CourseEntry.CONTENT_URI,null,null,null,null);

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        mUdacitytAdapter = new UdacityAdapter(getActivity(), null, 0);
        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_courses);
        listView.setAdapter(mUdacitytAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView adapterView, View view,
                                    int position, long l) {
                // CursorAdapter returns a cursor at the correct position for
                // getItem(), or null
                // if it cannot seek to that position.
                Cursor cursor = (Cursor) adapterView
                        .getItemAtPosition(position);
                if (cursor != null) {
                    int id_key=cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_KEY);
                    String KEY=cursor.getString(id_key);

                    Uri uri= UdacityContract.CourseEntry.buildCourseKeyUri(KEY);
                    //  Toast.makeText(getActivity(),uri.getLastPathSegment(),Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(),DetailActivity.class).setData(uri);

                    startActivity(intent);

                }
            }
        });


        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchCourseTask f=new FetchCourseTask(getActivity());
            f.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(),UdacityContract.CourseEntry.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mUdacitytAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mUdacitytAdapter.swapCursor(null);

    }
}

