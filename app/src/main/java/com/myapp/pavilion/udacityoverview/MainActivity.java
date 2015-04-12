package com.myapp.pavilion.udacityoverview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new UdacityFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
  /*  public static class UdacityFragment extends Fragment implements View.OnClickListener{

        public UdacityFragment()
        {

        }
Button cs;


        Button wd;
Button se;
Button and;
Button iOS;
Button geo;
Button non_tech;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            cs=(Button)rootView.findViewById(R.id.data_science);
            wd=(Button)rootView.findViewById(R.id.web_development);
            se=(Button)rootView.findViewById(R.id.software_Engineering);
            and=(Button)rootView.findViewById(R.id.android);
            iOS=(Button)rootView.findViewById(R.id.iOS);
            geo=(Button)rootView.findViewById(R.id.georgia_cs);
            non_tech=(Button)rootView.findViewById(R.id.non_tech);
            cs.setOnClickListener(this);
            wd.setOnClickListener(this);
            se.setOnClickListener(this);            non_tech.setOnClickListener(this);
            and.setOnClickListener(this);
            iOS.setOnClickListener(this);
            geo.setOnClickListener(this);
            return rootView;

        }
        @Override
        public void onClick(View v) {
            Log.d("lst","onclick");
            int id=v.getId();
            switch(id)
            {
                case R.id.data_science:
                {
                    Intent intent=new Intent(getActivity(),ListActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.software_Engineering:
                {
                    break;
                }
                case R.id.web_development:
                {
                    break;
                }
                case R.id.android:
                {
                    break;
                }
                case R.id.iOS:
                {
                    break;
                }
                case R.id.georgia_cs:
                {
                    break;
                }
                case R.id.non_tech:
                {
                    break;
                }






            }
        }

    }*/
}
