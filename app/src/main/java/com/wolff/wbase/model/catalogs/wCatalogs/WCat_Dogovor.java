package com.wolff.wbase.model.catalogs.wCatalogs;

import android.content.Context;

import com.wolff.wbase.datalab.OnlineDataSender;
import com.wolff.wbase.datalab.WGetter;
import com.wolff.wbase.model.catalogs.wCatalog.WCatalog;
import com.wolff.wbase.model.metadata.MetaCatalogs;
import com.wolff.wbase.tools.Debug;
import com.wolff.wbase.tools.XmlTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_PATCH;
import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_POST;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MDogovor.HEAD.ORGANIZATION_KEY;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MOrganization.HEAD.CONTRAGENT_KEY;

/**
 * Created by wolff on 01.09.2017.
 */

public class WCat_Dogovor extends WCatalog implements Serializable {

    private class Currency_Table_row{
        private WCat_Currency mCurrency;
        private int mLineNumber;
        Currency_Table_row(Context context,JSONObject jsonObject){
            try {
                this.mLineNumber = jsonObject.getInt("LineNumber");
                this.mCurrency = new WGetter<>(context,MetaCatalogs.MCurrency.CATALOG_NAME,WCat_Currency.class).getItem(jsonObject.getString(MetaCatalogs.MDogovor.Currency_Table.TABLE.CURRENCY_DOG));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        protected JSONObject toJson(){
            JSONObject row = new JSONObject();
            try {
                row.put(MetaCatalogs.MDogovor.Currency_Table.TABLE.CURRENCY_DOG,mCurrency.getRef_Key());
                row.put("LineNumber",mLineNumber);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return row;
        }
    }

    private class KindOfActivity_Table_row{
        private WCat_KindOfActivity mKindOfActivity;
        KindOfActivity_Table_row(Context context,JSONObject jsonObject){
            try {
                //this.mKindOfActivity = new WCat_KindOfActivity_getterr(context).getItem(jsonObject.getString(MetaCatalogs.MDogovor.KindOfActivity_Table.TABLE.KINDOFACTIVITY));
                this.mKindOfActivity = new WGetter<>(context,MetaCatalogs.MKindOfActivity.CATALOG_NAME,WCat_KindOfActivity.class)
                        .getItem(jsonObject.getString(MetaCatalogs.MDogovor.KindOfActivity_Table.TABLE.KINDOFACTIVITY));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private Context mContext;
    private static final String CATALOG_TYPE = MetaCatalogs.MDogovor.CATALOG_NAME;

    private WCat_Organization mOrganization;
    private WCat_Contragent mContragent;
    private ArrayList<KindOfActivity_Table_row> mKindOfActivityTable;
    private ArrayList<Currency_Table_row> mCurrencyTable;


    public WCat_Dogovor(Context context){
        mContext=context;
    }

    public WCat_Dogovor(Context context, JSONObject jsonObject) {
        super(context,jsonObject);
        mContext=context;
        try {
            //this.mOrganization = new WCat_Organization_getterr(mContext).getItem(jsonObject.getString(ORGANIZATION_KEY));
            this.mOrganization = new WGetter<>(mContext,MetaCatalogs.MOrganization.CATALOG_NAME,WCat_Organization.class).getItem(jsonObject.getString(ORGANIZATION_KEY));
            //this.mContragent = new WCat_Contragent_getterr(mContext).getItem(jsonObject.getString(CONTRAGENT_KEY));
            this.mContragent = new WGetter<>(mContext,MetaCatalogs.MContragent.CATALOG_NAME,WCat_Contragent.class).getItem(jsonObject.getString(CONTRAGENT_KEY));
            JSONArray jsonArrayCurrency = jsonObject.getJSONArray(MetaCatalogs.MDogovor.Currency_Table.TABLE_NAME);
            JSONArray jsonArrayKind = jsonObject.getJSONArray(MetaCatalogs.MDogovor.KindOfActivity_Table.TABLE_NAME);

            this.mCurrencyTable = new ArrayList<>(jsonArrayCurrency.length());
            for(int i=0;i<jsonArrayCurrency.length();i++){
                JSONObject jj = (JSONObject) jsonArrayCurrency.get(i);
                this.mCurrencyTable.add(new Currency_Table_row(mContext,jj));
            }
            this.mKindOfActivityTable = new ArrayList<>(jsonArrayCurrency.length());
            for(int i=0;i<jsonArrayKind.length();i++){
                JSONObject jj = (JSONObject) jsonArrayKind.get(i);
                this.mKindOfActivityTable.add(new KindOfActivity_Table_row(mContext,jj));
            }

            Debug.Log("ROW CURR","OK");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Debug.Log("CREATE DOG",""+this.getRef_Key()+"; "+this.getDescription());
    }


    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = super.toJson(onlyDeletionMark);
        if(!onlyDeletionMark) {
            try {
                if (mOrganization != null) {
                    item.put(ORGANIZATION_KEY, mOrganization.getRef_Key());
                }
                if (mContragent != null) {
                    item.put(CONTRAGENT_KEY, mContragent.getRef_Key());
                }
                JSONArray currArray = new JSONArray();
                for (int i=0;i<mCurrencyTable.size();i++){
                    //JSONObject jj = mCurrencyTable.get(i).toJson(getRef_Key());
                    JSONObject jj = mCurrencyTable.get(i).toJson();
                    currArray.put(jj);
                }
                item.put(MetaCatalogs.MDogovor.Currency_Table.TABLE_NAME,currArray);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Debug.Log("toJson",""+item.toString());
        return item;
    }

    public WCat_Contragent getContragent() {
        return mContragent;
    }

    public void setContragent(WCat_Contragent contragent) {
        mContragent = contragent;
    }

 //=================================================================================================
    @Override
    public boolean addNewItem(){
        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        String s_data = XmlTools.formatXmlHeader(mContext,CATALOG_TYPE)+formatXmlBody().toString()+ XmlTools.formatXmlFooter(mContext);
        return dataLab.postObjectOnline(CONNECTION_TYPE_POST,CATALOG_TYPE,null,s_data);
    }

    @Override
    public boolean updateItem(){
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
        //if (getContragent() != null) {
        //    StringConvertTools.addFieldToXml(sb, CONTRAGENT_KEY, getContragent().getRef_Key());
        //}
        Debug.Log("formatXmlBody",""+sb.toString());
        return sb;
    }

}
