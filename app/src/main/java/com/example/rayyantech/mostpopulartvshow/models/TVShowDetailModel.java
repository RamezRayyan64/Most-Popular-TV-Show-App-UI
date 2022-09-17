package com.example.rayyantech.mostpopulartvshow.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVShowDetailModel {

    private int id;
    private String name;
    private String url;
    private String description;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;
    private String country;
    private String status;
    private String runtime;
    private String network;
    @SerializedName("image_path")
    private String imagePath;
    private String rating;
    @SerializedName("rating_count")
    private String ratingCount;
    private List<String> genres;
    private List<String> pictures;
    @SerializedName("episodes")
    private List<TVShowEpisodesModel> tvShowEpisodesModelList;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getCountry() {
        return country;
    }

    public String getStatus() {
        return status;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getNetwork() {
        return network;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getRating() {
        return rating;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public List<TVShowEpisodesModel> getTvShowEpisodesModelList() {
        return tvShowEpisodesModelList;
    }

}
