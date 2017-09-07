package com.wolff.wbase.model.abs;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wolff on 23.08.2017.
 */

public abstract class AWObject implements Serializable{
    //ArrayList getList(JSONObject jsonObject);
    public abstract JSONObject toJson(boolean onlyDeletionMark);
    public abstract boolean addNewItem();
    protected abstract StringBuffer formatXmlBody();
    public abstract boolean updateItem();
    public abstract boolean deleteItem();

}
