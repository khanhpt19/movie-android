package com.pt.khanh.movie;

import android.app.Application;

import com.pt.khanh.movie.data.source.remote.service.MovieServiceClient;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MovieServiceClient.initialize(this);
    }
}
