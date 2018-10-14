package com.pt.khanh.movie.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenresResult {
    @SerializedName("genres")
    @Expose
    private List<Genre> mGenres;

    public List<Genre> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genre> genres) {
        mGenres = genres;
    }
}
