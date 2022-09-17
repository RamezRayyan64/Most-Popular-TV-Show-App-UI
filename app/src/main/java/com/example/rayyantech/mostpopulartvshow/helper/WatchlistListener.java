package com.example.rayyantech.mostpopulartvshow.helper;

import com.example.rayyantech.mostpopulartvshow.models.MostPopularTVShowModel;

public interface WatchlistListener {

    void onClickedTVShow(MostPopularTVShowModel mostPopularTVShowModel, int position);

    void onDeleteTVShow(MostPopularTVShowModel mostPopularTVShowModel, int position);
}
