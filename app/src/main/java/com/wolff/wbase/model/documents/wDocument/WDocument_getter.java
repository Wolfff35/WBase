package com.wolff.wbase.model.documents.wDocument;

import android.content.Context;

import com.wolff.wbase.datalab.OnlineDataSender;
import com.wolff.wbase.model.aWObject.AWObject_getter;
import com.wolff.wbase.model.aWObject.WObject;
import com.wolff.wbase.tools.Const;
import com.wolff.wbase.tools.Debug;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wolff on 07.09.2017.
 */

public class WDocument_getter extends AWObject_getter {
    private Context mContext;
    public WDocument_getter(){}

    public WDocument_getter(Context context){
        this.mContext = context;
    }
    @Override
    public WObject getItem(String guid) {
        return null;
    }
    @Override
    public ArrayList<WDocument> getList(String mObjectType) {
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
    protected ArrayList<WDocument> getListFromJson(JSONObject jsonObjectList) throws JSONException {
        ArrayList<WDocument> items = new ArrayList<>();
        JSONArray jsonArray = jsonObjectList.getJSONArray(Const.JSON_SEPARATOR);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            WDocument item = new WDocument(mContext,jsonObject);
                items.add(item);
        }
        Debug.Log("LIST"," read "+items.size()+" items");
        return items;
    }
}
