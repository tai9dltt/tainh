package com.example.i_tainh.demoorderfood.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.i_tainh.demoorderfood.FragmentApp.showMenuCruise;
import com.example.i_tainh.demoorderfood.FragmentApp.showTable;
import com.example.i_tainh.demoorderfood.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayou;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView txtTenNV_Navigation;
    FragmentManager fragmentManager;



    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_layout);

        drawerLayou = (DrawerLayout) findViewById(R.id.drawLayout);
        navigationView = findViewById(R.id.home_navigationView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        View view =  navigationView.getHeaderView(0);
//        View view = LayoutInflater.from(this).inflate(R.layout.layout_header_navigation_homepage, null);
        txtTenNV_Navigation = view.findViewById(R.id.txt_tenNV_navigation);



        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayou,toolbar,R.string.open,R.string.close);
//        {
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//
//            }
//
//
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//            }
//        };


            drawerLayou.setDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();

            navigationView.setItemIconTintList(null);
            navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String username_temp = intent.getStringExtra("username_temp");
        txtTenNV_Navigation.setText(username_temp);


        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        showTable show = new showTable();
        fragmentTransaction.replace(R.id.content,show);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.it_home:
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                showTable show = new showTable();
                fragmentTransaction.replace(R.id.content,show);
                fragmentTransaction.commit();

                menuItem.setChecked(true);
                drawerLayou.closeDrawers();
                break;

            case R.id.thuc_don:
                FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                showMenuCruise showMenuCruise = new showMenuCruise();
                fragmentTransaction2.replace(R.id.content,showMenuCruise);
                fragmentTransaction2.commit();
                menuItem.setChecked(true);
                drawerLayou.closeDrawers();

        }
        return false;
    }
}
