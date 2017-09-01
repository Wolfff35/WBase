package com.wolff.wbase.model.catalogs.wOrganization;

import android.content.Context;

import com.wolff.wbase.datalab.OnlineDataLab;
import com.wolff.wbase.model.abs.AWObject_saver;
import com.wolff.wbase.tools.StringConvertTools;

import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_POST;
import static com.wolff.wbase.datalab.OnlineDataLab.CATALOG_ORGANIZATION;

/**
 * Created by wolff on 01.09.2017.
 */

public class WOrganization_saver extends AWObject_saver {
    private WOrganization mWOrganization;
    private Context mContext;

    private String mObjectType = CATALOG_ORGANIZATION;

    public WOrganization_saver(Context context,WOrganization organization){
        mWOrganization=organization;
        mContext = context;
    }

    @Override
    public boolean addNewItem(){
        OnlineDataLab dataLab = OnlineDataLab.get(mContext);
        String s_data = getDataToPost();
        return dataLab.postObjectOnline(CONNECTION_TYPE_POST,mObjectType,mWOrganization.getRef_Key(),s_data);


    }
    @Override
    protected String getDataToPost(){

        String ss_header = formatXmlHeader(mContext,mObjectType);
        String ss_footer = formatXmlFooter(mContext);
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

        StringConvertTools.addFieldToXml(sb, "Code", mWOrganization.getCode());
        StringConvertTools.addFieldToXml(sb, "Description", mWOrganization.getDescription());
        if (mWOrganization.getContragent() != null) {
            StringConvertTools.addFieldToXml(sb, "Контрагент_Key", mWOrganization.getContragent().getRef_Key());
        }
        StringConvertTools.addFieldToXml(sb, "DeletionMark", String.valueOf(mWOrganization.isDeletionMark()));
        return sb.toString();
    }


}
