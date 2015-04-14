package com.myapp.pavilion.udacityoverview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class ListActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        final ImageView imageView=(ImageView)findViewById(R.id.imageView);


        final int []imageArray={R.drawable.udacity,R.drawable.udacity1,R.drawable.udacity2,R.drawable.udacity3};


        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run() {
                imageView.setImageResource(imageArray[i]);
                i++;
                if(i>imageArray.length-1)
                {
                    i=0;
                }
                handler.postDelayed(this,2000);  //for interval...
            }
        };
        handler.postDelayed(runnable, 2000); //for initial delay..




    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
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
 /*   public static class CourseFragment extends Fragment {

        public CourseFragment() {
            setHasOptionsMenu(true);
        }

        private UdacityAdapter mUdacitytAdapter;
public static final String KEY_COURSE="key";

        @Override
        public void onStart() {
            super.onStart();
            Log.e("onStart","fetchCoursetask");
            FetchCourseTask f=new FetchCourseTask(getActivity());
            f.execute();
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
            mUdacitytAdapter = new UdacityAdapter(getActivity(), cur, 0);
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

    }*/
}
