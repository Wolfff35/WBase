package com.wolff.wbase.model.catalogs.wOrganization;

import android.content.Context;

import com.wolff.wbase.datalab.OnlineDataSender;
import com.wolff.wbase.model.catalogs.wCatalog.WCatalog_getter;
import com.wolff.wbase.model.metadata.MetaCatalogs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.wolff.wbase.tools.Const.NULL_REF;

/**
 * Created by wolff on 01.09.2017.
 */

public class WCat_Organization_getter extends WCatalog_getter {
    private String mObjectType;
    private Context mContext;
    public WCat_Organization_getter(Context context){
        this.mObjectType = MetaCatalogs.MOrganization.CATALOG_NAME;
        this.mContext = context;
    }
    @Override
    public WCat_Organization getItem(String guid) {
        if(guid.isEmpty()){
            return null;
        }
        if(guid.equalsIgnoreCase(NULL_REF)){
            return null;
        }

        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        JSONObject jsonTasks = dataLab.getObjectOnline(mObjectType,guid);
        if (jsonTasks!=null) {
            return new WCat_Organization(mContext,jsonTasks);
        }else {
            return null;
        }
    }

    @Override
    public ArrayList getList(String sTypeObject) {
        return super.getList(sTypeObject);
    }

    @Override
    protected ArrayList getListFromJson(JSONObject jsonObjectList) throws JSONException {
        return super.getListFromJson(jsonObjectList);
    }
}

