package com.wolff.wbase.model.abs;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wolff on 29.08.2017.
 */

public abstract class AWObjectGetter {
    public abstract WObject getItemByGuid(Context context, String guid);
    public abstract ArrayList getList(Context context);
    protected abstract ArrayList getListFromJson(Context context,JSONObject jsonObjectList) throws JSONException;
}
