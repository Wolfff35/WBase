package com.wolff.wbase.model.catalogs.wOrganization;

import android.content.Context;

import com.wolff.wbase.datalab.OnlineDataLab;
import com.wolff.wbase.model.abs.AWObject_updater;

import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_PATCH;
import static com.wolff.wbase.datalab.OnlineDataLab.CATALOG_ORGANIZATION;

/**
 * Created by wolff on 01.09.2017.
 */

public class WOrganization_updater extends AWObject_updater {
    private WOrganization mOrganization;
    private String mObjectType;
    private Context mContext;

    public WOrganization_updater(Context context,WOrganization organization){
        this.mOrganization=organization;
        this.mContext=context;
        this.mObjectType=CATALOG_ORGANIZATION;
    }

    @Override
    public boolean updateItem(){
        OnlineDataLab dataLab = OnlineDataLab.get(mContext);
        String s_data = mOrganization.toJson(false).toString();
        return dataLab.postObjectOnline(CONNECTION_TYPE_PATCH,mObjectType,mOrganization.getRef_Key(),s_data);
        //Log.e("update",""+s_data);
    }

    @Override
    public boolean deleteItem(){
        mOrganization.setDeletionMark(true);
        OnlineDataLab dataLab = OnlineDataLab.get(mContext);
        String s_data = mOrganization.toJson(true).toString();
        return dataLab.postObjectOnline(CONNECTION_TYPE_PATCH,mObjectType,mOrganization.getRef_Key(),s_data);
        //Log.e("delete",""+s_data);
    }

}
