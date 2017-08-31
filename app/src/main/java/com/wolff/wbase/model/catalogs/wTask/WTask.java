package com.wolff.wbase.model.catalogs.wTask;

import android.content.Context;
import android.util.Log;

import com.wolff.wbase.model.abs.WCatalog;
import com.wolff.wbase.model.catalogs.wUser.WUser;
import com.wolff.wbase.model.catalogs.wUser.WUserGetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wolff on 28.08.2017.
 */

public class WTask extends WCatalog {
    private WUser mAuthor;
    private WUser mProgrammer;
    private boolean mIsClosed;

    public WTask(){
        super();
    }

    public WTask(Context context, JSONObject jsonObject) {
        super(jsonObject);
        try {
            this.setClosed(jsonObject.getBoolean("фЗавершена"));
            WUserGetter userGetter = new WUserGetter();
            this.setAuthor(userGetter.getItemByGuid(context,jsonObject.getString("Автор_Key")));
            this.setProgrammer(userGetter.getItemByGuid(context,jsonObject.getString("Исполнитель_Key")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = super.toJson(onlyDeletionMark);
         if(!onlyDeletionMark) {
             try {
                 if (mAuthor != null) {
                     item.put("Автор_Key", mAuthor.getRef_Key());
                 }
                 if (mProgrammer != null) {
                     item.put("Исполнитель_Key", mProgrammer.getRef_Key());
                 }
                 item.put("фЗавершена", mIsClosed);
             } catch (JSONException e) {
                 e.printStackTrace();
             }
         }
        return item;
    }

    public WUser getAuthor() {
        return mAuthor;
    }

    public void setAuthor(WUser author) {
        mAuthor = author;
    }

    public WUser getProgrammer() {
        return mProgrammer;
    }

    public void setProgrammer(WUser programmer) {
        mProgrammer = programmer;
    }

    public boolean isClosed() {
        return mIsClosed;
    }

    public void setClosed(boolean closed) {
        mIsClosed = closed;
    }
}
