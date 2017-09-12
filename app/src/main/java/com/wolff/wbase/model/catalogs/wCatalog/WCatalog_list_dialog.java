package com.wolff.wbase.model.catalogs.wCatalog;

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
import android.widget.ListView;
import android.widget.SearchView;

import com.wolff.wbase.R;

import java.util.ArrayList;

/**
 * Created by wolff on 04.09.2017.
 */

public class WCatalog_list_dialog extends DialogFragment implements SearchView.OnQueryTextListener{
    public static final String CATALOG_DIALOG_RESULT = "CAT_DIAL_RES";
    private ListView mMainListView;
    private SearchView mMainSearchView;
    private WCatalog_list_item_adapter mMainAdapter;
    private Context mContext;
    private View mMainView;
    private String mCatalogType;
    private static final String CATALOG_TYPE = "Type_Cat";

    public WCatalog_list_dialog(){
       // this.mCatalogType = catalogType;
    }
    public static WCatalog_list_dialog newInstance(String catalogType){
        Bundle args = new Bundle();
        args.putSerializable(CATALOG_TYPE,catalogType);
        WCatalog_list_dialog fragment = new WCatalog_list_dialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCatalogType = getArguments().getString(CATALOG_TYPE);
        //getActivity().setTitle("ВЫБОР "+mCatalogType.replace("Catalog_",""));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        mMainView = LayoutInflater.from(mContext).inflate(R.layout.wcatalog_list_fragment,null);
        mMainListView = (ListView)mMainView.findViewById(R.id.lvMain);
        mMainSearchView = (SearchView)mMainView.findViewById(R.id.etSearchItem);
        setupSearchView();

        final ArrayList<WCatalog> listCatalog = new WCatalog_getter(mContext).getList(mCatalogType);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mMainAdapter = new WCatalog_list_item_adapter(mContext, listCatalog);
        mMainListView.setAdapter(mMainAdapter);
        mMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(CATALOG_DIALOG_RESULT,listCatalog.get(position).getRef_Key());
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
                dismiss();
            }
        });
        builder.setView(mMainView);
        return builder.create();

    }
    private void setupSearchView() {
        mMainSearchView.setIconifiedByDefault(false);
        mMainSearchView.setOnQueryTextListener(this);
        mMainSearchView.setSubmitButtonEnabled(true);
        mMainSearchView.setQueryHint("Поиск");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mMainAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

}
