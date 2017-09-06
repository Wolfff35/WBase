package com.wolff.wbase.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.wolff.wbase.R;
import com.wolff.wbase.adapters.WCatalog_list_item_adapter;
import com.wolff.wbase.model.abs.WCatalog;
import com.wolff.wbase.model.catalogs.wOrganization.WCat_Organization;
import com.wolff.wbase.model.catalogs.wOrganization.WCat_Organization_getter;
import com.wolff.wbase.tools.Debug;

import java.util.ArrayList;

/**
 * Created by wolff on 01.09.2017.
 */

public class WCat_Organization_list_fragment extends WCatalog_list_fragment {
    private ArrayList<WCat_Organization> mOrgList;
    private WOrganization_list_fragment_listener listener1;
    public interface WOrganization_list_fragment_listener{
        void OnWOrganizationItemSelected(WCat_Organization organization);
    }
    public static WCat_Organization_list_fragment newInstance(){
        return new WCat_Organization_list_fragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mOrgList = new WCat_Organization_getter(getContext()).getList();
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
        mMainAdapter = new WCatalog_list_item_adapter(getContext(),(ArrayList<WCatalog>)(ArrayList<?>) mOrgList);
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
        listener1 = (WCat_Organization_list_fragment.WOrganization_list_fragment_listener) context;
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
                //Intent intent = ChannelGroup_item_activity.newIntent(getContext(),null);
                //startActivity(intent);
                Debug.Log("ADD ORG","ADDD");
                break;
            }
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}