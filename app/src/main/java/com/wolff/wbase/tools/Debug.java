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
    public static void onCreateActivityMain(Context context){
/*         for (int i=0;i<5000;i++){
            org.setDescription("Org - "+new DateFormatTools().dateToString(new Date(),DateFormatTools.DATE_FORMAT_VID_FULL)+" - "+i);
            WCat_Organization_saver saver = new WCat_Organization_saver(context,org);
            if(saver.addNewItem()) {
                Log.i("COUNTER", " = " + i);
            }
        }
*/



        //Log.e("USER",""+user.getCode()+";  "+user.getDescription());


  /*          if(wUsers!=null) {
                for (int i = 0; i < wUsers.size(); i++) {
                    Log.e("ITEMS", ""+i+"-"+ wUsers.get(i).getCode() + "; " + wUsers.get(i).getDescription()+"; "+wUsers.get(i).getRef_Key()+" ; AUTHOR = ");
                }
             }
*/
        //WTaswk t = new WTawsk();
        //t.setClosed(true);
        //t.setDescription("TESSST "+start.getTime());
//            WTaskUpdaterq updater = new WTaskUpdaterq(t);
//            updater.updateItem(getApplicationContext());
      /*      WTaswk t = new WTaskGetterr().getItemByGuid(context,"e59a46ed-8e2b-11e7-80ee-f2bd425ab9dd");
            if(t!=null){
                t.setDeletionMark(false);
                t.setDescription(""+start);
                WTaskUpdateqr saver = new WTaskUpdateqr(context,t);
                Log.e("UPDATE",""+saver.updateItem());
                Log.e("DELETE",""+saver.deleteItem());
            }else {
                Log.e("EEEEERRR","ERRRROOOOR");
            }
*/


    }
}
