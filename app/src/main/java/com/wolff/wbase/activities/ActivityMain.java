package com.wolff.wbase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.wolff.wbase.R;
import com.wolff.wbase.fragments.Logo_fragment;
import com.wolff.wbase.model.catalogs.wOrganization.WCat_Organization_item_activity;
import com.wolff.wbase.model.catalogs.wOrganization.WCat_Organization_list_fragment;
import com.wolff.wbase.model.documents.wDoc_Kassa_PKO.WDoc_Kassa_PKO_list_fragment;
import com.wolff.wbase.model.catalogs.wCatalog.WCatalog;
import com.wolff.wbase.model.documents.wDocument.WDocument;
import com.wolff.wbase.model.documents.wDoc_Kassa_PKO.WDoc_Kassa_PKO_item_activity;
import com.wolff.wbase.tools.UITools;
import com.wolff.wbase.tools.PreferencesTools;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        WCat_Organization_list_fragment.WCat_Organization_list_fragment_listener,
        WDoc_Kassa_PKO_list_fragment.WDoc_Kassa_PKO_list_fragment_listener{

    private Fragment mMainFragment;
    private UITools mUITools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(new PreferencesTools().getBooleanPreference(getApplicationContext(),PreferencesTools.PREFERENCE_IS_FIRST_RUN)){
            new UITools().firstRunInitialize(getApplicationContext());
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mUITools = new UITools();
        mUITools.designNavigationMenu(getApplicationContext(),navigationView.getMenu());
        navigationView.setNavigationItemSelectedListener(this);

        mMainFragment = Logo_fragment.newInstance();
        mUITools.displayFragment(this,mMainFragment);
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
        switch (id){
            case R.id.nav_menu_item_Organization: {
                mMainFragment = WCat_Organization_list_fragment.newInstance();
                mUITools.displayFragment(this,mMainFragment);
                break;
            }
            case R.id.nav_menu_item_PKO:{
                mMainFragment = WDoc_Kassa_PKO_list_fragment.newInstance();
                mUITools.displayFragment(this,mMainFragment);

                break;
            }

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//==================================================================================================

    @Override
    public void OnWOrganizationItemSelected(WCatalog organization) {
        Intent intent = WCat_Organization_item_activity.newIntent(getApplicationContext(),organization.getRef_Key());
        startActivity(intent);
    }

    @Override
    public void OnWDocumentItemSelected(WDocument document) {
        Intent intent = WDoc_Kassa_PKO_item_activity.newIntent(getApplicationContext(),document.getRef_Key());
        startActivity(intent);
    }
}
