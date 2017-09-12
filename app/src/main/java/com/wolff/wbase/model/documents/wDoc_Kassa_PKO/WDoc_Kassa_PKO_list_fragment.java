package com.wolff.wbase.model.documents.wDoc_Kassa_PKO;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.wolff.wbase.R;
import com.wolff.wbase.model.documents.wDocument.WDocument_list_item_adapter;
import com.wolff.wbase.model.documents.wDocument.WDocument;
import com.wolff.wbase.model.documents.wDocument.WDocument_getter;
import com.wolff.wbase.model.documents.wDocument.WDocument_list_fragment;
import com.wolff.wbase.model.metadata.MetaDocuments;
import com.wolff.wbase.tools.Debug;

import java.util.ArrayList;

/**
 * Created by wolff on 01.09.2017.
 */

public class WDoc_Kassa_PKO_list_fragment extends WDocument_list_fragment {
    private ArrayList<WDocument> mDocList;
    private WDoc_Kassa_PKO_list_fragment_listener listener1;

    public interface WDoc_Kassa_PKO_list_fragment_listener{
        void OnWDocumentItemSelected(WDocument document);
    }

    public static WDoc_Kassa_PKO_list_fragment newInstance(){
        return new WDoc_Kassa_PKO_list_fragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mDocList = new WDocument_getter(getContext()).getList(MetaDocuments.MDoc_Kassa_PKO.DOCUMENT_NAME);
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
        mMainAdapter = new WDocument_list_item_adapter(getContext(), mDocList);
        mMainListView.setAdapter(mMainAdapter);
        mMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener1.OnWDocumentItemSelected(mDocList.get(position));
            }
        });
        mMainListView.setTextFilterEnabled(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener1 = (WDoc_Kassa_PKO_list_fragment_listener) context;
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
                //Intent intent = WCat_Organization_item_activity.newIntent(getContext(),null);
                //startActivity(intent);
                Debug.Log("ADD ORG","ADDD");
                break;
            }
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}