package com.wolff.wbase.model.abs;

import android.content.Context;

import com.wolff.wbase.model.abs.WObject;
import com.wolff.wbase.model.metadata.MetaDocuments;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import static com.wolff.wbase.model.metadata.MetaDocuments.MDocument.HEAD.DATE;
import static com.wolff.wbase.model.metadata.MetaDocuments.MDocument.HEAD.NUMBER;
import static com.wolff.wbase.model.metadata.MetaDocuments.MDocument.HEAD.POSTED;


/**
 * Created by wolff on 28.08.2017.
 */

public class WDocument extends WObject implements Serializable{
    private String mNumber;
    private String mDate;
    private boolean mPosted; //проведен
    //public WDocument(){
    //    super();
    //}
     public WDocument(Context context,JSONObject jsonObject) {
        //super(context,jsonObject);
        try {
            this.setDate(jsonObject.getString(DATE));
            this.setNumber(jsonObject.getString(NUMBER));
            this.setPosted(jsonObject.getBoolean(POSTED));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject toJson(boolean onlyDeletionMark) {
        JSONObject item = super.toJson(onlyDeletionMark);
        if(!onlyDeletionMark) {
            try {
                item.put(DATE, mDate);
                item.put(NUMBER, mNumber);
                item.put(POSTED, mPosted);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return item;
    }

    @Override
    public boolean addNewItem() {
        return false;
    }

    @Override
    protected StringBuffer formatXmlBody() {
        return super.formatXmlBody();
    }

    @Override
    public boolean updateItem() {
        return false;
    }

    @Override
    public boolean deleteItem() {
        return false;
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
