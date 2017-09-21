package com.wolff.wbase.model.documents.wDocument;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.wolff.wbase.R;
import com.wolff.wbase.datalab.WGetter;
import com.wolff.wbase.model.catalogs.wCatalog.WCatalog_item_activity;
import com.wolff.wbase.tools.Debug;

import java.util.ArrayList;

/**
 * Created by wolff on 01.09.2017.
 */

public class WDocument_list_fragment<WType extends WDocument> extends WDocument_search_list_fragment {
    private static String OBJECT_TYPE = "Obj_Type";
    private static String OBJECT_CLASS = "Obj_class";
    private Class <? extends WType> mClass;
    private String mObjectType;

    private ArrayList<WType> mDocList;
    private WDocument_list_fragment_listener listener1;

    public interface WDocument_list_fragment_listener{
        void OnWDocumentItemSelected(String doc_key,String objectType,Class class1);
    }

    public static WDocument_list_fragment newInstance(String objectType, Class<?> wClass){
        Bundle args = new Bundle();
        args.putString(OBJECT_TYPE,objectType);
        args.putSerializable(OBJECT_CLASS,wClass);
        WDocument_list_fragment fragm = new WDocument_list_fragment();
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
        mDocList = new WGetter<>(getContext(),mObjectType,mClass).getList();
        fillData();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity().getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        fillData();
        getActivity().setTitle("ПКО");
    }

    private void fillData(){
        mMainAdapter = new WDocument_list_item_adapter(getContext(),  (ArrayList<WDocument>) mDocList);
        mMainListView.setAdapter(mMainAdapter);
        mMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener1.OnWDocumentItemSelected(mDocList.get(position).getRef_Key(),mObjectType,mClass);
            }
        });
        mMainListView.setTextFilterEnabled(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener1 = (WDocument_list_fragment_listener) context;
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
                Intent intent = WDocument_item_activity.newIntent(getContext(),null,mClass);
                startActivity(intent);
                break;
            }
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}