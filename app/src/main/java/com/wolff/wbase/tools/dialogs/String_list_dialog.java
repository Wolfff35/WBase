package com.wolff.wbase.tools.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.wolff.wbase.R;
import com.wolff.wbase.model.catalogs.wCatalog.WCatalog;
import com.wolff.wbase.model.catalogs.wCatalog.WCatalog_getter;
import com.wolff.wbase.model.catalogs.wCatalog.WCatalog_list_dialog;
import com.wolff.wbase.model.catalogs.wCatalog.WCatalog_list_item_adapter;

import java.util.ArrayList;

/**
 * Created by wolff on 13.09.2017.
 */

public class String_list_dialog  extends DialogFragment {
    public static final String CATALOG_DIALOG_RESULT = "CAT_DIAL_RES";
    private ListView mMainListView;
    private ArrayAdapter mMainAdapter;
    private Context mContext;
    private View mMainView;
    private static final String CATALOG_TYPE = "Type_Cat";
    private ArrayList<String> mCatalog;

    public String_list_dialog(){
        // this.mCatalogType = catalogType;
    }
    public static String_list_dialog newInstance(ArrayList<String> catalogType){
        Bundle args = new Bundle();
        args.putStringArrayList(CATALOG_TYPE,catalogType);
        String_list_dialog fragment = new String_list_dialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCatalog = getArguments().getStringArrayList(CATALOG_TYPE);
        //mCatalogType = getArguments().getString(CATALOG_TYPE);
        //getActivity().setTitle("ВЫБОР "+mCatalogType.replace("Catalog_",""));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        mMainView = LayoutInflater.from(mContext).inflate(R.layout.wcatalog_list_fragment,null);
        mMainListView = (ListView)mMainView.findViewById(R.id.lvMain);

        //final ArrayList<WCatalog> listCatalog = new WCatalog_getter(mContext).getList(mCatalogType);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //mMainAdapter = new WCatalog_list_item_adapter(mContext, listCatalog);
        mMainAdapter = new ArrayAdapter(mContext,android.R.layout.simple_list_item_1,mCatalog);
        mMainListView.setAdapter(mMainAdapter);
        mMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                //intent.putExtra(CATALOG_DIALOG_RESULT,listCatalog.get(position).getRef_Key());
                intent.putExtra(CATALOG_DIALOG_RESULT,mCatalog.get(position));
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
                dismiss();
            }
        });
        builder.setView(mMainView);
        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

}
