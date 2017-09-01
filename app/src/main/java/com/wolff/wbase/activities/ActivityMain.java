package com.wolff.wbase.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.wolff.wbase.R;
import com.wolff.wbase.fragments.WOrganization_list_fragment;
import com.wolff.wbase.model.catalogs.wOrganization.WOrganization;
import com.wolff.wbase.tools.DateFormatTools;
import com.wolff.wbase.tools.Debug;
import com.wolff.wbase.tools.UITools;
import com.wolff.wbase.tools.PreferencesTools;

import java.util.Date;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,WOrganization_list_fragment.WOrganization_list_fragment_listener {

    private Fragment mMainFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(new PreferencesTools().getBooleanPreference(getApplicationContext(),PreferencesTools.PREFERENCE_IS_FIRST_RUN)){
            new UITools().firstRunInitialize(getApplicationContext());
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
        //});

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        new UITools().designNavigationMenu(getApplicationContext(),navigationView.getMenu());
        navigationView.setNavigationItemSelectedListener(this);

        //mMainFragment =Settings_fragment.newInstance();
        //mMainFragment = Logo_fragment.newInstance();
        if(PreferencesTools.IS_DEBUG) {
            Date start = new Date();
            Log.e("DEBUG","BEGIN "+new DateFormatTools().dateToString(start,DateFormatTools.DATE_FORMAT_VID_FULL));
            //------------------------------------------------------
            Debug.onCreateActivityMain(getApplicationContext());

            mMainFragment = WOrganization_list_fragment.newInstance();
            displayFragment();
            //------------------------------------------------------
            Date endDate = new Date();
            Log.e("DEBUG","END "+new DateFormatTools().dateToString(endDate,DateFormatTools.DATE_FORMAT_VID_FULL)+"; time - " +((endDate.getTime()-start.getTime()))+" m/sec");


        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

     @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//===
    private void displayFragment() {
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_main, mMainFragment);
        fragmentTransaction.commit();
    }

//==================================================================================================

    @Override
    public void OnWOrganizationItemSelected(WOrganization organization) {

    }
}
