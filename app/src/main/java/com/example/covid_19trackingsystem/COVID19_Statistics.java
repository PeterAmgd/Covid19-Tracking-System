package com.example.covid_19trackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class COVID19_Statistics extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {

    //UI Views

    private BottomNavigationView nvig_button;
    private final static int REQUEST_ENABLE_BT = 1;

    //Fragments
    private Fragment GlobalFragment,CitiesFragment,dashboard;
    private Fragment activeFragment;
    private FragmentManager fragmentManager;
    private BluetoothAdapter mBTAdapter;
    private ArrayAdapter<String> mBTArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid19_statistics);
        mBTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);



        //init UI Views

//        Rfrshbtn=findViewById(R.id.Rfrsh_Btn);
        nvig_button=findViewById(R.id.nvig_button);


        initFragment();


        //refresh Button Click,Refresh records
/*        Rfrshbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalFragment.onResume();
                CitiesFragment.onResume();
                dashboard.onResume();
            }
        });*/
        nvig_button.setOnNavigationItemSelectedListener(this);

    }




    private void initFragment() {
        //init fragment
        GlobalFragment = new GlobalFragment();
        CitiesFragment= new CitiesFragment();
        dashboard=new DashboardFragment();
        fragmentManager=getSupportFragmentManager();
        activeFragment= dashboard;
        fragmentManager.beginTransaction().add(R.id.frame,dashboard,"Dashboard ").commit();
        fragmentManager.beginTransaction().add(R.id.frame,GlobalFragment,"Global Statistics").hide(GlobalFragment).commit();

        fragmentManager.beginTransaction().add(R.id.frame,CitiesFragment,"Cities Statistics").hide(CitiesFragment).commit();

    }
    private void loaddashboardFragment(){


        fragmentManager.beginTransaction().hide(activeFragment).show(dashboard).commit();
        activeFragment=dashboard;

    }

    private void loadHomeFragment(){


        fragmentManager.beginTransaction().hide(activeFragment).show(GlobalFragment).commit();
        activeFragment=GlobalFragment;

    }
    private void loadStatsFragment(){


        fragmentManager.beginTransaction().hide(activeFragment).show(CitiesFragment).commit();
        activeFragment=CitiesFragment;

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //handelling navigation button item clicks
        switch (item.getItemId()) {

            //load home data

            case R.id.nav_home:
                loadHomeFragment();
                return true;

            //load home data

            case R.id.nav_stats:
                loadStatsFragment();
                return true;

            case R.id.nav_dashboard:
                loaddashboardFragment();
                return true;

        }
        return false;

    }

}