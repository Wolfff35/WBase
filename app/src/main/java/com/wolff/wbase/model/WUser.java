package com.wolff.wbase.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wolff on 17.08.2017.
 */

public class WUser extends WObject{
    public WUser(JSONObject userJsonObject) {
        super(userJsonObject);
    }

    @Override
    public JSONObject toJson() {
        super.toJson();

        return null;
    }
}
