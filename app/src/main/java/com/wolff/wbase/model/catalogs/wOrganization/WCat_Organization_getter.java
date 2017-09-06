package com.wolff.wbase.model.catalogs.wOrganization;

import android.content.Context;

import com.wolff.wbase.datalab.OnlineDataSender;
import com.wolff.wbase.model.abs.AWObject_getter;
import com.wolff.wbase.model.abs.Const;
import com.wolff.wbase.model.metadata.MetaCatalogs;
import com.wolff.wbase.tools.Debug;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.wolff.wbase.model.abs.Const.NULL_REF;

/**
 * Created by wolff on 01.09.2017.
 */

public class WCat_Organization_getter extends AWObject_getter {
    private String mObjectType;
    private Context mContext;
    public WCat_Organization_getter(Context context){
        this.mObjectType = MetaCatalogs.MOrganization.CATALOG_NAME;
        this.mContext = context;
    }
    @Override
    public WCat_Organization getItemByGuid(String guid) {
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
    public ArrayList<WCat_Organization> getList() {
        try {
            OnlineDataSender dataLab = OnlineDataSender.get(mContext);
            JSONObject jsonObject = dataLab.getObjectOnline(mObjectType,null);
            if(jsonObject!=null) {
                return getListFromJson(jsonObject);
            }else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected ArrayList<WCat_Organization> getListFromJson(JSONObject jsonObjectList) throws JSONException {
        ArrayList<WCat_Organization> items = new ArrayList<>();
        JSONArray jsonArray = jsonObjectList.getJSONArray(Const.JSON_SEPARATOR);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            items.add(new WCat_Organization(mContext,jsonObject));
        }
        Debug.Log("ORG LIST"," read "+items.size()+" items");
        return items;
    }
}

