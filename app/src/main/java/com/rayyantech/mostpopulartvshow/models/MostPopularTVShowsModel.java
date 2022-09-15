package com.rayyantech.mostpopulartvshow.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MostPopularTVShowsModel {

    private int page;
    private int pages;
    @SerializedName("tv_shows")
    private List<MostPopularTVShowModel> mostPopularTVShowModel;

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public List<MostPopularTVShowModel> getMostPopularTVShowModel() {
        return mostPopularTVShowModel;
    }
}
