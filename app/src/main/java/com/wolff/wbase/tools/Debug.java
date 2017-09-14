package com.wolff.wbase.tools;

import android.content.Context;
import android.util.Log;

import com.wolff.wbase.model.catalogs.wDogovor.WCat_Dogovor;
import com.wolff.wbase.model.catalogs.wDogovor.WCat_Dogovor_getter;

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
        WCat_Dogovor dogovor = new WCat_Dogovor_getter(context).getItem("2cdc9528-7bc2-11e5-8119-86ffdae8e6dd");
        //dogovor.toJson(false);
        dogovor.setDescription("rrrrrrrrrrrrrrr");
        boolean b = dogovor.updateItem();
        Debug.Log("UPDATE DOG"," - "+b);
    }

}
