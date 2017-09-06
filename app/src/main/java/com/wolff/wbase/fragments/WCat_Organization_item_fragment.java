package com.wolff.wbase.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wolff.wbase.R;
import com.wolff.wbase.custom_views.EditView;
import com.wolff.wbase.dialogs.WCatalog_list_dialog;
import com.wolff.wbase.model.catalogs.wContragent.WCat_Contragent;
import com.wolff.wbase.model.catalogs.wContragent.WCat_Contragent_getter;
import com.wolff.wbase.model.catalogs.wOrganization.WCat_Organization;
import com.wolff.wbase.model.catalogs.wOrganization.WCat_Organization_saver;
import com.wolff.wbase.model.catalogs.wOrganization.WCat_Organization_updater;
import com.wolff.wbase.tools.Debug;
import com.wolff.wbase.custom_views.SelectView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by wolff on 04.09.2017.
 */

public class WCat_Organization_item_fragment extends WCatalog_item_fragment{
    public static final String ORG_ITEM_ARG = "W_ORG";
    private static final int DIALOG_REQUEST_CONTRAGENT = 1;

    private WCat_Organization mWOrganization;

    private SelectView svContragent;
    private EditView evCode;
    private EditView evDescription;

    public static WCat_Organization_item_fragment newInstance(WCat_Organization item){
        Bundle args = new Bundle();
        args.putSerializable(ORG_ITEM_ARG,item);
        WCat_Organization_item_fragment fragment = new WCat_Organization_item_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWOrganization = (WCat_Organization)getArguments().getSerializable(ORG_ITEM_ARG);
        if(mWOrganization==null){
            mWOrganization = new WCat_Organization(getContext());
            mIsNewItem=true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.wcat_organization_item_fragment, container, false);
        svContragent = (SelectView)view.findViewById(R.id.svContragent);
        evCode = (EditView) view.findViewById(R.id.evCode);
        evDescription = (EditView) view.findViewById(R.id.evDescription);

        svContragent.setLabel("Контрагент");
        svContragent.setWCatalogItem(mWOrganization.getContragent());
        svContragent.setOnClickListener_choose(chooseListener);

        evCode.setLabel("Код");
        evCode.setText(mWOrganization.getCode());
        evCode.setEnabled(false);
        //evCode.addTextChangedListener(textChangedListener);

        evDescription.setLabel("Наименование");
        evDescription.setText(mWOrganization.getDescription());
        evDescription.addTextChangedListener(textChangedListener);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==-RESULT_OK) {
            switch (requestCode) {
                case DIALOG_REQUEST_CONTRAGENT:
                    String guid = data.getStringExtra(WCatalog_list_dialog.CATALOG_DIALOG_RESULT);
                    WCat_Contragent contragent = new WCat_Contragent_getter(getContext()).getItemByGuid(guid);
                    mWOrganization.setContragent(contragent);
                    svContragent.setWCatalogItem(mWOrganization.getContragent());
                    mIsDataChanged=true;
                    setOptionsMenuVisibility();
                    Debug.Log("RESULT",""+contragent.getDescription());
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
//--------------------------------------------------------------------------------------------------
    private View.OnClickListener chooseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment dialogFragment = new WCatalog_list_dialog();
            dialogFragment.setTargetFragment(WCat_Organization_item_fragment.this,DIALOG_REQUEST_CONTRAGENT);
            dialogFragment.show(getFragmentManager(),dialogFragment.getClass().getName());
        }
    };
    private TextWatcher textChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!mIsDataChanged) {
                mIsDataChanged = true;
                setOptionsMenuVisibility();
            }
        }
    };
    @Override
    protected void saveItem() {
        updateData();
        WCat_Organization_saver saver = new WCat_Organization_saver(getContext(),mWOrganization);
        boolean result = saver.addNewItem();
        Debug.Log("sveItem","SAVE = "+result);
        if(result){
            getActivity().finish();
        }else {
            Toast toast = Toast.makeText(getContext(),"Не удалось сохранить элемент",Toast.LENGTH_LONG);
            toast.show();
        }

    }

    @Override
    protected void updateItem() {
        updateData();
        WCat_Organization_updater updater = new WCat_Organization_updater(getContext(),mWOrganization);
        boolean result = updater.updateItem();
        Debug.Log("updateItem","UPDATE = "+result);
        if(result){
            getActivity().finish();
        }else {
            Toast toast = Toast.makeText(getContext(),"Не удалось обновить элемент",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void deleteItem() {
        WCat_Organization_updater updater = new WCat_Organization_updater(getContext(),mWOrganization);
        boolean result = updater.deleteItem();
        Debug.Log("deleteItem","DELETE = "+result);
        if(result){
            getActivity().finish();
        }else {
            Toast toast = Toast.makeText(getContext(),"Не удалось удалить элемент",Toast.LENGTH_LONG);
            toast.show();
        }
    }
    private void updateData(){
        mWOrganization.setCode(evCode.getText());
        mWOrganization.setDescription(evDescription.getText());
    }

    @Override
    protected void setOptionsMenuVisibility() {
        super.setOptionsMenuVisibility();
        MenuItem it_del = mOptionsMenu.findItem(R.id.action_item_delete);
        it_del.setVisible(!mWOrganization.isDeletionMark());

    }
}
