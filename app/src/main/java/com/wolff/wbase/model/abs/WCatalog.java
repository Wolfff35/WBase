package com.wolff.wbase.model.abs;


import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import static com.wolff.wbase.model.metadata.MetaCatalogs.MCatalog.HEAD.CODE;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MCatalog.HEAD.DESCRIPTION;

/**
 * Created by wolff on 28.08.2017.
 */

public class WCatalog extends WObject {
    private String mCode;
    private String mDescription;

    public WCatalog(){
        super();
    }

    public WCatalog(Context context, JSONObject userJsonObject) {
        super(context,userJsonObject);
        try {
            this.setDescription(userJsonObject.getString(DESCRIPTION));
            this.setCode(userJsonObject.getString(CODE));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = super.toJson(onlyDeletionMark);
        if(!onlyDeletionMark) {
            try {
                item.put(CODE, mCode);
                item.put(DESCRIPTION, mDescription);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return item;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }


}
