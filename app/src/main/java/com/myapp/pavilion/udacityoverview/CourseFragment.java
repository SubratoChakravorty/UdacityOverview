package com.myapp.pavilion.udacityoverview;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NotificationCompat;
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
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.pavilion.udacityoverview.Service.UdacityService;

/**
 * Created by Pavilion on 12-04-2015.
 */
public class CourseFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private final int COURSE_LOADER_ID = 0;
    public int mPosition;
    String KEY="POSITION";
    private static final int uniqueId=734838;

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Uri dateUri);
    }

    public CourseFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mPosition!=ListView.INVALID_POSITION)
        {
            outState.putInt(KEY,mPosition);
        }

        super.onSaveInstanceState(outState);
    }

    private Boolean[] thumbnailsselection;
    private int count;
    ListView listView;
    private int[] ids;
    private CheckBox check;
    private UdacityAdapter mUdacitytAdapter;
    private StarredAdapter mStarredAdapter;

    public static final String KEY_COURSE = "key";

    @Override
    public void onStart() {
        super.onStart();




        getLoaderManager().restartLoader(COURSE_LOADER_ID, null, this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(COURSE_LOADER_ID, null, this);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        Cursor cur = getActivity().getContentResolver().query(UdacityContract.CourseEntry.CONTENT_URI, null, null, null, null);
Intent intent=getActivity().getIntent();
        if(intent!=null) {
            int uniId = intent.getIntExtra("uniqueId", 0);
            NotificationManager nm=(NotificationManager)getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
   nm.cancel(uniId);
        }
        mUdacitytAdapter = new UdacityAdapter(getActivity(), null, 0);
        // Get a reference to the ListView, and attach this adapter to it.
        listView = (ListView) rootView.findViewById(R.id.listview_courses);
        listView.setAdapter(mUdacitytAdapter);
        ViewHolder viewHolder = (ViewHolder) rootView.getTag();
        check = (CheckBox) rootView.findViewById(R.id.check_all);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView adapterView, View view,
                                    int position, long l) {
                // CursorAdapter returns a cursor at the correct position for
                // getItem(), or null
                // if it cannot seek to that position.
                           mPosition=position;
                Cursor cursor = (Cursor) adapterView
                        .getItemAtPosition(position);
                if (cursor != null) {
                    int id_key = cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_KEY);
                    String KEY = cursor.getString(id_key);

                    Uri uri = UdacityContract.CourseEntry.buildCourseKeyUri(KEY);
                    ((Callback) getActivity()).onItemSelected(uri);
                    //  Toast.makeText(getActivity(),uri.getLastPathSegment(),Toast.LENGTH_SHORT).show();


                }
            }
        });
        if(savedInstanceState!=null&&savedInstanceState.containsKey(KEY))
        {
            mPosition=savedInstanceState.getInt(KEY);
        }

        return rootView;
    }

    public boolean storeArray(Boolean[] array, String arrayName, Context mContext) {

        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName + "_size", array.length);

        for (int i = 0; i < array.length; i++)
            editor.putBoolean(arrayName + "_" + i, array[i]);

        return editor.commit();
    }

    public Boolean[] loadArray(String arrayName, Context mContext) {

        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        int size;
        if (prefs.getInt(arrayName + "_size", count) == count) {
            size = prefs.getInt(arrayName + "_size", count);
        } else {
            Intent intent=new Intent(getActivity(),ListActivity.class);
            String text=count-prefs.getInt(arrayName + "_size", count)+" new courses were added!!";
            NotificationCompat.Builder mbuilder=new NotificationCompat.Builder(getActivity()).setSmallIcon(R.drawable.icon).setContentTitle("Udacity Overview").setContentText(text);
TaskStackBuilder stackBuilder=TaskStackBuilder.create(getActivity());
            stackBuilder.addNextIntent(intent);
            PendingIntent pi=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
            mbuilder.setContentIntent(pi);
            NotificationManager nm=(NotificationManager)getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
            nm.notify(uniqueId,mbuilder.build());
            size = count;
        }

        Boolean array[] = new Boolean[size];
        for (int i = 0; i < size; i++)
            array[i] = prefs.getBoolean(arrayName + "_" + i, false);

        return array;
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
            Log.e("refresh button", "UdacityService");
            getLoaderManager().initLoader(COURSE_LOADER_ID, null, this);
            Intent intent=new Intent(getActivity(), UdacityService.class);
            getActivity().startService(intent);
            return true;
        }
        if (id == R.id.check_all) {


            if (item.isChecked()) {
                storeArray(thumbnailsselection, "thumbnail", getActivity());
                Log.e("is checked", "" + thumbnailsselection.length);
                //Toast.makeText(getActivity(),"checkbox is checked",Toast.LENGTH_SHORT).show();
                item.setChecked(false);
                item.setIcon(R.drawable.button_pressed);

                final int len = thumbnailsselection.length;
                int cnt = 0;
                for (int i = 0; i < len; i++) {
                    if (thumbnailsselection[i]) {
                        cnt++;
                    }


                }

                String selectargs[] = new String[cnt];
                cnt = 0;
                for (int i = 0; i < len; i++) {
                    if (thumbnailsselection[i]) {
                        cnt++;
                        selectargs[cnt - 1] = "" + ids[i];


                    }
                }
                if (cnt == 0) {
                    Toast.makeText(getActivity(),
                            "Please select at least one course",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(),
                            "You've selected Total " + cnt + " courses.",
                            Toast.LENGTH_LONG).show();

                }

                //Toast.makeText(getActivity(),""+ids[1],Toast.LENGTH_SHORT).show();
                final String Selection = getSelection(cnt);
                Cursor ur = getActivity().getContentResolver().query(UdacityContract.CourseEntry.CONTENT_URI, null, Selection, selectargs, null);


                mStarredAdapter = new StarredAdapter(getActivity(), ur, 0);

                // Get a reference to the ListView, and attach this adapter to it.

                listView.setAdapter(mStarredAdapter);


            } else {
                listView.setAdapter(mUdacitytAdapter);
                item.setChecked(true);
                item.setIcon(R.drawable.star);
                getLoaderManager().restartLoader(COURSE_LOADER_ID, null, this);
                // Toast.makeText(getActivity(), "checkBox is unchecked", Toast.LENGTH_SHORT).show();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), UdacityContract.CourseEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mUdacitytAdapter.swapCursor(cursor);
        this.count = cursor.getCount();
        Log.e("course fragment",count+"");
        if(this.count==0)
        {
            Intent intent=new Intent(getActivity(), UdacityService.class);
            getActivity().startService(intent);
            Log.e("course fragment","service called");
        }
        this.ids = new int[this.count];
        for (int i = 0; i < this.count; i++) {
            cursor.moveToPosition(i);
            int id = cursor.getColumnIndex(UdacityContract.CourseEntry._ID);


            ids[i] = cursor.getInt(id);
        }
        this.thumbnailsselection = new Boolean[this.count];
        Log.e("length array", "" + thumbnailsselection.length);

        this.thumbnailsselection = loadArray("thumbnail", getActivity());

        Log.e("length array", "" + thumbnailsselection.length);

        if(mPosition!=ListView.INVALID_POSITION)
        {
            listView.setSelection(mPosition);
        }


    }

    private static String getSelection(int args) {
        StringBuilder sb = new StringBuilder(UdacityContract.CourseEntry._ID + " IN  (");
        boolean first = true;
        for (int i = 0; i < args; i++) {
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            sb.append('?');
        }
        sb.append(')');
        return sb.toString();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mUdacitytAdapter.swapCursor(null);

    }

    public class UdacityAdapter extends CursorAdapter {


        public UdacityAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);

        }


        /*
            Remember that these views are reused as needed.
         */
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_courses, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);


            return view;
        }

        /*
            This is where we fill-in the views with the contents of the cursor.
         */
        @Override
        public void bindView(View view, final Context context, Cursor cursor) {
            // our view is pretty simple here --- just a text view
            // we'll keep the UI functional with a simple (and slow!) binding.
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            int id_title = cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_TITLE);
            int id_level = cursor.getColumnIndex(UdacityContract.CourseEntry.COLUMN_LEVEL);
            Cursor c = getCursor();
            int id = c.getPosition();

            //this.thumbnailsselection=new Boolean[this.cur.getCount()];

            viewHolder.cb.setId(id);


            viewHolder.cb.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    CheckBox cb = (CheckBox) v;
                    int id = cb.getId();
                    // Toast.makeText(getActivity(),id+"",Toast.LENGTH_SHORT).show();
                    if (thumbnailsselection[id]) {

                        thumbnailsselection[id] = false;
                    } else {

                        thumbnailsselection[id] = true;
                    }
                }
            });
            if (thumbnailsselection.length>0&&thumbnailsselection[id]) {
                viewHolder.cb.setChecked(true);
            } else {


                viewHolder.cb.setChecked(false);
            }
            //i++;
            int idd = id + 1;
            viewHolder.titleView.setText(idd + ")" + cursor.getString(id_title));
            viewHolder.levelView.setText(cursor.getString(id_level));


        }
    }

    public static class ViewHolder {

        public final TextView titleView;
        public final TextView levelView;
        public final CheckBox cb;


        public ViewHolder(View view) {
            cb = (CheckBox) view.findViewById(R.id.checkBox1);

            titleView = (TextView) view.findViewById(R.id.list_item_title);
            levelView = (TextView) view.findViewById(R.id.list_item_level);


        }

        public int getCount(Cursor cursor) {
            return cursor.getCount();
        }
    }
}

