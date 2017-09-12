package com.wolff.wbase.model.catalogs.wContragent;

import android.content.Context;
import android.util.Log;

import com.wolff.wbase.datalab.OnlineDataSender;
import com.wolff.wbase.model.aWObject.AWObject_getter;
import com.wolff.wbase.tools.Const;
import com.wolff.wbase.model.metadata.MetaCatalogs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.wolff.wbase.tools.Const.NULL_REF;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MCatalog.HEAD.ISFOLDER;

/**
 * Created by wolff on 04.09.2017.
 */

public class WCat_Contragent_getter extends AWObject_getter {
    //private String mObjectType;
    private Context mContext;

    public WCat_Contragent_getter(Context context){
        //this.mObjectType = MetaCatalogs.MContragent.CATALOG_NAME;
        this.mContext = context;
    }
    @Override
    public WCat_Contragent getItem(String guid) {
        if (guid.equalsIgnoreCase(NULL_REF)) {
            return null;
        }
        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        JSONObject jsonTasks = dataLab.getObjectOnline(MetaCatalogs.MContragent.CATALOG_NAME, guid);
        if (jsonTasks != null) {
            return new WCat_Contragent(mContext,jsonTasks);
        } else {
            return null;
        }
    }
    @Override
    public ArrayList<WCat_Contragent> getList(String sObjectType) {
        try {
            OnlineDataSender dataLab = OnlineDataSender.get(mContext);
            JSONObject jsonObject = dataLab.getObjectOnline(sObjectType,null);
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
    protected ArrayList<WCat_Contragent> getListFromJson(JSONObject jsonObjectList) throws JSONException {
        ArrayList<WCat_Contragent> items = new ArrayList<>();
        JSONArray jsonArray = jsonObjectList.getJSONArray(Const.JSON_SEPARATOR);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            boolean isFolder = jsonObject.getBoolean(ISFOLDER);
            if(!isFolder) {
                items.add(new WCat_Contragent(mContext,jsonObject));
            }
        }
        Log.e("ORG LIST"," read "+items.size()+" items");
        return items;
    }


}
