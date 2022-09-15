package com.rayyantech.mostpopulartvshow.models;

import com.google.gson.annotations.SerializedName;

public class TVShowsDetailModel {

    @SerializedName("tvShow")
    private TVShowDetailModel tvShowDetailModel;

    public TVShowDetailModel getTvShowDetailModel() {
        return tvShowDetailModel;
    }
}
