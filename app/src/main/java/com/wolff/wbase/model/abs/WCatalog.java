package com.wolff.wbase.model.abs;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wolff on 28.08.2017.
 */

public class WCatalog extends WObject {
    private String mCode;
    private String mDescription;

    public WCatalog(){
        super();
    }

    public WCatalog(JSONObject userJsonObject) {
        super(userJsonObject);
        try {
            this.setDescription(userJsonObject.getString("Description"));
            this.setCode(userJsonObject.getString("Code"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = super.toJson(onlyDeletionMark);
        if(!onlyDeletionMark) {
            try {
                item.put("Code", mCode);
                item.put("Description", mDescription);
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
