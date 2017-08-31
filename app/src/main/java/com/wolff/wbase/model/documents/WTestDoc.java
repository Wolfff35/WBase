package com.wolff.wbase.model.documents;

import android.util.Log;

import com.wolff.wbase.model.abs.WDocument;
import com.wolff.wbase.model.catalogs.wContragent.WContragent;
import com.wolff.wbase.model.catalogs.wUser.WUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wolff on 28.08.2017.
 */

public class WTestDoc extends WDocument {
    private WUser mAuthor;
    private WContragent mContragent;
    private ArrayList<WTestDoc_Table> mTable;
    public WTestDoc(JSONObject userJsonObject) {
        super(userJsonObject);


    }
 /*   public static ArrayList<WTestDoc> getList(JSONObject jsonObjectList) {
        ArrayList<WTestDoc> docs = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonObjectList.getJSONArray("value");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userJsonObject = jsonArray.getJSONObject(i);
                docs.add(new WTestDoc(userJsonObject));
            }
            return docs;
        } catch (JSONException e) {
            Log.e("getList","ERROR - "+e.getLocalizedMessage());
            return null;
        }

    }
*/
}
