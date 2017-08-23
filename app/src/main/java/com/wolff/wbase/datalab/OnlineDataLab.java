package com.wolff.wbase.datalab;

import android.content.Context;
import android.util.Log;

import com.wolff.wbase.model.WUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wolff on 10.08.2017.
 */

public class OnlineDataLab {
    public static final String CATALOG_USERS = "Catalog_Пользователи";
    //public static final String CATALOG_TEST_DOC = "Catalog_Пользователи";
    //=================================================================================================
    public ArrayList<WUser> get_WUserList(Context context){
       //TODO переделать на метод обьекта
        ArrayList<WUser> users = new ArrayList<>();
        OnlineDataReceiver receiver = new OnlineDataReceiver();
        try {
            String jsonString = receiver.getUrlString(context,CATALOG_USERS,null);
            JsonParcer parcer = new JsonParcer();
            JSONObject jsonObject = new JSONObject(jsonString);
            users = parcer.parse_WUserList(jsonObject);
          } catch (JSONException e) {
              Log.e("get_WUserList","ERROR2 "+e.getLocalizedMessage());
          }
            return users;
   }

}
