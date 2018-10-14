package com.pt.khanh.movie.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movie")
public class Movie implements Parcelable {
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    private int mId;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    @Expose
    private String mTitle;

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    @Expose
    private String mPosterPath;

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    @Expose
    private String mBackdropPath;

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    @Expose
    private String mOverview;

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    @Expose
    private float mVoteAverage;

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    @Expose
    private String mReleaseDate;

    @SerializedName("popularity")
    @ColumnInfo(name = "popularity")
    @Expose
    private String mPopularity;

    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    @Expose
    private String mOriginalLanguage;

    protected Movie(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mPosterPath = in.readString();
        mBackdropPath = in.readString();
        mOverview = in.readString();
        mVoteAverage = in.readFloat();
        mReleaseDate = in.readString();
        mPopularity = in.readString();
        mOriginalLanguage = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
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

    public String getPopularity() {
        return mPopularity;
    }

    public void setPopularity(String popularity) {
        mPopularity = popularity;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTitle);
        dest.writeString(mPosterPath);
        dest.writeString(mBackdropPath);
        dest.writeString(mOverview);
        dest.writeFloat(mVoteAverage);
        dest.writeString(mReleaseDate);
        dest.writeString(mPopularity);
        dest.writeString(mOriginalLanguage);
    }
}
