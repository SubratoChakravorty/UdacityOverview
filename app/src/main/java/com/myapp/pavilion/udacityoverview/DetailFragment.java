package com.myapp.pavilion.udacityoverview;

import android.content.Intent;
import android.database.Cursor;
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
    TextView tv;
    Uri muri;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        tv=(TextView)rootView.findViewById(R.id.tv);
        Intent i=getActivity().getIntent();
        if(i==null)
        {
            Log.e(LOG_TAG,"intent is null");
            return null;

        }
        muri=i.getData();
        Toast.makeText(getActivity(), muri.toString(), Toast.LENGTH_SHORT).show();
        getLoaderManager().restartLoader(DETAIL_LOADER_ID,null,this);






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
        Log.e("id_title",id_title+"");
        Log.e("column_count",cursor.getColumnCount()+"");
        // try {
        cursor.moveToFirst();
        String highAndLow = cursor.getString(id_title);

        Log.e("title", highAndLow);
        tv.setText(highAndLow);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
}
