package com.wolff.wbase.model.catalogs.wContragent;

import android.content.Context;

import com.wolff.wbase.model.abs.WCatalog;
import com.wolff.wbase.model.metadata.MetaCatalogs;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wolff on 28.08.2017.
 */

public class WCat_Contragent extends WCatalog {
    private String name_short;
    private String name_full;
    public WCat_Contragent(Context context, JSONObject jsonObject) {
        super(context,jsonObject);
        try {
            this.name_short = jsonObject.getString(MetaCatalogs.MContragent.HEAD.NAME_SHORT);
            this.name_full = jsonObject.getString(MetaCatalogs.MContragent.HEAD.NAME_FULL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = super.toJson(onlyDeletionMark);
        if(!onlyDeletionMark) {

        }
        return item;
    }

    public String getName_short() {
        return name_short;
    }

    public void setName_short(String name_short) {
        this.name_short = name_short;
    }

    public String getName_full() {
        return name_full;
    }

    public void setName_full(String name_full) {
        this.name_full = name_full;
    }
}
