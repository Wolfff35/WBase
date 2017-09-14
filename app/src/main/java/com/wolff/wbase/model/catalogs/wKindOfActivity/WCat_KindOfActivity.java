package com.wolff.wbase.model.catalogs.wKindOfActivity;

import android.content.Context;

import com.wolff.wbase.datalab.OnlineDataSender;
import com.wolff.wbase.model.catalogs.wCatalog.WCatalog;
import com.wolff.wbase.model.metadata.MetaCatalogs;
import com.wolff.wbase.tools.Debug;
import com.wolff.wbase.tools.StringConvertTools;
import com.wolff.wbase.tools.XmlTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_PATCH;
import static com.wolff.wbase.datalab.OnlineConnector.CONNECTION_TYPE_POST;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MKindOfActivity.HEAD.IS_AVANS;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MKindOfActivity.HEAD.IS_BONUS;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MKindOfActivity.HEAD.IS_VZAIMORASCH;
import static com.wolff.wbase.model.metadata.MetaCatalogs.MKindOfActivity.HEAD.IS_VZAIMORASCH_ZATRATA;

/**
 * Created by wolff on 06.09.2017.
 */

public class WCat_KindOfActivity extends WCatalog implements Serializable {
    private Context mContext;
    private static final String CATALOG_TYPE = MetaCatalogs.MKindOfActivity.CATALOG_NAME;

    private boolean mIsVzaimorasch;          //фВзаиморасчеты
    private boolean mIsAvans;                //фВзаиморасчеты_Авансы
    private boolean mIsBonus;                //фВзаиморасчеты_Бонусы
    private boolean mIsVzaimoraschZatrata;   //фВзаиморасчеты_Затраты

    public WCat_KindOfActivity(Context context){
        mContext=context;
    }

    public WCat_KindOfActivity(Context context, JSONObject jsonObject) {
        super(context,jsonObject);
        mContext=context;
        try {
            this.mIsVzaimorasch = jsonObject.getBoolean(IS_VZAIMORASCH);
            this.mIsAvans = jsonObject.getBoolean(IS_AVANS);
            this.mIsBonus = jsonObject.getBoolean(IS_BONUS);
            this.mIsVzaimoraschZatrata = jsonObject.getBoolean(IS_VZAIMORASCH_ZATRATA);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Debug.Log("CREATE KIND",""+this.getRef_Key()+"; "+this.getDescription());
    }


    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = super.toJson(onlyDeletionMark);
        if(!onlyDeletionMark) {
            try {
                item.put(IS_VZAIMORASCH, isVzaimorasch());
                item.put(IS_AVANS, isAvans());
                item.put(IS_BONUS, isBonus());
                item.put(IS_VZAIMORASCH_ZATRATA, isVzaimoraschZatrata());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Debug.Log("toJson", "" + item.toString());
        return item;
    }

    public boolean isVzaimorasch() {
        return mIsVzaimorasch;
    }

    public void setIsVzaimorasch(boolean iisVzaimorasch) {
        mIsVzaimorasch = iisVzaimorasch;
    }

    public boolean isAvans() {
        return mIsAvans;
    }

    public void setAvans(boolean avans) {
        mIsAvans = avans;
    }

    public boolean isBonus() {
        return mIsBonus;
    }

    public void setBonus(boolean bonus) {
        mIsBonus = bonus;
    }

    public boolean isVzaimoraschZatrata() {
        return mIsVzaimoraschZatrata;
    }

    public void setVzaimoraschZatrata(boolean vzaimoraschZatrata) {
        mIsVzaimoraschZatrata = vzaimoraschZatrata;
    }

    //=================================================================================================
    @Override
    public boolean addNewItem(){
        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        String s_data = XmlTools.formatXmlHeader(mContext, CATALOG_TYPE)+formatXmlBody().toString()+ XmlTools.formatXmlFooter(mContext);
        return dataLab.postObjectOnline(CONNECTION_TYPE_POST, CATALOG_TYPE,null,s_data);
    }

    @Override
    public boolean updateItem(){
        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        String s_data = this.toJson(false).toString();
        return dataLab.postObjectOnline(CONNECTION_TYPE_PATCH, CATALOG_TYPE,this.getRef_Key(),s_data);
    }

    @Override
    public boolean deleteItem(){
        this.setDeletionMark(true);
        OnlineDataSender dataLab = OnlineDataSender.get(mContext);
        String s_data = this.toJson(true).toString();
        return dataLab.postObjectOnline(CONNECTION_TYPE_PATCH, CATALOG_TYPE,this.getRef_Key(),s_data);
    }

    @Override
    protected StringBuffer formatXmlBody() {
        StringBuffer sb = super.formatXmlBody();
        StringConvertTools.addFieldToXml(sb, IS_VZAIMORASCH, String.valueOf(isVzaimorasch()));
        StringConvertTools.addFieldToXml(sb, IS_AVANS, String.valueOf(isAvans()));
        StringConvertTools.addFieldToXml(sb, IS_BONUS, String.valueOf(isBonus()));
        StringConvertTools.addFieldToXml(sb, IS_VZAIMORASCH_ZATRATA, String.valueOf(isVzaimoraschZatrata()));

        return sb;
    }

}

