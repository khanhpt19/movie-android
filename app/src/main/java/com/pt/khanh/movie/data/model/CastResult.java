package com.pt.khanh.movie.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastResult {
    @SerializedName("cast")
    @Expose
    private List<Cast> mCasts;

    public List<Cast> getCasts() {
        return mCasts;
    }

    public void setCasts(List<Cast> casts) {
        mCasts = casts;
    }
}
