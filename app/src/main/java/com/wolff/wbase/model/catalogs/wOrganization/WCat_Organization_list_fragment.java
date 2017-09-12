package com.wolff.wbase.model.catalogs.wOrganization;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.wolff.wbase.R;
import com.wolff.wbase.model.catalogs.wCatalog.WCatalog_list_item_adapter;
import com.wolff.wbase.model.catalogs.wCatalog.WCatalog_list_fragment;
import com.wolff.wbase.model.catalogs.wCatalog.WCatalog;
import com.wolff.wbase.model.catalogs.wCatalog.WCatalog_getter;
import com.wolff.wbase.model.metadata.MetaCatalogs;
import com.wolff.wbase.tools.Debug;

import java.util.ArrayList;

/**
 * Created by wolff on 01.09.2017.
 */

public class WCat_Organization_list_fragment extends WCatalog_list_fragment {
    private ArrayList<WCatalog> mOrgList;
    private WCat_Organization_list_fragment_listener listener1;

    public interface WCat_Organization_list_fragment_listener{
        void OnWOrganizationItemSelected(WCatalog organization);
    }

    public static WCat_Organization_list_fragment newInstance(){
        return new WCat_Organization_list_fragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mOrgList = new WCatalog_getter(getContext()).getList(MetaCatalogs.MOrganization.CATALOG_NAME);
        fillData();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity().getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        fillData();
        getActivity().setTitle("Организации");
    }

    private void fillData(){
        mMainAdapter = new WCatalog_list_item_adapter(getContext(), mOrgList);
        mMainListView.setAdapter(mMainAdapter);
        mMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener1.OnWOrganizationItemSelected(mOrgList.get(position));
            }
        });
        mMainListView.setTextFilterEnabled(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener1 = (WCat_Organization_list_fragment_listener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener1=null;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_item_add:{
                Intent intent = WCat_Organization_item_activity.newIntent(getContext(),null);
                startActivity(intent);
                Debug.Log("ADD ORG","ADDD");
                break;
            }
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}