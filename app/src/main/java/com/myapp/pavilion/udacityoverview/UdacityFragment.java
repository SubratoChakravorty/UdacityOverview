package com.myapp.pavilion.udacityoverview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Pavilion on 11-04-2015.
 */
public class UdacityFragment extends Fragment implements View.OnClickListener{

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
        Log.d("lst", "onclick");
        int id=v.getId();
        switch(id)
        {
            case R.id.data_science:
            {
                Log.e("onclick","datascience");
                Intent intent=new Intent(getActivity(),ListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.software_Engineering:
            {
                Log.e("onclick","courses data");


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

}


