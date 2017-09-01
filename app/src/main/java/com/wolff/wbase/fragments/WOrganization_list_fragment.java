package com.wolff.wbase.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.wolff.wbase.R;
import com.wolff.wbase.adapters.WOrganization_list_item_adapter;
import com.wolff.wbase.model.catalogs.wOrganization.WOrganization;
import com.wolff.wbase.model.catalogs.wOrganization.WOrganization_getter;

import java.util.ArrayList;

/**
 * Created by wolff on 01.09.2017.
 */

public class WOrganization_list_fragment  extends AWObject_list_fragment implements SearchView.OnQueryTextListener{
    private ArrayList<WOrganization> mOrgList;
    private ListView mListViewMain;
    private EditText mEtSearchItem;
    private WOrganization_list_fragment_listener listener1;

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

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
        mEtSearchItem = (EditText)view.findViewById(R.id.etSearchItem);
        //mEtSearchItem.
        http://www.java2s.com/Code/Android/2D-Graphics/ShowsalistthatcanbefilteredinplacewithaSearchViewinnoniconifiedmode.htm
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        WOrganization_list_item_adapter adapter = new WOrganization_list_item_adapter(getContext(),mOrgList);

        mListViewMain.setAdapter(adapter);
        mListViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener1.OnWOrganizationItemSelected(mOrgList.get(position));
            }
        });
        getActivity().setTitle("Организации");
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

}