package com.rayyantech.mostpopulartvshow.helper;

import com.rayyantech.mostpopulartvshow.models.MostPopularTVShowsModel;
import com.rayyantech.mostpopulartvshow.models.TVShowDetailModel;
import com.rayyantech.mostpopulartvshow.models.TVShowsDetailModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EpisodateApiClient {

    @GET("most-popular")
    Call<MostPopularTVShowsModel> getMostPopularTVShows(@Query("page") int page);

    @GET("show-details")
    Call<TVShowsDetailModel> getTVShowsDetails(@Query("q") int tvShowId);

    @GET("search")
    Call<MostPopularTVShowsModel> getSearchedTVShow(@Query("q") String tvShowName, @Query("page") int page);

}
