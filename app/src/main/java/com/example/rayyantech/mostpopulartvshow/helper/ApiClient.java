package com.example.rayyantech.mostpopulartvshow.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit retrofit;
    public static EpisodateApiClient episodateApiClient;

    public static EpisodateApiClient getEpisodateApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.episodate.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        episodateApiClient = retrofit.create(EpisodateApiClient.class);
        return episodateApiClient;
    }

}
