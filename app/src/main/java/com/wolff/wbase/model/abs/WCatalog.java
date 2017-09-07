package com.wolff.wbase.model.abs;


import android.content.Context;

import com.wolff.wbase.tools.Debug;
import com.wolff.wbase.tools.StringConvertTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import static com.wolff.wbase.model.metadata.MetaCatalogs.MCatalog.HEAD.CODE;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MCatalog.HEAD.DESCRIPTION;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MCatalog.HEAD.ISFOLDER;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MCatalog.HEAD.PARENT_KEY;

/**
 * Created by wolff on 28.08.2017.
 */

public class WCatalog extends WObject implements Serializable {
    private String mCode;
    private String mDescription;
    private String mParentKey;
    private boolean mIsFolder;

    public WCatalog(){
        super();
    }

    public WCatalog(Context context,JSONObject userJsonObject) {
        super(context,userJsonObject);
        try {
            this.setDescription(userJsonObject.getString(DESCRIPTION));
            this.setCode(userJsonObject.getString(CODE));
        } catch (JSONException e) {
            Debug.Log("ERROR 1",""+e.getLocalizedMessage());
        }
        try{
            this.setFolder(Boolean.valueOf(userJsonObject.getString(ISFOLDER)));
        }catch (JSONException e){
            this.setFolder(false);
        }
        try{
            this.setParentKey(userJsonObject.getString(PARENT_KEY));
        }catch (JSONException e){
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

    @Override
    public boolean addNewItem() {
        return false;
    }

    @Override
    protected StringBuffer formatXmlBody() {
        StringBuffer sb = super.formatXmlBody();
        if (!getCode().isEmpty()) {
            StringConvertTools.addFieldToXml(sb, CODE, getCode());
        }
        StringConvertTools.addFieldToXml(sb, DESCRIPTION, getDescription());
        return sb;
    }

    @Override
    public boolean updateItem() {
        return false;
    }

    @Override
    public boolean deleteItem() {
        return false;
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

    public boolean isFolder() {
        return mIsFolder;
    }

    public void setFolder(boolean folder) {
        mIsFolder = folder;
    }

    public String getParentKey() {
        return mParentKey;
    }

    public void setParentKey(String parentKey) {
        mParentKey = parentKey;
    }
}
