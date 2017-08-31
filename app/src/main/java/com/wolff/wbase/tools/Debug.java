package com.wolff.wbase.tools;

import android.content.Context;
import android.util.Log;

import com.wolff.wbase.model.catalogs.wTask.WTask;
import com.wolff.wbase.model.catalogs.wTask.WTaskGetter;
import com.wolff.wbase.model.catalogs.wTask.WTaskUpdater;

import java.util.Date;

/**
 * Created by wolff on 31.08.2017.
 */

public class Debug {
    public static void onCreateActivityMain(Context context){
        Date start = new Date();
        Log.e("DEBUG","BEGIN "+start);
        //WUser user = new WUserGetter().getItemByGuid(getApplicationContext(),"62ce267e-e2d7-11e6-80c4-f2bd425ab9dd");
        //Log.e("USER",""+user.getCode()+";  "+user.getDescription());
        //ArrayList<WUser> wUsers = new WUserGetter().getList(getApplicationContext());
        //ArrayList<WTestDoc> docs = OnlineDataLab.get(getApplicationContext()).getWTestDocList();

 /*           ArrayList<WTask> wUsers = new WTaskGetter().getList(getApplicationContext());

            if(wUsers!=null) {
                for (int i = 0; i < wUsers.size(); i++) {
                    Log.e("ITEMS", ""+i+"-"+ wUsers.get(i).getCode() + "; " + wUsers.get(i).getDescription()+"; "+wUsers.get(i).getRef_Key()+" ; AUTHOR = ");
                }
             }
*/
        //WTask t = new WTask();
        //t.setClosed(true);
        //t.setDescription("TESSST "+start.getTime());
//            WTaskUpdater updater = new WTaskUpdater(t);
//            updater.updateItem(getApplicationContext());
            WTask t = new WTaskGetter().getItemByGuid(context,"e59a46ed-8e2b-11e7-80ee-f2bd425ab9dd");
            if(t!=null){
                t.setDeletionMark(false);
                t.setDescription(""+start);
                WTaskUpdater saver = new WTaskUpdater(context,t);
                Log.e("UPDATE",""+saver.updateItem());
                Log.e("DELETE",""+saver.deleteItem());
            }else {
                Log.e("EEEEERRR","ERRRROOOOR");
            }


        Date endDate = new Date();
        Log.e("DEBUG","END "+endDate+"; time - " +((endDate.getTime()-start.getTime()))+" m/sec");


    }
}
