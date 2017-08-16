package com.wolff.wbase.model;

/**
 * Created by wolff on 16.08.2017.
 */

public class WObject {
    private static final long serialVersionUID = 2163051469151804397L;
    private String mRef_Key;
    private boolean mDeletionMark;
    private String mCode;
    private String mDescription;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WObject)) return false;

        WObject wObject = (WObject) o;

        return getRef_Key() != null ? getRef_Key().equals(wObject.getRef_Key()) : wObject.getRef_Key() == null;

    }

    public String getRef_Key() {
        return mRef_Key;
    }

    public void setRef_Key(String ref_Key) {
        mRef_Key = ref_Key;
    }

    public boolean isDeletionMark() {
        return mDeletionMark;
    }

    public void setDeletionMark(boolean deletionMark) {
        mDeletionMark = deletionMark;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
