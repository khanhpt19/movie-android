package com.pt.khanh.movie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cast implements Parcelable {
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("character")
    @Expose
    private String mCharacter;
    @SerializedName("profile_path")
    @Expose
    private String mProfilePath;
    @SerializedName("order")
    @Expose
    private String mOrder;
    @SerializedName("birthday")
    @Expose
    private String mBirthday;
    @SerializedName("biography")
    @Expose
    private String mBiography;
    @SerializedName("place_of_birth")
    @Expose
    private String mPlaceOfBirth;
    @SerializedName("gender")
    @Expose
    private int mGender;

    protected Cast(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mCharacter = in.readString();
        mProfilePath = in.readString();
        mOrder = in.readString();
        mBirthday = in.readString();
        mBiography = in.readString();
        mPlaceOfBirth = in.readString();
        mGender = in.readInt();
    }

    public static final Creator<Cast> CREATOR = new Creator<Cast>() {
        @Override
        public Cast createFromParcel(Parcel in) {
            return new Cast(in);
        }

        @Override
        public Cast[] newArray(int size) {
            return new Cast[size];
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

    public String getCharacter() {
        return mCharacter;
    }

    public void setCharacter(String character) {
        mCharacter = character;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        mProfilePath = profilePath;
    }

    public String getOrder() {
        return mOrder;
    }

    public void setOrder(String order) {
        mOrder = order;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    public String getBiography() {
        return mBiography;
    }

    public void setBiography(String biography) {
        mBiography = biography;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
    }

    public int getGender() {
        return mGender;
    }

    public void setGender(int gender) {
        mGender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mCharacter);
        dest.writeString(mProfilePath);
        dest.writeString(mOrder);
        dest.writeString(mBirthday);
        dest.writeString(mBiography);
        dest.writeString(mPlaceOfBirth);
        dest.writeInt(mGender);
    }
}
