package com.example.rayyantech.mostpopulartvshow.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.rayyantech.mostpopulartvshow.models.MostPopularTVShowModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface MostPopularTVShowDao {

    @Query("SELECT * FROM mostPopularTVShow")
    Flowable<List<MostPopularTVShowModel>> getWatchListMostPopularTVShow();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToWatchList(MostPopularTVShowModel mostPopularTVShowModel);

    @Delete
    Completable deleteFromWatchList(MostPopularTVShowModel mostPopularTVShowModel);

    @Query("SELECT * FROM mostPopularTVShow WHERE id = :tvShowId")
    Flowable<MostPopularTVShowModel> getWatchListMostPopularTVShow(int tvShowId);

}
