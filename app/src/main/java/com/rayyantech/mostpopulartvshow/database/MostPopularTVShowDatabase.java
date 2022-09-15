package com.rayyantech.mostpopulartvshow.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rayyantech.mostpopulartvshow.dao.MostPopularTVShowDao;
import com.rayyantech.mostpopulartvshow.models.MostPopularTVShowModel;

@Database(entities = MostPopularTVShowModel.class, version = 1, exportSchema = false)
public abstract class MostPopularTVShowDatabase extends RoomDatabase {

    private static MostPopularTVShowDatabase mostPopularTVShowDatabase;

    public static synchronized MostPopularTVShowDatabase getMostPopularTVShowDatabase(Context context) {
        if (mostPopularTVShowDatabase == null) {
            mostPopularTVShowDatabase = Room.databaseBuilder(context,
                    MostPopularTVShowDatabase.class,
                    "mostPopularTVShowDatabase")
                    .build();
        }
        return mostPopularTVShowDatabase;
    }

    public abstract MostPopularTVShowDao mostPopularTVShowDao();
}
