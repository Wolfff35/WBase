package com.wolff.wbase.datalab;

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
    public ArrayList<WUser> parse_Users(JSONObject jsonObject){
        ArrayList<WUser> users = new ArrayList<>();
        try {
            JSONArray usersJsonArray = jsonObject.getJSONArray("value");
            for (int i = 0; i < usersJsonArray.length(); i++) {
                JSONObject userJsonObject = usersJsonArray.getJSONObject(i);
                WUser item = new WUser();
                item.setRef_Key(userJsonObject.getString("Ref_Key"));
                item.setDescription(userJsonObject.getString("Description"));
                item.setDeletionMark(userJsonObject.getBoolean("DeletionMark"));
                item.setCode(userJsonObject.getString("Code"));
                users.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }
}
