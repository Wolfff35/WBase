package com.wolff.wbase.datalab;

import android.util.Log;

import com.wolff.wbase.model.WObject;
import com.wolff.wbase.model.WUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wolff on 17.08.2017.
 */

public class JsonParcer {
    public ArrayList<WUser> parse_WUserList(JSONObject jsonObject){
        //TODO переделать на метод обьекта
        ArrayList<WUser> users = new ArrayList<>();
        try {
            JSONArray usersJsonArray = jsonObject.getJSONArray("value");
            for (int i = 0; i < usersJsonArray.length(); i++) {
                JSONObject userJsonObject = usersJsonArray.getJSONObject(i);
                WUser item = new WUser(userJsonObject);
                users.add(item);
            }
            return users;
        } catch (JSONException e) {
            //e.printStackTrace();
            Log.e("parse_WUserList","ERROR - "+e.getLocalizedMessage());
            return null;
        }
    }
}
