package com.wolff.wbase.model.documents.wDoc_Kassa_PKO;

import android.content.Context;

import com.wolff.wbase.datalab.OnlineDataSender;
import com.wolff.wbase.model.documents.wDocument.WDocument;
import com.wolff.wbase.model.catalogs.wContragent.WCat_Contragent;
import com.wolff.wbase.model.catalogs.wContragent.WCat_Contragent_getter;
import com.wolff.wbase.model.catalogs.wCurrency.WCat_Currency;
import com.wolff.wbase.model.catalogs.wCurrency.WCat_Currency_getter;
import com.wolff.wbase.model.catalogs.wOrganization.WCat_Organization;
import com.wolff.wbase.model.catalogs.wOrganization.WCat_Organization_getter;
import com.wolff.wbase.model.metadata.MetaCatalogs;
import com.wolff.wbase.model.metadata.MetaDocuments;
import com.wolff.wbase.tools.DateFormatTools;
import com.wolff.wbase.tools.Debug;
import com.wolff.wbase.tools.StringConvertTools;
import com.wolff.wbase.tools.XmlTools;

import org.json.JSONException;
import org.json.JSONObject;

import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_PATCH;
import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_POST;
import static com.wolff.wbase.model.metadata.MetaDocuments.MDoc_Kassa_PKO.HEAD.CONTRAGENT_KEY;
import static com.wolff.wbase.model.metadata.MetaDocuments.MDoc_Kassa_PKO.HEAD.CONTRAGENT_TYPE;
import static com.wolff.wbase.model.metadata.MetaDocuments.MDoc_Kassa_PKO.HEAD.CURRENCY_COURSE;
import static com.wolff.wbase.model.metadata.MetaDocuments.MDoc_Kassa_PKO.HEAD.CURRENCY_KEY;
import static com.wolff.wbase.model.metadata.MetaDocuments.MDoc_Kassa_PKO.HEAD.ISTABLE;
import static com.wolff.wbase.model.metadata.MetaDocuments.MDoc_Kassa_PKO.HEAD.ORGANIZATION_KEY;
import static com.wolff.wbase.model.metadata.MetaDocuments.MDoc_Kassa_PKO.HEAD.SUMMA_VAL;
import static com.wolff.wbase.tools.Const.STANDARD_ODATA;

/**
 * Created by wolff on 06.09.2017.
 */

public class WDoc_Kassa_PKO extends WDocument {
    private Context mContext;
    private static final String CATALOG_TYPE = MetaDocuments.MDoc_Kassa_PKO.DOCUMENT_NAME;

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
        mContext = context;

    }
    public WDoc_Kassa_PKO(Context context, JSONObject jsonObject) {
        super(context, jsonObject);
        mContext = context;
        try {
            this.mOrganization = new WCat_Organization_getter(mContext).getItem(jsonObject.getString(ORGANIZATION_KEY));
            this.mContragent = new WCat_Contragent_getter(mContext).getItem(jsonObject.getString(CONTRAGENT_KEY));
            this.mCurrency = new WCat_Currency_getter(mContext).getItem(jsonObject.getString(CURRENCY_KEY));
            this.mContragentType = jsonObject.getString(CONTRAGENT_TYPE).replace(STANDARD_ODATA,"");
            this.mCurrencyCourse = jsonObject.getDouble(CURRENCY_COURSE);
            this.mSummaVal = jsonObject.getDouble(SUMMA_VAL);
            this.mTabl = jsonObject.getBoolean(ISTABLE);

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
                    item.put(ORGANIZATION_KEY, mOrganization.getRef_Key());
                }
                if (mContragent != null) {
                    item.put(CONTRAGENT_KEY, mContragent.getRef_Key());
                    item.put(CONTRAGENT_TYPE, STANDARD_ODATA+mContragentType);
                  }
                if (mCurrency != null) {
                    item.put(CURRENCY_KEY, mCurrency.getRef_Key());
                }
                item.put(CURRENCY_COURSE,mCurrencyCourse);
                item.put(SUMMA_VAL,mSummaVal);
                item.put(ISTABLE,mTabl);

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
    //=================================================================================================
    @Override
    public boolean addNewItem(){
        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        String s1 = XmlTools.formatXmlHeader(mContext,CATALOG_TYPE);
        String s2 = formatXmlBody().toString();
        String s3 =  XmlTools.formatXmlFooter(mContext);
        String s_data = s1+s2+s3;
        return dataLab.postObjectOnline(CONNECTION_TYPE_POST,CATALOG_TYPE,null,s_data);
    }

    @Override
    public boolean updateItem() {
        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        String s_data = this.toJson(false).toString();
        return dataLab.postObjectOnline(CONNECTION_TYPE_PATCH,CATALOG_TYPE,this.getRef_Key(),s_data);

    }
    @Override
    public boolean deleteItem(){
        this.setDeletionMark(true);
        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        String s_data = this.toJson(true).toString();
        return dataLab.postObjectOnline(CONNECTION_TYPE_PATCH,CATALOG_TYPE,this.getRef_Key(),s_data);
    }

    @Override
    protected StringBuffer formatXmlBody() {
        StringBuffer sb = super.formatXmlBody();
        DateFormatTools dft = new DateFormatTools();
        StringConvertTools.addFieldToXml(sb, MetaDocuments.MDocument.HEAD.DATE, dft.dateToString(getDate(),DateFormatTools.DATE_FORMAT_STR));
        StringConvertTools.addFieldToXml(sb, MetaDocuments.MDocument.HEAD.POSTED, String.valueOf(isPosted()));

        if (getOrganization() != null) {
            StringConvertTools.addFieldToXml(sb, MetaDocuments.MDoc_Kassa_PKO.HEAD.ORGANIZATION_KEY, getOrganization().getRef_Key());
        }
        if (getContragent() != null) {

            StringConvertTools.addFieldToXml(sb, MetaDocuments.MDoc_Kassa_PKO.HEAD.CONTRAGENT_TYPE, STANDARD_ODATA+getContragentType());
            StringConvertTools.addFieldToXml(sb, MetaDocuments.MDoc_Kassa_PKO.HEAD.CONTRAGENT_KEY, getContragent().getRef_Key());
        }
        if (getCurrency() != null) {
            StringConvertTools.addFieldToXml(sb, MetaDocuments.MDoc_Kassa_PKO.HEAD.CURRENCY_KEY, getCurrency().getRef_Key());
        }
        StringConvertTools.addFieldToXml(sb, MetaDocuments.MDoc_Kassa_PKO.HEAD.CURRENCY_COURSE, String.valueOf(getCurrencyCourse()));
        StringConvertTools.addFieldToXml(sb, MetaDocuments.MDoc_Kassa_PKO.HEAD.SUMMA_VAL, String.valueOf(getSummaVal()));


        Debug.Log("formatXmlBody",""+sb.toString());
        return sb;
    }


}
