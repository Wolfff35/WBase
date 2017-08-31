package com.wolff.wbase.model.abs;

import com.wolff.wbase.model.abs.WObject;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by wolff on 28.08.2017.
 */

public class WDocument extends WObject {
    private String mNumber;
    private String mDate;
    private boolean mPosted; //проведен
    public WDocument(JSONObject jsonObject) {
        super(jsonObject);
        try {
            this.setDate(jsonObject.getString("Date"));
            this.setNumber(jsonObject.getString("Number"));
            this.setPosted(jsonObject.getBoolean("Posted"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = super.toJson(onlyDeletionMark);
        if(!onlyDeletionMark) {
            try {
                item.put("Date", mDate);
                item.put("Number", mNumber);
                item.put("Posted", mPosted);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return item;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public boolean isPosted() {
        return mPosted;
    }

    public void setPosted(boolean posted) {
        mPosted = posted;
    }
}
