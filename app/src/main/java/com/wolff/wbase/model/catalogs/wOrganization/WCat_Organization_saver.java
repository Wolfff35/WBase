package com.wolff.wbase.model.catalogs.wOrganization;

import android.content.Context;

import com.wolff.wbase.datalab.OnlineDataSender;
import com.wolff.wbase.model.abs.AWObject_saver;
import com.wolff.wbase.tools.StringConvertTools;

import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_POST;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MCatalog.HEAD.CODE;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MCatalog.HEAD.DESCRIPTION;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MObject.HEAD.DELETION_MARK;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MOrganization.CATALOG_NAME;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MOrganization.HEAD.CONTRAGENT_KEY;

/**
 * Created by wolff on 01.09.2017.
 */

public class WCat_Organization_saver extends AWObject_saver {
    private WCat_Organization mWOrganization;
    private Context mContext;

    private String mObjectType = CATALOG_NAME;

    public WCat_Organization_saver(Context context, WCat_Organization organization){
        mWOrganization=organization;
        mContext = context;
    }

    @Override
    public boolean addNewItem(){
        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
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

        StringConvertTools.addFieldToXml(sb, CODE, mWOrganization.getCode());
        StringConvertTools.addFieldToXml(sb, DESCRIPTION, mWOrganization.getDescription());
        if (mWOrganization.getContragent() != null) {
            StringConvertTools.addFieldToXml(sb, CONTRAGENT_KEY, mWOrganization.getContragent().getRef_Key());
        }
        StringConvertTools.addFieldToXml(sb, DELETION_MARK, String.valueOf(mWOrganization.isDeletionMark()));
        return sb.toString();
    }


}
