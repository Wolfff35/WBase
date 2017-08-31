package com.wolff.wbase.model.catalogs.wTask;

import android.content.Context;

import com.wolff.wbase.datalab.OnlineDataLab;
import com.wolff.wbase.model.abs.AWObjectGetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.wolff.wbase.datalab.OnlineDataLab.CATALOG_TASKS;
import static com.wolff.wbase.model.abs.Const.NULL_REF;

/**
 * Created by wolff on 29.08.2017.
 */

public class WTaskGetter extends AWObjectGetter {
    private String mObjectType;
    public WTaskGetter(){
        mObjectType = CATALOG_TASKS;
    }
    @Override
    public WTask getItemByGuid(Context context, String guid) {
        if(guid.equalsIgnoreCase(NULL_REF)){
            return null;
        }
        OnlineDataLab dataLab = OnlineDataLab.get(context);
        JSONObject jsonTasks = dataLab.getObjectOnline(mObjectType,guid);
        if (jsonTasks!=null) {
            return new WTask(context, jsonTasks);
        }else {
            return null;
        }
    }

    @Override
    public ArrayList<WTask> getList(Context context) {
        try {
            OnlineDataLab dataLab = OnlineDataLab.get(context);
            JSONObject jsonUsers = dataLab.getObjectOnline(mObjectType,null);
            if(jsonUsers!=null) {
                return getListFromJson(context, jsonUsers);
            }else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected ArrayList<WTask> getListFromJson(Context context,JSONObject jsonObjectList) throws JSONException {
        ArrayList<WTask> tasks = new ArrayList<>();
        JSONArray jsonArray = jsonObjectList.getJSONArray("value");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            tasks.add(new WTask(context,jsonObject));
        }
        return tasks;
    }
}
