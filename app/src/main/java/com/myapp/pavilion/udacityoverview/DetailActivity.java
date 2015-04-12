package com.myapp.pavilion.udacityoverview;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {
        TextView tv;

        public DetailFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            tv=(TextView)rootView.findViewById(R.id.tv);
            Intent i=getActivity().getIntent();
           Uri uri=i.getData();
            Toast.makeText(getActivity(),uri.toString(),Toast.LENGTH_SHORT).show();
            Cursor cur = getActivity().getContentResolver().query(uri, null, null, null, null);
            Log.e("cursor count",cur.getCount()+"");
            Toast.makeText(getActivity(),cur.getCount()+"",Toast.LENGTH_SHORT);




        int id_title = cur.getColumnIndex(UdacityContract.CourseEntry.COLUMN_TITLE);
       Log.e("id_title",id_title+"");
            Log.e("column_count",cur.getColumnCount()+"");
           // try {
cur.moveToFirst();
                String highAndLow = cur.getString(id_title);

                Log.e("title", highAndLow);
            tv.setText(highAndLow);
           // }
           /* catch(Exception e)
            {
                e.printStackTrace();
            }*/

            return rootView;
        }
    }
}
