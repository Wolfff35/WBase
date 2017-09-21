package com.wolff.wbase.model.catalogs.wCatalog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.wolff.wbase.R;
import com.wolff.wbase.datalab.WGetter;
import com.wolff.wbase.tools.Debug;

import java.util.ArrayList;

/**
 * Created by wolff on 01.09.2017.
 */

public class WCatalog_list_fragment<WType extends WCatalog> extends WCatalog_search_list_fragment {
    private static String OBJECT_TYPE = "Obj_Type";
    private static String OBJECT_CLASS = "Obj_class";
    private Class <? extends WType> mClass;
    private String mObjectType;

    private ArrayList<WType> mList;
    private WList_fragment_listener listener1;

    public interface WList_fragment_listener{
        void OnItemSelected(String catalog_key,String objectType,Class cl);
    }

    public static WCatalog_list_fragment newInstance(String objectType, Class<?> wClass){
        Bundle args = new Bundle();
        args.putString(OBJECT_TYPE,objectType);
        args.putSerializable(OBJECT_CLASS,wClass);
        WCatalog_list_fragment fragm = new WCatalog_list_fragment();
        fragm.setArguments(args);
        return fragm;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mObjectType = getArguments().getString(OBJECT_TYPE);
        mClass = (Class)getArguments().getSerializable(OBJECT_CLASS);
    }

    @Override
    public void onResume() {
        super.onResume();
        mList = new WGetter<>(getContext(),mObjectType,mClass).getList();
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
        mMainAdapter = new WCatalog_list_item_adapter(getContext(), (ArrayList<WCatalog>) mList);
        mMainListView.setAdapter(mMainAdapter);
        mMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener1.OnItemSelected(mList.get(position).getRef_Key(),mObjectType,mClass);
            }
        });
        mMainListView.setTextFilterEnabled(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener1 = (WList_fragment_listener) context;
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
                Intent intent = WCatalog_item_activity.newIntent(getContext(),null,mClass);
                startActivity(intent);
                break;
            }
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}