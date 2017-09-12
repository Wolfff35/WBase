package com.wolff.wbase.model.documents.wDoc_Kassa_PKO;

import android.content.Context;

import com.wolff.wbase.datalab.OnlineDataSender;
import com.wolff.wbase.model.documents.wDoc_Kassa_PKO.WDoc_Kassa_PKO;
import com.wolff.wbase.model.documents.wDocument.WDocument_getter;
import com.wolff.wbase.model.metadata.MetaDocuments;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.wolff.wbase.tools.Const.NULL_REF;

/**
 * Created by wolff on 11.09.2017.
 */

public class WDoc_Kassa_PKO_getter extends WDocument_getter {
    private String mObjectType;
    private Context mContext;

    public WDoc_Kassa_PKO_getter(Context context){
        this.mObjectType = MetaDocuments.MDoc_Kassa_PKO.DOCUMENT_NAME;
        this.mContext = context;
    }
    @Override
    public WDoc_Kassa_PKO getItem(String guid) {
        if(guid.isEmpty()){
            return null;
        }
        if(guid.equalsIgnoreCase(NULL_REF)){
            return null;
        }

        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        JSONObject jsonTasks = dataLab.getObjectOnline(mObjectType,guid);
        if (jsonTasks!=null) {
            return new WDoc_Kassa_PKO(mContext,jsonTasks);
        }else {
            return null;
        }
    }

    @Override
    public ArrayList getList(String sTypeObject) {
        return super.getList(sTypeObject);
    }

    @Override
    protected ArrayList getListFromJson(JSONObject jsonObjectList) throws JSONException {
        return super.getListFromJson(jsonObjectList);
    }
}
