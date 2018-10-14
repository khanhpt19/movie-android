package com.pt.khanh.movie.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerMovieResult {
    @SerializedName("results")
    @Expose
    private List<TrailerMovie> mTrailerMovies;

    public List<TrailerMovie> getTrailerMovies() {
        return mTrailerMovies;
    }

    public void setTrailerMovies(List<TrailerMovie> trailerMovies) {
        mTrailerMovies = trailerMovies;
    }
}
