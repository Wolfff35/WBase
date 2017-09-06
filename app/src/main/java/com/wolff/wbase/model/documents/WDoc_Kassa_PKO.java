package com.wolff.wbase.model.documents;

import android.content.Context;

import com.wolff.wbase.model.abs.WDocument;
import com.wolff.wbase.tools.Debug;

import org.json.JSONObject;

/**
 * Created by wolff on 06.09.2017.
 */

public class WDoc_Kassa_PKO extends WDocument {
    private Context mContext;
    //private String mPrefix;


    public WDoc_Kassa_PKO(Context context, JSONObject jsonObject) {
        super(context, jsonObject);
        //try {
            //this.mContragent = new WCat_Contragent_getter(mContext).getItemByGuid(jsonObject.getString("Контрагент_Key"));
            //} catch (JSONException e) {
            //   e.printStackTrace();
        //}
        Debug.Log("CREATE ORG", "" + this.getRef_Key() + "; " + this.getNumber());
    }

    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = super.toJson(onlyDeletionMark);
        if (!onlyDeletionMark) {
            //try {
                //  if (mContragent != null) {
                //      item.put("Контрагент_Key", mContragent.getRef_Key());
                //  }
                // } catch (JSONException e) {
                //      e.printStackTrace();
            //}
        }
        Debug.Log("toJson", "" + item.toString());
        return item;

    }
}
