package com.wolff.wbase.model.catalogs.wOrganization;

import android.content.Context;

import com.wolff.wbase.model.abs.WCatalog;
import com.wolff.wbase.model.catalogs.wContragent.WCat_Contragent;
import com.wolff.wbase.model.catalogs.wContragent.WCat_Contragent_getter;
import com.wolff.wbase.model.metadata.MetaCatalogs;
import com.wolff.wbase.tools.Debug;

import org.json.JSONException;
import org.json.JSONObject;

import static com.wolff.wbase.model.metadata.MetaCatalogs.MOrganization.HEAD.CONTRAGENT_KEY;

/**
 * Created by wolff on 01.09.2017.
 */

public class WCat_Organization extends WCatalog {
    private WCat_Contragent mContragent;
    private Context mContext;
    private String mPrefix;

    public WCat_Organization(Context context){
        mContext=context;
    }

    public WCat_Organization(Context context, JSONObject jsonObject) {
        super(context,jsonObject);
        try {
            this.mPrefix = jsonObject.getString(MetaCatalogs.MOrganization.HEAD.PREFIX);
            this.mContragent = new WCat_Contragent_getter(mContext).getItemByGuid(jsonObject.getString(CONTRAGENT_KEY));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Debug.Log("CREATE ORG",""+this.getRef_Key()+"; "+this.getDescription());
    }
    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = super.toJson(onlyDeletionMark);
        if(!onlyDeletionMark) {
            try {
                if (mContragent != null) {
                    item.put(CONTRAGENT_KEY, mContragent.getRef_Key());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Debug.Log("toJson",""+item.toString());
        return item;
    }

    public WCat_Contragent getContragent() {
        return mContragent;
    }

    public void setContragent(WCat_Contragent contragent) {
        mContragent = contragent;
    }

 /*   public String getPrefix() {
        return mPrefix;
    }

    public void setPrefix(String prefix) {
        mPrefix = prefix;
    }*/
}
