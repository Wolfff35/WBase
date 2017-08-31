package com.wolff.wbase.model.abs;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wolff on 23.08.2017.
 */

public abstract class AWObject {
    //ArrayList getList(JSONObject jsonObject);
    public abstract JSONObject toJson(boolean onlyDeletionMark);
}
