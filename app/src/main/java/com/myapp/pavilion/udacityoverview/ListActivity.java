package com.myapp.pavilion.udacityoverview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class ListActivity extends ActionBarActivity implements CourseFragment.Callback{
    private boolean mTwoPane;
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
       // final ImageView imageView=(ImageView)findViewById(R.id.imageView);
        if (findViewById(R.id.udacity_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.udacity_detail_container, new DetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
            ImageAdapter adapter = new ImageAdapter(this);
            viewPager.setAdapter(adapter);






            final Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                int i=0;
                public void run() {
                    viewPager.setCurrentItem(i);
                    i++;
                    if(i>2)
                    {
                        i=0;
                    }
                    handler.postDelayed(this,2000);  //for interval...
                }
            };
            handler.postDelayed(runnable, 2000); //for initial delay..*/


        }



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

    @Override
    public void onItemSelected(Uri contentUri){
        // TODO Auto-generated method stub
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putParcelable(DetailFragment.DETAIL_URI, contentUri);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.udacity_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
           // Toast.makeText(this,"list activity"+contentUri.toString(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DetailActivity.class)
                    .setData(contentUri);
            startActivity(intent);
        }
    }
}
