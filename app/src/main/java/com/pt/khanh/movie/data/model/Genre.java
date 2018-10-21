package com.pt.khanh.movie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pt.khanh.movie.R;

public class Genre implements Parcelable {
    @SerializedName("id")
    @Expose
    private long mId;
    @SerializedName("name")
    @Expose
    private String mName;
    private int mImageResource;

    protected Genre(Parcel in) {
        mId = in.readLong();
        mName = in.readString();
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public void setImageResource(int imageResource) {
        mImageResource = imageResource;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mName);
    }

    public static final class Image {
        public static final int[] RESOURCE = {R.drawable.action,
                R.drawable.adventure, R.drawable.animetion, R.drawable.comedy, R.drawable.crime,
                R.drawable.documentary, R.drawable.drama, R.drawable.family,
                R.drawable.fantasy, R.drawable.history, R.drawable.horror,
                R.drawable.music, R.drawable.mystery, R.drawable.romance,
                R.drawable.science_fiction, R.drawable.tvmovie, R.drawable.thriller,
                R.drawable.war, R.drawable.western};
    }
}
