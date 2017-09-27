package com.wolff.wbase.model.documents.wDocuments;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.wolff.wbase.datalab.OnlineDataSender;
import com.wolff.wbase.datalab.WGetter;
import com.wolff.wbase.fragments.itemFragments.WDoc_Kassa_PKO_item_fragment;
import com.wolff.wbase.model.catalogs.wCatalog.WCatalog;
import com.wolff.wbase.model.catalogs.wCatalogs.WCat_AZS;
import com.wolff.wbase.model.catalogs.wCatalogs.WCat_Contragent;
import com.wolff.wbase.model.catalogs.wCatalogs.WCat_Currency;
import com.wolff.wbase.model.catalogs.wCatalogs.WCat_DDS;
import com.wolff.wbase.model.catalogs.wCatalogs.WCat_Dogovor;
import com.wolff.wbase.model.catalogs.wCatalogs.WCat_KindOfActivity;
import com.wolff.wbase.model.catalogs.wCatalogs.WCat_Organization;
import com.wolff.wbase.model.catalogs.wCatalogs.WCat_Podrzdelenie;
import com.wolff.wbase.model.documents.wDocument.WDocument;
import com.wolff.wbase.model.metadata.MetaCatalogs;
import com.wolff.wbase.model.metadata.MetaDocuments;
import com.wolff.wbase.tools.DateFormatTools;
import com.wolff.wbase.tools.Debug;
import com.wolff.wbase.tools.StringConvertTools;
import com.wolff.wbase.tools.XmlTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    private class WDoc_Kassa_PKO_table_row {
        private int mLineNumber;
        private WCat_DDS mDds;
        private double mSummaVal;
        private WCat_KindOfActivity mKind;
        private String mDopType;
        private WCatalog mDopObject;
        private WCat_Podrzdelenie mPodrazdel;

        WDoc_Kassa_PKO_table_row(Context context,JSONObject jsonObject){
            try {
                this.mLineNumber = jsonObject.getInt(MetaDocuments.MDoc_Kassa_PKO.TABLE.LINENUMBER);
                this.mDds = new WGetter<>(context,MetaCatalogs.MDDS.CATALOG_NAME,WCat_DDS.class).getItem(jsonObject.getString(MetaDocuments.MDoc_Kassa_PKO.TABLE.DDS));
                this.mPodrazdel = new WGetter<>(context,MetaCatalogs.MPodrazdelenie.CATALOG_NAME,WCat_Podrzdelenie.class).getItem(jsonObject.getString(MetaDocuments.MDoc_Kassa_PKO.TABLE.PODRAZDEL));
                this.mKind = new WGetter<>(context,MetaCatalogs.MKindOfActivity.CATALOG_NAME,WCat_KindOfActivity.class).getItem(jsonObject.getString(MetaDocuments.MDoc_Kassa_PKO.TABLE.KINDOFACT));
                this.mDopType = jsonObject.getString(MetaDocuments.MDoc_Kassa_PKO.TABLE.DOP_TYPE).replace(STANDARD_ODATA,"");

                this.mSummaVal = jsonObject.getDouble(MetaDocuments.MDoc_Kassa_PKO.TABLE.SUMMA_VAL);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        protected JSONObject toJson(){
            JSONObject row = new JSONObject();
            try {
                row.put(MetaDocuments.MDoc_Kassa_PKO.TABLE.DDS,mDds.getRef_Key());
                row.put(MetaDocuments.MDoc_Kassa_PKO.TABLE.SUMMA_VAL,mSummaVal);
                row.put(MetaDocuments.MDoc_Kassa_PKO.TABLE.KINDOFACT,mKind);
                row.put(MetaDocuments.MDoc_Kassa_PKO.TABLE.DOP_TYPE,mDopType);
                row.put(MetaDocuments.MDoc_Kassa_PKO.TABLE.DOP_OBJECT,mDopObject.getRef_Key());
                row.put(MetaDocuments.MDoc_Kassa_PKO.TABLE.PODRAZDEL,mPodrazdel.getRef_Key());
                row.put(MetaDocuments.MDoc_Kassa_PKO.TABLE.LINENUMBER,mLineNumber);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return row;
        }

    }

    private Context mContext;
    private static final String CATALOG_TYPE = MetaDocuments.MDoc_Kassa_PKO.DOCUMENT_NAME;

    private WCat_Organization mOrganization;    //Организация_Key

    private WCat_Currency mCurrency;            //Валюта_Key
    private WCatalog mContragent;        //Контрагент
    private String mContragentType;             //Контрагент_Type
    private double mCurrencyCourse;             //КурсВалюты
    private double mSummaVal;
    private boolean mTabl;//несколько статей ДДС

    private ArrayList<WDoc_Kassa_PKO_table_row> mTable;
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
            this.mOrganization = new WGetter<>(mContext,MetaCatalogs.MOrganization.CATALOG_NAME,WCat_Organization.class).getItem(jsonObject.getString(ORGANIZATION_KEY));
            this.mCurrency = new WGetter<>(context,MetaCatalogs.MCurrency.CATALOG_NAME,WCat_Currency.class).getItem(jsonObject.getString(CURRENCY_KEY));
            this.mContragentType = jsonObject.getString(CONTRAGENT_TYPE).replace(STANDARD_ODATA,"");
            this.mCurrencyCourse = jsonObject.getDouble(CURRENCY_COURSE);
            if(this.mContragentType.equalsIgnoreCase("Catalog_Контрагенты")) {
                this.mContragent = new WGetter<>(mContext, MetaCatalogs.MContragent.CATALOG_NAME, WCat_Contragent.class).getItem(jsonObject.getString(CONTRAGENT_KEY));
            }else if(this.mContragentType.equalsIgnoreCase("Catalog_АЗС")){
                this.mContragent = new WGetter<>(mContext, MetaCatalogs.MAZS.CATALOG_NAME, WCat_AZS.class).getItem(jsonObject.getString(CONTRAGENT_KEY));
            } else {
                Debug.Log("NO","CONTRAGENT "+mContragentType);
            }
            this.mSummaVal = jsonObject.getDouble(SUMMA_VAL);
            this.mTabl = jsonObject.getBoolean(ISTABLE);
            JSONArray jsonArrayTable = jsonObject.getJSONArray(MetaDocuments.MDoc_Kassa_PKO.HEAD.TABLE_NAME);

            this.mTable = new ArrayList<>(jsonArrayTable.length());
            for(int i=0;i<jsonArrayTable.length();i++){
                JSONObject jj = (JSONObject) jsonArrayTable.get(i);
                this.mTable.add(new WDoc_Kassa_PKO_table_row(mContext,jj));
            }

        } catch (JSONException e) {
               e.printStackTrace();
        }
        Debug.Log("CREATE DOC PKO", "" + this.getRef_Key() + "; " + this.getNumber());
    }

    public static Fragment getItemFragment(String item_key) {
        return WDoc_Kassa_PKO_item_fragment.newInstance(item_key);
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
                JSONArray currArray = new JSONArray();
                for (int i=0;i<mTable.size();i++){
                    JSONObject jj = mTable.get(i).toJson();
                    currArray.put(jj);
                }
                item.put(MetaDocuments.MDoc_Kassa_PKO.HEAD.TABLE_NAME,currArray);

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

    public WCatalog getContragent() {
        return mContragent;
    }

    public void setContragent(WCatalog contragent) {
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
