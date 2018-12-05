package com.pt.khanh.movie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company implements Parcelable {
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("logo_path")
    @Expose
    private String mLogoPath;
    @SerializedName("headquarters")
    @Expose
    private String mHeadquarters;
    @SerializedName("description")
    @Expose
    private String mDescription;

    protected Company(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mLogoPath = in.readString();
        mHeadquarters = in.readString();
        mDescription = in.readString();
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLogoPath() {
        return mLogoPath;
    }

    public void setLogoPath(String logoPath) {
        mLogoPath = logoPath;
    }

    public String getHeadquarters() {
        return mHeadquarters;
    }

    public void setHeadquarters(String headquarters) {
        mHeadquarters = headquarters;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mLogoPath);
        dest.writeString(mHeadquarters);
        dest.writeString(mDescription);
    }
}
