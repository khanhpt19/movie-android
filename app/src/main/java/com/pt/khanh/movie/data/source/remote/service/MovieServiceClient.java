package com.pt.khanh.movie.data.source.remote.service;

import android.app.Application;
import android.support.annotation.NonNull;

import com.pt.khanh.movie.utils.Constants;

public class MovieServiceClient extends ServiceClient {
    private static MovieAPI sAPI;

    public static void initialize(@NonNull Application application) {
        sAPI = createService(application, Constants.BASE_URL, MovieAPI.class);
    }

    public static MovieAPI getInstance() {
        if (sAPI == null) {
            throw new IllegalStateException(MovieServiceClient.class.getSimpleName());
        }
        return sAPI;
    }
}
