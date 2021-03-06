package com.wolff.wbase.model.catalogs.wCatalogs;

import android.content.Context;

import com.wolff.wbase.datalab.OnlineDataSender;
import com.wolff.wbase.model.catalogs.wCatalog.WCatalog;
import com.wolff.wbase.model.metadata.MetaCatalogs;
import com.wolff.wbase.tools.Debug;
import com.wolff.wbase.tools.XmlTools;

import org.json.JSONObject;

import java.io.Serializable;

import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_PATCH;
import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_POST;

/**
 * Created by wolff on 06.09.2017.
 */

public class WCat_DDS extends WCatalog implements Serializable {
    private Context mContext;
    private static final String CATALOG_TYPE = MetaCatalogs.MDDS.CATALOG_NAME;
    public WCat_DDS(Context context){
        mContext=context;
    }

    public WCat_DDS(Context context, JSONObject jsonObject) {
        super(context,jsonObject);
        //mContext=context;
        Debug.Log("CREATE DDS",""+this.getRef_Key()+"; "+this.getDescription());
    }


    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = super.toJson(onlyDeletionMark);
        Debug.Log("toJson", "" + item.toString());
        return item;
    }

    //=================================================================================================
    @Override
    public boolean addNewItem(){
        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        String s_data = XmlTools.formatXmlHeader(mContext, CATALOG_TYPE)+formatXmlBody().toString()+ XmlTools.formatXmlFooter(mContext);
        return dataLab.postObjectOnline(CONNECTION_TYPE_POST, CATALOG_TYPE,null,s_data);
    }

    @Override
    public boolean updateItem(){
        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        String s_data = this.toJson(false).toString();
        return dataLab.postObjectOnline(CONNECTION_TYPE_PATCH, CATALOG_TYPE,this.getRef_Key(),s_data);
    }

    @Override
    public boolean deleteItem(){
        this.setDeletionMark(true);
        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        String s_data = this.toJson(true).toString();
        return dataLab.postObjectOnline(CONNECTION_TYPE_PATCH, CATALOG_TYPE,this.getRef_Key(),s_data);
    }

    @Override
    protected StringBuffer formatXmlBody() {
        StringBuffer sb = super.formatXmlBody();
         return sb;
    }

}

