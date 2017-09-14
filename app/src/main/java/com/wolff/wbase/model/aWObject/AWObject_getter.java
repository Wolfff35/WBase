package com.wolff.wbase.model.aWObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wolff on 29.08.2017.
 */

public abstract class AWObject_getter {
    public abstract WObject getItem(String guid);
    public abstract ArrayList getList(String sTypeObject);
    protected abstract ArrayList getListFromJson(JSONObject jsonObjectList) throws JSONException;
}