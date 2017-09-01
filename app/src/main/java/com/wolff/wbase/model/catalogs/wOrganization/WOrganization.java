package com.wolff.wbase.model.catalogs.wOrganization;

import android.content.Context;

import com.wolff.wbase.model.abs.WCatalog;
import com.wolff.wbase.model.catalogs.wContragent.WContragent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wolff on 01.09.2017.
 */

public class WOrganization extends WCatalog {
    private WContragent mContragent;
    private String mPrefix;

    public WOrganization(){
        super();
    }

    public WOrganization(Context context, JSONObject jsonObject) {
        super(jsonObject);
        try {
            this.mPrefix = jsonObject.getString("Префикс");
            //this.setClosed(jsonObject.getBoolean("фЗавершена"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = super.toJson(onlyDeletionMark);
        if(!onlyDeletionMark) {
            /*try {
                if (mAuthor != null) {
                    item.put("Автор_Key", mAuthor.getRef_Key());
                }
                if (mProgrammer != null) {
                    item.put("Исполнитель_Key", mProgrammer.getRef_Key());
                }
                item.put("фЗавершена", mIsClosed);
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }
        return item;
    }

    public WContragent getContragent() {
        return mContragent;
    }

    public void setContragent(WContragent contragent) {
        mContragent = contragent;
    }

 /*   public String getPrefix() {
        return mPrefix;
    }

    public void setPrefix(String prefix) {
        mPrefix = prefix;
    }*/
}
