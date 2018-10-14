package com.pt.khanh.movie.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrailerMovie {
    @SerializedName("key")
    @Expose
    private String mKey;
    @SerializedName("name")
    @Expose
    private String mName;

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
