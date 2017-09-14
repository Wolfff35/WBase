package com.wolff.wbase.model.documents.wDoc_Kassa_PKO;

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
import android.widget.CheckBox;
import android.widget.Toast;

import com.wolff.wbase.R;
import com.wolff.wbase.model.catalogs.wCurrency.WCat_Currency;
import com.wolff.wbase.model.catalogs.wCurrency.WCat_Currency_getter;
import com.wolff.wbase.model.catalogs.wOrganization.WCat_Organization_getter;
import com.wolff.wbase.tools.DateFormatTools;
import com.wolff.wbase.tools.custom_views.EditView;
import com.wolff.wbase.tools.custom_views.SelectStringArrayView;
import com.wolff.wbase.tools.custom_views.SelectWCatalogView;
import com.wolff.wbase.model.catalogs.wCatalog.WCatalog_list_dialog;
import com.wolff.wbase.model.catalogs.wContragent.WCat_Contragent;
import com.wolff.wbase.model.catalogs.wContragent.WCat_Contragent_getter;
import com.wolff.wbase.model.catalogs.wOrganization.WCat_Organization;
import com.wolff.wbase.model.documents.wDocument.WDocument_item_fragment;
import com.wolff.wbase.model.metadata.MetaCatalogs;
import com.wolff.wbase.tools.Debug;
import com.wolff.wbase.tools.dialogs.String_list_dialog;

import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.wolff.wbase.tools.Const.PREFIX_CATALOG;

/**
 * Created by wolff on 04.09.2017.
 */

public class WDoc_Kassa_PKO_item_fragment extends WDocument_item_fragment {
    public static final String ORG_ITEM_ARG = "W_PKO";
    private static final int DIALOG_REQUEST_ORGANIZATION = 1;
    private static final int DIALOG_REQUEST_CONTRAGENT_TYPE = 2;
    private static final int DIALOG_REQUEST_CONTRAGENT = 3;
    private static final int DIALOG_REQUEST_CURRENCY = 4;

    private ArrayList<String> mContrsgentTypes = new ArrayList<>(); //  Arrays.asList(new String[]{"Контрагент","АЗС"});
    private String mOldContragentType;
    private WDoc_Kassa_PKO mWDoc;

    private SelectWCatalogView svOrganization;
    private SelectWCatalogView svContragent;
    private SelectWCatalogView svCurrency;
    private SelectStringArrayView ssaContragentType;

    private EditView evCurrencyCourse;
    private EditView evSummaVal;

    private EditView evNumber;
    private EditView evDate;

    private CheckBox cbTable;

