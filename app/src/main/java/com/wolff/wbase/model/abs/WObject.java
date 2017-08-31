package com.wolff.wbase.model.abs;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wolff on 16.08.2017.
 */

public abstract class WObject extends AWObject {
    private static final long serialVersionUID = 2163051469151804397L;
    private String mRef_Key;
    private boolean mDeletionMark;

    public WObject(){}

    public WObject (JSONObject userJsonObject){
        try {
            this.setRef_Key(userJsonObject.getString("Ref_Key"));
            this.setDeletionMark(userJsonObject.getBoolean("DeletionMark"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = new JSONObject();
        try {
            item.put("DeletionMark",mDeletionMark);

        }catch (JSONException e){
            e.printStackTrace();
        }
        return item;
    }

    //==================================================================================================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WObject)) return false;

        WObject wObject = (WObject) o;

        return getRef_Key() != null ? getRef_Key().equals(wObject.getRef_Key()) : wObject.getRef_Key() == null;

    }

    public String getRef_Key() {
        return mRef_Key;
    }

    public void setRef_Key(String ref_Key) {
        mRef_Key = ref_Key;
    }

    public boolean isDeletionMark() {
        return mDeletionMark;
    }

    public void setDeletionMark(boolean deletionMark) {
        mDeletionMark = deletionMark;
    }

 }
