package com.pt.khanh.movie.data.source.remote.service;

import android.app.Application;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceClient {
    public static final int READ_TIMEOUT = 5000;
    public static final int WRITE_TIMEOUT = 5000;
    public static final int CONNECT_TIMEOUT = 5000;

    static <T> T createService(Application application, String endPoint, Class<T> serviceClass) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true);
        int cacheSize = 10 * 1024 * 1024;
        httpClientBuilder.cache(new Cache(application.getCacheDir(), cacheSize));
        Retrofit retrofit = new Retrofit.Builder().baseUrl(endPoint)
                .client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }
}
