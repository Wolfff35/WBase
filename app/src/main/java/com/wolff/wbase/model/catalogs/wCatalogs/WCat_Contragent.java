package com.wolff.wbase.model.catalogs.wCatalogs;

import android.content.Context;

import com.wolff.wbase.datalab.OnlineDataSender;
import com.wolff.wbase.model.catalogs.wCatalog.WCatalog;
import com.wolff.wbase.model.metadata.MetaCatalogs;
import com.wolff.wbase.tools.Debug;
import com.wolff.wbase.tools.StringConvertTools;
import com.wolff.wbase.tools.XmlTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_PATCH;
import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_POST;

/**
 * Created by wolff on 28.08.2017.
 */

public class WCat_Contragent extends WCatalog implements Serializable {
    private String name_short;
    private String name_full;
    private Context mContext;

    private static final String CATALOG_TYPE = MetaCatalogs.MContragent.CATALOG_NAME;

    public WCat_Contragent(Context context){
        this.mContext = context;
    }
    public WCat_Contragent(Context context,JSONObject jsonObject) {
        super(context,jsonObject);
        this.mContext=context;
        try {
            this.name_short = jsonObject.getString(MetaCatalogs.MContragent.HEAD.NAME_SHORT);
            this.name_full = jsonObject.getString(MetaCatalogs.MContragent.HEAD.NAME_FULL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Debug.Log("CREATE CONTRAGENT",""+this.getRef_Key()+"; "+this.getDescription());

    }
    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = super.toJson(onlyDeletionMark);
        if(!onlyDeletionMark) {
            try {
                item.put(MetaCatalogs.MContragent.HEAD.NAME_FULL,this.name_full);
                item.put(MetaCatalogs.MContragent.HEAD.NAME_SHORT,this.name_short);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Debug.Log("toJson",""+item.toString());
        return item;
    }

    public String getName_short() {
        return name_short;
    }

    public void setName_short(String name_short) {
        this.name_short = name_short;
    }

    public String getName_full() {
        return name_full;
    }

    public void setName_full(String name_full) {
        this.name_full = name_full;
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
        StringConvertTools.addFieldToXml(sb, MetaCatalogs.MContragent.HEAD.NAME_FULL, getName_full());
        StringConvertTools.addFieldToXml(sb, MetaCatalogs.MContragent.HEAD.NAME_SHORT, getName_short());
        Debug.Log("formatXmlBody",""+sb.toString());
        return sb;
    }

}
