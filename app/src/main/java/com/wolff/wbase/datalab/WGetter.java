package com.wolff.wbase.datalab;

import android.content.Context;
import android.util.Log;

import com.wolff.wbase.model.aWObject.WObject;
import com.wolff.wbase.model.metadata.MetaCatalogs;
import com.wolff.wbase.tools.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static com.wolff.wbase.model.metadata.MetaCatalogs.MCatalog.HEAD.ISFOLDER;
import static com.wolff.wbase.tools.Const.NULL_REF;

/**
 * Created by wolff on 19.09.2017.
 */

public class WGetter <WType extends WObject> {
    private String mObjectType;
    private Context mContext;
    Class<? extends WType> wClass;

    public WGetter(Context context, String sObjectType, Class<? extends WType> wClass) {
        this.mObjectType = sObjectType;
        this.mContext = context;
        this.wClass = wClass;
    }

    public WType getItem(String guid) {
        if (guid.equalsIgnoreCase(NULL_REF)) {
            return null;
        }
        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        JSONObject jsonTasks = dataLab.getObjectOnline(mObjectType, guid);
        if (jsonTasks != null) {
            Class[] cArg = new Class[2];
            cArg[0] = Context.class;
            cArg[1] = JSONObject.class;
            WType instance = null;
            try {
                instance = wClass.getDeclaredConstructor(cArg).newInstance(mContext, jsonTasks);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return instance;
        } else {
            return null;
        }
    }

    public ArrayList<WType> getList() {
        try {
            OnlineDataSender dataLab = OnlineDataSender.get(mContext);
            JSONObject jsonObject = dataLab.getObjectOnline(mObjectType, null);
            if (jsonObject != null) {
                return getListFromJson(jsonObject);
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
           }
        return null;
    }


    protected ArrayList<WType> getListFromJson(JSONObject jsonObjectList) throws JSONException {
        ArrayList<WType> items = new ArrayList<>();
        JSONArray jsonArray = jsonObjectList.getJSONArray(Const.JSON_SEPARATOR);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            boolean isFolder;
            try {
                isFolder = jsonObject.getBoolean(ISFOLDER);
            } catch (Exception e) {
                isFolder = false;
            }
            if (!isFolder) {
                WType instance = getItem(jsonObject.getString(MetaCatalogs.MObject.HEAD.REF_KEY));
                items.add(instance);
            }
        }
        Log.e("LIST From JSON", " read " + items.size() + " items");
        return items;
    }
}
