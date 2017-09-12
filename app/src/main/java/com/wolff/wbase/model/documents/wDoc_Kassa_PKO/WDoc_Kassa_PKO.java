package com.wolff.wbase.model.documents.wDoc_Kassa_PKO;

import android.content.Context;

import com.wolff.wbase.model.documents.wDocument.WDocument;
import com.wolff.wbase.model.catalogs.wContragent.WCat_Contragent;
import com.wolff.wbase.model.catalogs.wContragent.WCat_Contragent_getter;
import com.wolff.wbase.model.catalogs.wCurrency.WCat_Currency;
import com.wolff.wbase.model.catalogs.wCurrency.WCat_Currency_getter;
import com.wolff.wbase.model.catalogs.wOrganization.WCat_Organization;
import com.wolff.wbase.model.catalogs.wOrganization.WCat_Organization_getter;
import com.wolff.wbase.model.metadata.MetaDocuments;
import com.wolff.wbase.tools.Debug;

import org.json.JSONException;
import org.json.JSONObject;

import static com.wolff.wbase.model.metadata.MetaDocuments.MDoc_Kassa_PKO.HEAD.CONTRAGENT_KEY;
import static com.wolff.wbase.model.metadata.MetaDocuments.MDoc_Kassa_PKO.HEAD.CONTRAGENT_TYPE;
import static com.wolff.wbase.model.metadata.MetaDocuments.MDoc_Kassa_PKO.HEAD.CURRENCY_COURSE;
import static com.wolff.wbase.model.metadata.MetaDocuments.MDoc_Kassa_PKO.HEAD.CURRENCY_KEY;
import static com.wolff.wbase.model.metadata.MetaDocuments.MDoc_Kassa_PKO.HEAD.ORGANIZATION_KEY;
import static com.wolff.wbase.model.metadata.MetaDocuments.MDoc_Kassa_PKO.HEAD.SUMMA_VAL;

/**
 * Created by wolff on 06.09.2017.
 */

public class WDoc_Kassa_PKO extends WDocument {
    private Context mContext;

    private WCat_Organization mOrganization;    //Организация_Key

    private WCat_Currency mCurrency;            //Валюта_Key
    private WCat_Contragent mContragent;        //Контрагент
    private String mContragentType;             //Контрагент_Type
    private double mCurrencyCourse;             //КурсВалюты
    private double mSummaVal;
    private boolean mTabl;//несколько статей ДДС
    //Договор
    //Касса

    public WDoc_Kassa_PKO(Context context) {
        super(context);

    }
    public WDoc_Kassa_PKO(Context context, JSONObject jsonObject) {
        super(context, jsonObject);
        try {
            this.mOrganization = new WCat_Organization_getter(mContext).getItem(jsonObject.getString(ORGANIZATION_KEY));
            this.mContragent = new WCat_Contragent_getter(mContext).getItem(jsonObject.getString(CONTRAGENT_KEY));
            this.mCurrency = new WCat_Currency_getter(mContext).getItem(jsonObject.getString(CURRENCY_KEY));
            this.mContragentType = jsonObject.getString(CONTRAGENT_TYPE).replace("StandardODATA.","");
            this.mCurrencyCourse = jsonObject.getDouble(CURRENCY_COURSE);
            this.mSummaVal = jsonObject.getDouble(SUMMA_VAL);
            this.mTabl = jsonObject.getBoolean("фНесколькоСтатейДДС");

        } catch (JSONException e) {
               e.printStackTrace();
        }
        Debug.Log("CREATE DOC PKO", "" + this.getRef_Key() + "; " + this.getNumber());
    }

    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = super.toJson(onlyDeletionMark);
        if (!onlyDeletionMark) {
            try {
                if (mOrganization != null) {
                    item.put("Организация_Key", mOrganization.getRef_Key());
                }
                if (mContragent != null) {
                    item.put("Контрагент", mContragent.getRef_Key());
                    item.put("Контрагент_Type", "StandardODATA."+mContragentType);
                  }
                if (mCurrency != null) {
                    item.put("Валюта_Key", mCurrency.getRef_Key());
                }
                item.put("КурсВалюты",mCurrencyCourse);
                item.put("СуммаВал",mSummaVal);
                item.put("фНесколькоСтатейДДС",mTabl);

            } catch (JSONException e) {
                      e.printStackTrace();
            }
        }
        Debug.Log("toJson", "" + item.toString());
        return item;

    }
//----

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public WCat_Organization getOrganization() {
        return mOrganization;
    }

    public void setOrganization(WCat_Organization organization) {
        mOrganization = organization;
    }

    public WCat_Currency getCurrency() {
        return mCurrency;
    }

    public void setCurrency(WCat_Currency currency) {
        mCurrency = currency;
    }

    public WCat_Contragent getContragent() {
        return mContragent;
    }

    public void setContragent(WCat_Contragent contragent) {
        mContragent = contragent;
    }

    public String getContragentType() {
        return mContragentType;
    }

    public void setContragentType(String contragentType) {
        mContragentType = contragentType;
    }

    public double getCurrencyCourse() {
        return mCurrencyCourse;
    }

    public void setCurrencyCourse(double currencyCourse) {
        mCurrencyCourse = currencyCourse;
    }

    public double getSummaVal() {
        return mSummaVal;
    }

    public void setSummaVal(double summaVal) {
        mSummaVal = summaVal;
    }

    public boolean isTabl() {
        return mTabl;
    }

    public void setTabl(boolean tabl) {
        mTabl = tabl;
    }
}
