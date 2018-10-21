package com.pt.khanh.movie.data.source.remote;

import com.pt.khanh.movie.BuildConfig;
import com.pt.khanh.movie.data.model.Cast;
import com.pt.khanh.movie.data.model.GenresResult;
import com.pt.khanh.movie.data.model.MovieDetail;
import com.pt.khanh.movie.data.model.MovieResult;
import com.pt.khanh.movie.data.model.ReviewResult;
import com.pt.khanh.movie.data.source.MovieDatasource;
import com.pt.khanh.movie.data.source.remote.service.MovieAPI;
import com.pt.khanh.movie.data.source.remote.service.MovieServiceClient;
import com.pt.khanh.movie.utils.StringUtils;

import io.reactivex.Observable;

public class MovieRemoteDataSource implements MovieDatasource.RemoteDataSource {
    private static MovieRemoteDataSource sInstance;
    private MovieAPI mAPI;

    public MovieRemoteDataSource(MovieAPI api) {
        mAPI = api;
    }

    public static synchronized MovieRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new MovieRemoteDataSource(MovieServiceClient.getInstance());
        }
        return sInstance;
    }

    @Override
    public Observable<MovieResult> searchMovie(String name, int page) {
        return mAPI.searchMovie(BuildConfig.API_KEY, name, page);
    }

    @Override
    public Observable<MovieResult> getMoviesCategory(String type, int page) {
        return mAPI.getMoviesCategory(type, BuildConfig.API_KEY, page);
    }

    @Override
    public Observable<GenresResult> getGenres() {
        return mAPI.getGenres(BuildConfig.API_KEY);
    }

    @Override
    public Observable<MovieDetail> getMovie(long id) {
        return mAPI.getMovie(id, BuildConfig.API_KEY, StringUtils.appendToResponse());
    }

    @Override
    public Observable<MovieResult> getMoviesByGenre(long id, int page) {
        return mAPI.getMoviesByGenre(BuildConfig.API_KEY, id, page);
    }

    @Override
    public Observable<MovieResult> getMoviesByCast(long id, int page) {
        return mAPI.getMoviesByCast(BuildConfig.API_KEY, id, page);
    }

    @Override
    public Observable<MovieResult> getMoviesByCompany(long id, int page) {
        return mAPI.getMoviesByCompany(BuildConfig.API_KEY, id, page);
    }

    @Override
    public Observable<MovieResult> getMoviesTrend() {
        return mAPI.getMoviesTrend(BuildConfig.API_KEY);
    }

    @Override
    public Observable<Cast> getCastDetail(long id) {
        return mAPI.getCastDetail(id, BuildConfig.API_KEY);
    }

    @Override
    public Observable<MovieResult> getMovieRecommend(long id, int page) {
        return mAPI.getRecommend(id, BuildConfig.API_KEY, page);
    }

    @Override
    public Observable<ReviewResult> getReview(long id, int page) {
        return mAPI.getReview(id, BuildConfig.API_KEY, page);
    }
}