    public static WDoc_Kassa_PKO_item_fragment newInstance(String item_key){
        Bundle args = new Bundle();
        args.putSerializable(ORG_ITEM_ARG,item_key);
        WDoc_Kassa_PKO_item_fragment fragment = new WDoc_Kassa_PKO_item_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContrsgentTypes.add(MetaCatalogs.MContragent.CATALOG_NAME.replace(PREFIX_CATALOG,""));
        mContrsgentTypes.add(MetaCatalogs.MAZS.CATALOG_NAME.replace(PREFIX_CATALOG,""));

        String item_key = getArguments().getString(ORG_ITEM_ARG);
        if(item_key!=null&&!item_key.isEmpty()){
            mWDoc = new WDoc_Kassa_PKO_getter(getContext()).getItem(item_key);
        }
        if(mWDoc==null){
            mWDoc = new WDoc_Kassa_PKO(getContext());
            mWDoc.setDate(new Date());
            mIsNewItem=true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.wdoc_kassa_pko_item_fragment, container, false);

        svOrganization = (SelectWCatalogView)view.findViewById(R.id.svOrganization);
        svContragent = (SelectWCatalogView)view.findViewById(R.id.svContragent);
        ssaContragentType = (SelectStringArrayView)view.findViewById(R.id.svContragentType);
        svCurrency = (SelectWCatalogView)view.findViewById(R.id.svCurrency);

        evNumber = (EditView) view.findViewById(R.id.evNumber);
        evDate = (EditView) view.findViewById(R.id.evDate);

        evCurrencyCourse = (EditView) view.findViewById(R.id.evCurrencyCourse);
        evSummaVal = (EditView) view.findViewById(R.id.evSummaVal);

        cbTable = (CheckBox) view.findViewById(R.id.cbTable);
//------------
        DateFormatTools dft = new DateFormatTools();

        evNumber.setLabel("№");
        evNumber.setText(mWDoc.getNumber());
        evNumber.setEnabled(false);

        evDate.setLabel("от ");
        evDate.setText(dft.dateToString(mWDoc.getDate(),DateFormatTools.DATE_FORMAT_VID_FULL));
        evDate.addTextChangedListener(textChangedListener);

        if(mIsNewItem){
            evNumber.setVisibility(View.INVISIBLE);
        }else {
            evNumber.setVisibility(View.VISIBLE);
        }

        svOrganization.setLabel("Организация");
        svOrganization.setWCatalogItem(mWDoc.getOrganization());
        svOrganization.setOnClickListener_choose(chooseOrgListener);
        svOrganization.setOnClickListener_clear(clearOrgListener);


        ssaContragentType.setLabel("Тип плательщика");
        if(mWDoc!=null) {
            if(mWDoc.getContragentType()!=null) {
                mOldContragentType = mWDoc.getContragentType().replace(PREFIX_CATALOG, "");
            }
        }
        ssaContragentType.setItem(mOldContragentType);
        ssaContragentType.setOnClickListener_choose(chooseContragentTypeListener);
        ssaContragentType.setOnClickListener_clear(clearContragentTypeListener);

        svContragent.setLabel("Плательщик");
        svContragent.setWCatalogItem(mWDoc.getContragent());
        svContragent.setOnClickListener_choose(chooseContragentListener);
        svContragent.setOnClickListener_clear(clearContragentListener);

        svCurrency.setLabel("Валюта");
        svCurrency.setWCatalogItem(mWDoc.getCurrency());
        svCurrency.setOnClickListener_choose(chooseCurrencyListener);
        svCurrency.setOnClickListener_clear(clearCurrencyListener);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) {
            switch (requestCode) {
                case DIALOG_REQUEST_ORGANIZATION: {
                    String guid = data.getStringExtra(WCatalog_list_dialog.CATALOG_DIALOG_RESULT);
                    WCat_Organization organization = new WCat_Organization_getter(getContext()).getItem(guid);
                    mWDoc.setOrganization(organization);
                    svOrganization.setWCatalogItem(mWDoc.getOrganization());
                    mIsDataChanged = true;
                    setOptionsMenuVisibility();
                    Debug.Log("RESULT", "" + organization.getDescription());
                    break;
                }
                case DIALOG_REQUEST_CONTRAGENT: {
                    String guid = data.getStringExtra(WCatalog_list_dialog.CATALOG_DIALOG_RESULT);
                    switch ("Catalog_"+ ssaContragentType.getItem()){
                        case MetaCatalogs.MContragent.CATALOG_NAME:
                            WCat_Contragent contragent = new WCat_Contragent_getter(getContext()).getItem(guid);
                            mWDoc.setContragent(contragent);
                            break;
                        case MetaCatalogs.MAZS.CATALOG_NAME:
                            //WCat_Contragent contragent = new WCat_Contragent_getter(getContext()).getItem(guid);
                            //mWDoc.setContragent(contragent);
                            break;
                    }
                    svContragent.setWCatalogItem(mWDoc.getContragent());
                    mIsDataChanged = true;
                    setOptionsMenuVisibility();
                    //Debug.Log("RESULT", "" + contragent.getDescription());
                    break;
                }
                case DIALOG_REQUEST_CONTRAGENT_TYPE: {
                    String contragentType = data.getStringExtra(WCatalog_list_dialog.CATALOG_DIALOG_RESULT);
                    if(!contragentType.equalsIgnoreCase(mOldContragentType)) {
                        mWDoc.setContragentType(contragentType);
                        ssaContragentType.setItem(mWDoc.getContragentType());//.replace("Catalog_",""));
                        mWDoc.setContragent(null);
                        svContragent.setWCatalogItem(null);
                        mIsDataChanged = true;
                        setOptionsMenuVisibility();
                        Debug.Log("RESULT", "" + contragentType);
                    }
                    break;
                }
                case DIALOG_REQUEST_CURRENCY: {
                    String guid = data.getStringExtra(WCatalog_list_dialog.CATALOG_DIALOG_RESULT);
                    WCat_Currency currency = new WCat_Currency_getter(getContext()).getItem(guid);
                    mWDoc.setCurrency(currency);
                    svCurrency.setWCatalogItem(mWDoc.getCurrency());
                    mIsDataChanged = true;
                    setOptionsMenuVisibility();
                    Debug.Log("RESULT", "" + currency.getDescription());
                    break;
                }

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
//--------------------------------------------------------------------------------------------------
    private View.OnClickListener chooseOrgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment dialogFragment = WCatalog_list_dialog.newInstance(MetaCatalogs.MOrganization.CATALOG_NAME);
            dialogFragment.setTargetFragment(WDoc_Kassa_PKO_item_fragment.this,DIALOG_REQUEST_ORGANIZATION);
            dialogFragment.show(getFragmentManager(),dialogFragment.getClass().getName());
        }
    };
    private View.OnClickListener clearOrgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mWDoc.setOrganization(null);
            svOrganization.setWCatalogItem(null);
            mIsDataChanged=true;
            setOptionsMenuVisibility();
        }
    };
    private View.OnClickListener chooseContragentTypeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mOldContragentType =mWDoc.getContragentType();//.replace("Catalog_","");

            DialogFragment dialogFragment = String_list_dialog.newInstance(mContrsgentTypes);
            dialogFragment.setTargetFragment(WDoc_Kassa_PKO_item_fragment.this,DIALOG_REQUEST_CONTRAGENT_TYPE);
            dialogFragment.show(getFragmentManager(),dialogFragment.getClass().getName());
        }
    };
    private View.OnClickListener clearContragentTypeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mWDoc.setContragentType(null);
            ssaContragentType.setItem(null);
            mWDoc.setContragent(null);
            svContragent.setWCatalogItem(null);
            mIsDataChanged=true;
            setOptionsMenuVisibility();
        }
    };

    private View.OnClickListener chooseContragentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment dialogFragment;
            dialogFragment = WCatalog_list_dialog.newInstance(PREFIX_CATALOG+ ssaContragentType.getItem());
            dialogFragment.setTargetFragment(WDoc_Kassa_PKO_item_fragment.this,DIALOG_REQUEST_CONTRAGENT);
            dialogFragment.show(getFragmentManager(),dialogFragment.getClass().getName());
        }
    };
    private View.OnClickListener clearContragentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mWDoc.setContragent(null);
            svContragent.setWCatalogItem(null);
            mIsDataChanged=true;
            setOptionsMenuVisibility();
        }
    };
    private View.OnClickListener chooseCurrencyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment dialogFragment = WCatalog_list_dialog.newInstance(MetaCatalogs.MCurrency.CATALOG_NAME);
            dialogFragment.setTargetFragment(WDoc_Kassa_PKO_item_fragment.this,DIALOG_REQUEST_CURRENCY);
            dialogFragment.show(getFragmentManager(),dialogFragment.getClass().getName());
        }
    };
    private View.OnClickListener clearCurrencyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mWDoc.setCurrency(null);
            svCurrency.setWCatalogItem(null);
            mIsDataChanged=true;
            setOptionsMenuVisibility();
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
        boolean result = mWDoc.addNewItem();
        Debug.Log("sveItem","SAVE = "+result);
        if(result){
            getActivity().finish();
        }else {
            Toast toast = Toast.makeText(getContext(),"Не удалось сохранить документ",Toast.LENGTH_LONG);
            toast.show();
        }

    }

    @Override
    protected void updateItem() {
        updateData();
        boolean result = mWDoc.updateItem();
        Debug.Log("updateItem","UPDATE = "+result);
        if(result){
            getActivity().finish();
        }else {
            Toast toast = Toast.makeText(getContext(),"Не удалось обновить документ",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void deleteItem() {
        boolean result = mWDoc.deleteItem();
        Debug.Log("deleteItem","DELETE = "+result);
        if(result){
            getActivity().finish();
        }else {
            Toast toast = Toast.makeText(getContext(),"Не удалось удалить документ",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void postItem() {

    }

    private void updateData(){
        DateFormatTools dft = new DateFormatTools();
        mWDoc.setNumber(evNumber.getText());
        mWDoc.setDate(dft.dateFromString(evDate.getText(),DateFormatTools.DATE_FORMAT_VID_FULL));
        mWDoc.setContragentType("Catalog_"+ ssaContragentType.getItem());
    }

    @Override
    protected void setOptionsMenuVisibility() {
        super.setOptionsMenuVisibility();
        MenuItem it_del = mOptionsMenu.findItem(R.id.action_item_delete);
        it_del.setVisible(!mWDoc.isDeletionMark());
     }
}
