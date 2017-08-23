package com.wolff.wbase.model;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wolff on 23.08.2017.
 */

public interface WObjectInterface {
    ArrayList<WObject> getList(JSONObject jsonObject);
    JSONObject toJson();
}
