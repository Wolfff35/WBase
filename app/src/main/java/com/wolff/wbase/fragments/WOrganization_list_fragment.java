package com.wolff.wbase.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.wolff.wbase.R;
import com.wolff.wbase.adapters.WOrg_list_item_adapter;
import com.wolff.wbase.model.catalogs.wOrganization.WOrganization;
import com.wolff.wbase.model.catalogs.wOrganization.WOrganization_getter;

import java.util.ArrayList;

/**
 * Created by wolff on 01.09.2017.
 */

public class WOrganization_list_fragment  extends AWObject_list_fragment implements SearchView.OnQueryTextListener{
    private ArrayList<WOrganization> mOrgList;
    private ListView mListViewMain;
    private SearchView mSearchItem;
    private WOrganization_list_fragment_listener listener1;
    WOrg_list_item_adapter mAdapter;
    public interface WOrganization_list_fragment_listener{
        void OnWOrganizationItemSelected(WOrganization organization);
    }
    public static WOrganization_list_fragment newInstance(){
        return new WOrganization_list_fragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mOrgList = new WOrganization_getter(getContext()).getList();
        onActivityCreated(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.list_fragment,container,false);
        mListViewMain = (ListView)view.findViewById(R.id.lvMain);
        mSearchItem = (SearchView) view.findViewById(R.id.etSearchItem);
        //mSearchItem.setOnQueryTextListener();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity().getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        mAdapter = new WOrg_list_item_adapter(getActivity(),mOrgList);

        mListViewMain.setAdapter(mAdapter);
        mListViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener1.OnWOrganizationItemSelected(mOrgList.get(position));
            }
        });
        mListViewMain.setTextFilterEnabled(true);
        setupSearchView();
        getActivity().setTitle("Организации");
    }

    private void setupSearchView() {
        mSearchItem.setIconifiedByDefault(false);
        mSearchItem.setOnQueryTextListener(this);
        mSearchItem.setSubmitButtonEnabled(true);
        mSearchItem.setQueryHint("Подсказка");
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener1 = (WOrganization_list_fragment.WOrganization_list_fragment_listener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener1=null;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        //Log.e("TEXT CHANGE",""+newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //Log.e("SUBMIT","! "+query);
        return false;
    }

}