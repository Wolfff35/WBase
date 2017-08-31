package com.wolff.wbase.model.catalogs.wContragent;

import android.util.Log;

import com.wolff.wbase.model.abs.WCatalog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wolff on 28.08.2017.
 */

public class WContragent extends WCatalog {
    public WContragent(JSONObject userJsonObject) {
        super(userJsonObject);
    }
    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = super.toJson(onlyDeletionMark);

        return item;
    }

 /*   public static ArrayList<WContragent> getList(JSONObject jsonObjectList) {
        ArrayList<WContragent> contragents = new ArrayList<>();
        try {
            JSONArray usersJsonArray = jsonObjectList.getJSONArray("value");
            for (int i = 0; i < usersJsonArray.length(); i++) {
                JSONObject userJsonObject = usersJsonArray.getJSONObject(i);
                contragents.add(new WContragent(userJsonObject));
            }
            return contragents;
        } catch (JSONException e) {
            Log.e("getList","ERROR - "+e.getLocalizedMessage());
            return null;
        }

    }
*/
}
