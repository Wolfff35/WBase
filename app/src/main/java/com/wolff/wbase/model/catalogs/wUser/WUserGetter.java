package com.wolff.wbase.model.catalogs.wUser;

import android.content.Context;
import android.util.Log;

import com.wolff.wbase.datalab.OnlineDataLab;
import com.wolff.wbase.model.abs.AWObjectGetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import static com.wolff.wbase.datalab.OnlineDataLab.CATALOG_USERS;
import static com.wolff.wbase.model.abs.Const.NULL_REF;

/**
 * Created by wolff on 29.08.2017.
 */

public class WUserGetter extends AWObjectGetter {

    @Override
    public WUser getItemByGuid(Context context, String guid){
        if(guid.equalsIgnoreCase(NULL_REF)){
            return null;
        }
        OnlineDataLab dataLab = OnlineDataLab.get(context);
        JSONObject jsonUsers = dataLab.getObjectOnline(CATALOG_USERS,guid);
        //Log.e("GET USER","GUID = "+guid);
        //Log.e("GET USER",""+jsonUsers.toString());
        if(jsonUsers!=null) {
            return new WUser(jsonUsers);
        }else {
            return null;
        }
    }

    @Override
    public ArrayList<WUser> getList(Context context){
        try {
            OnlineDataLab dataLab = OnlineDataLab.get(context);
            JSONObject jsonUsers = dataLab.getObjectOnline(CATALOG_USERS,null);
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
    ///---------------------------------------------------------------------------------------------
    @Override
    protected ArrayList<WUser> getListFromJson(Context context,JSONObject jsonObjectList) throws JSONException {
        ArrayList<WUser> users = new ArrayList<>();
        JSONArray jsonArray = jsonObjectList.getJSONArray("value");
        for (int i = 0; i < jsonArray.length(); i++) {
           JSONObject jsonObject = jsonArray.getJSONObject(i);
           users.add(new WUser(jsonObject));
        }
        return users;
    }

}
