package com.wolff.wbase.model.catalogs.wUser;

import com.wolff.wbase.model.abs.WCatalog;

import org.json.JSONObject;

/**
 * Created by wolff on 17.08.2017.
 */

public class WUser extends WCatalog {
    public WUser(JSONObject userJsonObject) {
        super(userJsonObject);
    }

    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = super.toJson(onlyDeletionMark);

        return item;
    }
}
