package com.example.pchrp.pro_1.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pchrp.pro_1.R;
import com.example.pchrp.pro_1.fragment.FragmentDevelorment;
import com.example.pchrp.pro_1.fragment.FragmentGrown;
import com.example.pchrp.pro_1.fragment.FragmentHome;
import com.example.pchrp.pro_1.fragment.FragmentInsentKid;
import com.example.pchrp.pro_1.fragment.FragmentLogin;
import com.example.pchrp.pro_1.fragment.FragmentVaccine;
import com.example.pchrp.pro_1.manager.SharedPrefManager;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle barDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initInstances();


        if (savedInstanceState == null){

            if(SharedPrefManager.getInstance(HomeActivity.this).getCheck()) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame_home, FragmentHome.newInstance())
                        .commit();
            }
            else{

                int id = SharedPrefManager.getInstance(HomeActivity.this).getID() ;
                String name,email,pass;

                name =  SharedPrefManager.getInstance(HomeActivity.this).getUsername() ;
                email = SharedPrefManager.getInstance(HomeActivity.this).getUserEmail() ;
                pass = SharedPrefManager.getInstance(HomeActivity.this).getUserpassword() ;
                boolean check = true;
                SharedPrefManager.getInstance(HomeActivity.this)
                        .userLogin(
                                id, name, email, pass,check

                        );
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame_home, FragmentInsentKid.newInstance())
                        .commit();
            }

        }
    }

    private void initInstances() {

        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_home);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }


        barDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this
                ,drawerLayout
                ,R.string.open_drawer
                ,R.string.close_drawer);
        barDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(barDrawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nev_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment fragment = getFragmentManager().findFragmentById(R.id.frame_home);

                if (id == R.id.menu_home){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_home,FragmentHome.newInstance())
                            .commit();
                }
                else if(id == R.id.menu_insertkid){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_home,FragmentInsentKid.newInstance())
                            .commit();
                }
                else if(id == R.id.menu_grown){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_home,FragmentGrown.newInstance())
                            .commit();
                }
                else if(id == R.id.menu_evolution){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_home,FragmentDevelorment.newInstance())
                            .commit();
                }
                else if(id == R.id.menu_injection){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_home,FragmentVaccine.newInstance())
                            .commit();
                }
                else if(id == R.id.menu_nutrition){
                    Toast.makeText(HomeActivity.this,"nutrition",Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.menu_calendar){
                    Toast.makeText(HomeActivity.this,"calendar",Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.menu_commend){
                    Toast.makeText(HomeActivity.this,"commend",Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        barDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        barDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (barDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}
