package com.wolff.wbase.model.catalogs.wTask;

import android.content.Context;
import com.wolff.wbase.datalab.OnlineDataLab;
import com.wolff.wbase.model.abs.AWObjectSaver;
import com.wolff.wbase.tools.StringConvertTools;

import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_POST;
import static com.wolff.wbase.datalab.OnlineDataLab.CATALOG_TASKS;

/**
 * Created by wolff on 30.08.2017.
 */

public class WTaskSaver extends AWObjectSaver{
    private WTask mTask;
    private String mObjectType = CATALOG_TASKS;
    public WTaskSaver(WTask task){
        mTask=task;

    }

    @Override
    public boolean addNewItem(Context context){
        OnlineDataLab dataLab = OnlineDataLab.get(context);
        String s_data = getDataToPost(context);
        return dataLab.postObjectOnline(CONNECTION_TYPE_POST,mObjectType,mTask.getRef_Key(),s_data);


    }
    @Override
    protected String getDataToPost(Context context){

        String ss_header = formatXmlHeader(context,mObjectType);
        String ss_footer = formatXmlFooter(context);
        String ss_body = formatXmlBody();
        return ss_header+ss_body+ss_footer;
   }
    @Override
    protected String formatXmlHeader(Context context,String sObjectType){
        return super.formatXmlHeader(context,sObjectType);
      }

    @Override
    protected String formatXmlFooter(Context context){
         return super.formatXmlFooter(context);
    }
    @Override
    protected String formatXmlBody() {
        StringBuffer sb = new StringBuffer();
        StringConvertTools.addFieldToXml(sb, "Description", mTask.getDescription());
        if (mTask.getAuthor() != null) {
            StringConvertTools.addFieldToXml(sb, "Автор_Key", mTask.getAuthor().getRef_Key());
        }
        StringConvertTools.addFieldToXml(sb, "фЗавершена", String.valueOf(mTask.isClosed()));
        StringConvertTools.addFieldToXml(sb, "DeletionMark", "false");
        return sb.toString();
    }


}
