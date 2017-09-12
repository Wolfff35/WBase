package com.wolff.wbase.model.aWObject;

import android.content.Context;

import com.wolff.wbase.tools.StringConvertTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import static com.wolff.wbase.model.metadata.MetaCatalogs.MObject.HEAD.DATA_VERSION;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MObject.HEAD.DELETION_MARK;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MObject.HEAD.REF_KEY;

/**
 * Created by wolff on 16.08.2017.
 */

public abstract class WObject extends AWObject implements Serializable{
    //private static final long serialVersionUID = 2163051469151804397L;
    private String mRef_Key;
    private String mDataVersion;
    private boolean mDeletionMark;

    public WObject(){}
    public WObject (Context context,JSONObject userJsonObject){
        try {
            this.setRef_Key(userJsonObject.getString(REF_KEY));
            this.setDeletionMark(userJsonObject.getBoolean(DELETION_MARK));
            this.setDataVersion(userJsonObject.getString(DATA_VERSION));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = new JSONObject();
        try {
            item.put(DELETION_MARK,mDeletionMark);

        }catch (JSONException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    protected StringBuffer formatXmlBody() {
        StringBuffer sb = new StringBuffer();
        StringConvertTools.addFieldToXml(sb, DELETION_MARK, String.valueOf(isDeletionMark()));
        return sb;
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

    public String getDataVersion() {
        return mDataVersion;
    }

    public void setDataVersion(String dataVersion) {
        mDataVersion = dataVersion;
    }
}
