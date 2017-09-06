package com.wolff.wbase.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.wolff.wbase.R;
import com.wolff.wbase.fragments.WCat_Organization_item_fragment;
import com.wolff.wbase.model.catalogs.wOrganization.WCat_Organization;

/**
 * Created by wolff on 04.09.2017.
 */

public class WOrganization_item_activity extends AppCompatActivity {
    //private WChannel mChannelItem;
    private static final String EXTRA_ORG_ITEM = "WOrgItem";

    public static Intent newIntent(Context context, WCat_Organization item){
        Intent intent = new Intent(context,WOrganization_item_activity.class);
        intent.putExtra(EXTRA_ORG_ITEM,item);
        return intent;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.item_activity);
        WCat_Organization item = (WCat_Organization) getIntent().getSerializableExtra(EXTRA_ORG_ITEM);
        WCat_Organization_item_fragment itemFragment = WCat_Organization_item_fragment.newInstance(item);

        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.item_container, itemFragment);
        fragmentTransaction.commit();


    }

}


