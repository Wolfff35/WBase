package com.wolff.wbase.model.catalogs.wTask;

import android.content.Context;

import com.wolff.wbase.datalab.OnlineDataLab;
import com.wolff.wbase.model.abs.AWObjectUpdater;

import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_PATCH;
import static com.wolff.wbase.datalab.OnlineDataLab.CATALOG_TASKS;

/**
 * Created by wolff on 31.08.2017.
 */

public class WTaskUpdater extends AWObjectUpdater {
    private WTask mTask;
    private String mObjectType;
    private Context mContext;

    public WTaskUpdater(Context context,WTask task){
        this.mTask=task;
        this.mContext=context;
        this.mObjectType=CATALOG_TASKS;
    }

    @Override
    public boolean updateItem(){
        OnlineDataLab dataLab = OnlineDataLab.get(mContext);
        String s_data = mTask.toJson(false).toString();
        return dataLab.postObjectOnline(CONNECTION_TYPE_PATCH,mObjectType,mTask.getRef_Key(),s_data);
        //Log.e("update",""+s_data);
    }

    @Override
    public boolean deleteItem(){
        mTask.setDeletionMark(true);
        OnlineDataLab dataLab = OnlineDataLab.get(mContext);
        String s_data = mTask.toJson(true).toString();
        return dataLab.postObjectOnline(CONNECTION_TYPE_PATCH,mObjectType,mTask.getRef_Key(),s_data);
        //Log.e("delete",""+s_data);
    }

}
