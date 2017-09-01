package com.wolff.wbase.model.catalogs.wOrganization;

import android.content.Context;
import android.util.Log;

import com.wolff.wbase.datalab.OnlineDataLab;
import com.wolff.wbase.model.abs.AWObject_getter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.wolff.wbase.datalab.OnlineDataLab.CATALOG_ORGANIZATION;
import static com.wolff.wbase.model.abs.Const.NULL_REF;

/**
 * Created by wolff on 01.09.2017.
 */

public class WOrganization_getter extends AWObject_getter {
    private String mObjectType;
    private Context mContext;
    public WOrganization_getter(Context context){
        this.mObjectType = CATALOG_ORGANIZATION;
        this.mContext = context;
    }
    @Override
    public WOrganization getItemByGuid(String guid) {
        if(guid.equalsIgnoreCase(NULL_REF)){
            return null;
        }
        OnlineDataLab dataLab = OnlineDataLab.get(mContext);
        JSONObject jsonTasks = dataLab.getObjectOnline(mObjectType,guid);
        if (jsonTasks!=null) {
            return new WOrganization(mContext, jsonTasks);
        }else {
            return null;
        }
    }

    @Override
    public ArrayList<WOrganization> getList() {
        try {
            OnlineDataLab dataLab = OnlineDataLab.get(mContext);
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
    protected ArrayList<WOrganization> getListFromJson(JSONObject jsonObjectList) throws JSONException {
        ArrayList<WOrganization> items = new ArrayList<>();
        JSONArray jsonArray = jsonObjectList.getJSONArray("value");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            items.add(new WOrganization(mContext,jsonObject));
        }
        Log.e("ORG LIST"," read "+items.size()+" items");
        return items;
    }
}

