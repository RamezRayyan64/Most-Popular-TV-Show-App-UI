package com.rayyantech.mostpopulartvshow.helper;

import com.rayyantech.mostpopulartvshow.models.MostPopularTVShowModel;

public interface WatchlistListener {

    void onClickedTVShow(MostPopularTVShowModel mostPopularTVShowModel, int position);

    void onDeleteTVShow(MostPopularTVShowModel mostPopularTVShowModel, int position);
}
