package com.wolff.wbase.model.catalogs.wOrganization;

import android.content.Context;

import com.wolff.wbase.datalab.OnlineDataSender;
import com.wolff.wbase.model.catalogs.wContragent.WCat_Contragent_getter;
import com.wolff.wbase.tools.Const;
import com.wolff.wbase.tools.XmlTools;
import com.wolff.wbase.model.abs.WCatalog;
import com.wolff.wbase.model.catalogs.wContragent.WCat_Contragent;
import com.wolff.wbase.model.metadata.MetaCatalogs;
import com.wolff.wbase.tools.Debug;
import com.wolff.wbase.tools.StringConvertTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_PATCH;
import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_POST;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MOrganization.HEAD.CONTRAGENT_KEY;

/**
 * Created by wolff on 01.09.2017.
 */

public class WCat_Organization extends WCatalog implements Serializable {
    private WCat_Contragent mContragent;
    private Context mContext;
    private String mPrefix;

    public WCat_Organization(Context context){
        mContext=context;
    }

    public WCat_Organization(Context context, JSONObject jsonObject) {
        super(context,jsonObject);
        mContext=context;
        try {
            this.mPrefix = jsonObject.getString(MetaCatalogs.MOrganization.HEAD.PREFIX);
            this.mContragent = new WCat_Contragent_getter(mContext).getItem(jsonObject.getString(CONTRAGENT_KEY));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Debug.Log("CREATE ORG",""+this.getRef_Key()+"; "+this.getDescription());
    }


    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = super.toJson(onlyDeletionMark);
        if(!onlyDeletionMark) {
            try {
                if (mContragent != null) {
                    item.put(CONTRAGENT_KEY, mContragent.getRef_Key());
                }else {
                    item.put(CONTRAGENT_KEY, Const.NULL_REF);
                }
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
        String s_data = XmlTools.formatXmlHeader(mContext,MetaCatalogs.MOrganization.CATALOG_NAME)+formatXmlBody().toString()+ XmlTools.formatXmlFooter(mContext);
        return dataLab.postObjectOnline(CONNECTION_TYPE_POST,MetaCatalogs.MOrganization.CATALOG_NAME,null,s_data);
    }

    @Override
    public boolean updateItem(){
        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        String s_data = this.toJson(false).toString();
        return dataLab.postObjectOnline(CONNECTION_TYPE_PATCH,MetaCatalogs.MOrganization.CATALOG_NAME,this.getRef_Key(),s_data);
    }

    @Override
    public boolean deleteItem(){
        this.setDeletionMark(true);
        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        String s_data = this.toJson(true).toString();
        return dataLab.postObjectOnline(CONNECTION_TYPE_PATCH,MetaCatalogs.MOrganization.CATALOG_NAME,this.getRef_Key(),s_data);
    }

    @Override
    protected StringBuffer formatXmlBody() {
        StringBuffer sb = super.formatXmlBody();
        if (getContragent() != null) {
            StringConvertTools.addFieldToXml(sb, CONTRAGENT_KEY, getContragent().getRef_Key());
        }
        Debug.Log("formatXmlBody",""+sb.toString());
        return sb;
    }

}
