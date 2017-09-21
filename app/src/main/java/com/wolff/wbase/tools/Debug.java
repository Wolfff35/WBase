package com.wolff.wbase.tools;

import android.content.Context;
import android.util.Log;

import java.util.Date;

/**
 * Created by wolff on 31.08.2017.
 */

public class Debug {
    private static Date startTime;
    private static String tagg;
    public static void start(String tag){
        startTime = new Date();
        tagg=tag;
        Log.e("DEBUG","BEGIN "+tag+" "+new DateFormatTools().dateToString(startTime,DateFormatTools.DATE_FORMAT_VID_FULL));

    }
    public static void stop(){
        Date endDate = new Date();
        Log.e("DEBUG","END "+tagg+" "+new DateFormatTools().dateToString(endDate,DateFormatTools.DATE_FORMAT_VID_FULL)+"; time - " +((endDate.getTime()-startTime.getTime()))+" m/sec");

    }

    public static void Log(String tag,String msg){
        if(PreferencesTools.IS_DEBUG) {
            Log.e(tag, msg);
        }
    }
    public static void run(Context context) {
        //WCat_Dogovor dogovor = new WCat_Dogovor_getterr(context).getItem("2cdc9528-7bc2-11e5-8119-86ffdae8e6dd");
        //dogovor.toJson(false);
        //dogovor.setDescription("rrrrrrrrrrrrrrr");
        //boolean b = dogovor.updateItem();
        //Debug.Log("UPDATE DOG"," - "+b);
        //WGetter<WCat_Organization> getter = new WGetter<>(context, MetaCatalogs.MOrganization.CATALOG_NAME,WCat_Organization.class);
        //ArrayList<WCat_Organization> org = getter.getList();
        //Debug.Log("RUNN",""+org.toString());
        //WGetter<WCat_Contragent> g2 = new WGetter<>(context,MetaCatalogs.MContragent.CATALOG_NAME,WCat_Contragent.class);
        //ArrayList<WCat_Contragent> a2 = g2.getList();
        //WGetter<WDoc_Kassa_PKO> g3 = new WGetter<>(context, MetaDocuments.MDoc_Kassa_PKO.DOCUMENT_NAME,WDoc_Kassa_PKO.class);
        //ArrayList<WDoc_Kassa_PKO> a3 = g3.getList();
    }

}
