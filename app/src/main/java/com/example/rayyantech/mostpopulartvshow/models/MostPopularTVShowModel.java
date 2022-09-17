package com.example.rayyantech.mostpopulartvshow.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "mostPopularTVShow")
public class MostPopularTVShowModel implements Serializable, Parcelable {

    public MostPopularTVShowModel() {
    }

    @PrimaryKey
    private int id;
    private String name;
    private String network;
    @SerializedName("start_date")
    private String startedDate;
    private String country;
    private String status;
    @SerializedName("image_thumbnail_path")
    private String posterPath;

    protected MostPopularTVShowModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        network = in.readString();
        startedDate = in.readString();
        country = in.readString();
        status = in.readString();
        posterPath = in.readString();
    }

    public static final Creator<MostPopularTVShowModel> CREATOR = new Creator<MostPopularTVShowModel>() {
        @Override
        public MostPopularTVShowModel createFromParcel(Parcel in) {
            return new MostPopularTVShowModel(in);
        }

        @Override
        public MostPopularTVShowModel[] newArray(int size) {
            return new MostPopularTVShowModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNetwork() {
        return network;
    }

    public String getStartedDate() {
        return startedDate;
    }

    public String getCountry() {
        return country;
    }

    public String getStatus() {
        return status;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public void setStartedDate(String startedDate) {
        this.startedDate = startedDate;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(network);
        dest.writeString(startedDate);
        dest.writeString(country);
        dest.writeString(status);
        dest.writeString(posterPath);
    }
}
