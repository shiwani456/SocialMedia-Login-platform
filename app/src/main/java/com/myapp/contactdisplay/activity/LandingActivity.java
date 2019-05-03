package com.myapp.contactdisplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.myapp.contactdisplay.R;
import com.myapp.contactdisplay.application.ActivityUserSpace;
import com.myapp.contactdisplay.fragment.AboutUsFragment;
import com.myapp.contactdisplay.fragment.ContactListFragment;
import com.myapp.contactdisplay.fragment.HomeFragment;
import com.myapp.contactdisplay.fragment.UpdateProfileFragment;
import com.facebook.login.LoginManager;

public class LandingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ActivityUserSpace session  = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new ActivityUserSpace(LandingActivity.this);
        setContentView(R.layout.activity_landing);
        init();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        displayView(0);
    }

    private void init() {


        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing, menu);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.home: {
                displayView(0);
                break;
            }
            case R.id.nav_about_us: {

                displayView(1);
                break;

            }
            case R.id.nav_contact_list: {

                displayView(2);
                break;

            }
            case R.id.nav_view_my_location: {

                displayView(3);
                break;
            }
            case R.id.nav_update_profile: {

                displayView(4);
                break;
            }
            case R.id.nav_logout: {

                displayView(5);
                break;
            }
            default:
                displayView(0);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayView(int position) {
        Fragment fragment = null;
        int container = R.id.RelativeLayout;
        String title = " ";
        switch (position) {
            case 0:
                fragment = HomeFragment.newInstance();
                break;
            case 1:
                fragment = AboutUsFragment.newInstance();
                break;
            case 2:
                fragment = ContactListFragment.newInstance();
                break;
            case 3:


                break;
            case 4:

                fragment = UpdateProfileFragment.newInstance();

                break;
            case 5:

                session.clearSession();
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(LandingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                break;

            default:
                fragment = new HomeFragment();
                position = 0;
                break;
        }
        setFregment(fragment, position, container, true);

    }


    private void setFregment(Fragment fragment, int position, int container, boolean backtostack) {
        String backTo = HomeFragment.class.getSimpleName();

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (position != 0 && backtostack) {
                fragmentTransaction.addToBackStack(backTo);
            }
            fragmentTransaction.replace(container, fragment);
            fragmentTransaction.commit();
        }

    }

}
