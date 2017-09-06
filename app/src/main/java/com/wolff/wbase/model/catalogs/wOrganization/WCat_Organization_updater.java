package com.wolff.wbase.model.catalogs.wOrganization;

import android.content.Context;

import com.wolff.wbase.datalab.OnlineDataSender;
import com.wolff.wbase.model.abs.AWObject_updater;
import com.wolff.wbase.model.metadata.MetaCatalogs;

import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_PATCH;

/**
 * Created by wolff on 01.09.2017.
 */

public class WCat_Organization_updater extends AWObject_updater {
    private WCat_Organization mOrganization;
    private String mObjectType;
    private Context mContext;

    public WCat_Organization_updater(Context context, WCat_Organization organization){
        this.mOrganization=organization;
        this.mContext=context;
        this.mObjectType= MetaCatalogs.MOrganization.CATALOG_NAME;
    }

    @Override
    public boolean updateItem(){
        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        String s_data = mOrganization.toJson(false).toString();
        return dataLab.postObjectOnline(CONNECTION_TYPE_PATCH,mObjectType,mOrganization.getRef_Key(),s_data);
        //Log.e("update",""+s_data);
    }

    @Override
    public boolean deleteItem(){
        mOrganization.setDeletionMark(true);
        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        String s_data = mOrganization.toJson(true).toString();
        return dataLab.postObjectOnline(CONNECTION_TYPE_PATCH,mObjectType,mOrganization.getRef_Key(),s_data);
        //Log.e("delete",""+s_data);
    }

}
