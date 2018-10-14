package com.pt.khanh.movie.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetail {
    @SerializedName("id")
    @Expose
    private long mId;
    @SerializedName("title")
    @Expose
    private String mTitle;
    @SerializedName("poster_path")
    @Expose
    private String mPosterPath;
    @SerializedName("backdrop_path")
    @Expose
    private String mBackdropPath;
    @SerializedName("overview")
    @Expose
    private String mOverview;
    @SerializedName("vote_average")
    @Expose
    private float mVoteAverage;
    @SerializedName("release_date")
    @Expose
    private String mReleaseDate;
    @SerializedName("genres")
    @Expose
    private List<Genre> mGenres;
    @SerializedName("production_companies")
    @Expose
    private List<Company> mCompanies;
    @SerializedName("videos")
    @Expose
    private TrailerMovieResult mTrailerMovieResult;
    @SerializedName("credits")
    @Expose
    private CastResult mCastResult;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public float getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public List<Genre> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genre> genres) {
        mGenres = genres;
    }

    public List<Company> getCompanies() {
        return mCompanies;
    }

    public void setCompanies(List<Company> companies) {
        mCompanies = companies;
    }

    public TrailerMovieResult getTrailerMovieResult() {
        return mTrailerMovieResult;
    }

    public void setTrailerMovieResult(TrailerMovieResult trailerMovieResult) {
        mTrailerMovieResult = trailerMovieResult;
    }

    public CastResult getCastResult() {
        return mCastResult;
    }

    public void setCastResult(CastResult castResult) {
        mCastResult = castResult;
    }
}
