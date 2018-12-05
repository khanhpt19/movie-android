package com.pt.khanh.movie.data.source.remote;

import com.pt.khanh.movie.BuildConfig;
import com.pt.khanh.movie.data.model.Cast;
import com.pt.khanh.movie.data.model.GenresResult;
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.model.MovieResult;
import com.pt.khanh.movie.data.model.ReviewResult;
import com.pt.khanh.movie.data.source.MovieDatasource;
import com.pt.khanh.movie.data.source.remote.service.MovieAPI;
import com.pt.khanh.movie.data.source.remote.service.MovieServiceClient;
import com.pt.khanh.movie.utils.Constants;
import com.pt.khanh.movie.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

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
    public Observable<Movie> getMovie(int id) {
        return mAPI.getMovie(id, BuildConfig.API_KEY, StringUtils.appendToResponse());
    }

    @Override
    public Observable<MovieResult> getMoviesByGenre(int page, int id) {
        return mAPI.getMoviesByGenre(BuildConfig.API_KEY, id, page);
    }

    @Override
    public Observable<MovieResult> getMoviesByGenres(int page, int... id) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < id.length; i++) {
            map.put(Constants.PARAM_GENRE, id[i]);
        }
        return mAPI.getMoviesByGenres(BuildConfig.API_KEY, page, map);

    }

    @Override
    public Observable<MovieResult> getMoviesByListGenre(int page, String id) {
        return mAPI.getMoviesByListGenre(BuildConfig.API_KEY, page, id);
    }

    @Override
    public Observable<MovieResult> getMoviesByCast(int id, int page) {
        return mAPI.getMoviesByCast(BuildConfig.API_KEY, id, page);
    }

    @Override
    public Observable<MovieResult> getMoviesByCompany(int id, int page) {
        return mAPI.getMoviesByCompany(BuildConfig.API_KEY, id, page);
    }

    @Override
    public Observable<MovieResult> getMoviesTrend() {
        return mAPI.getMoviesTrend(BuildConfig.API_KEY);
    }

    @Override
    public Observable<Cast> getCastDetail(int id) {
        return mAPI.getCastDetail(id, BuildConfig.API_KEY);
    }

    @Override
    public Observable<MovieResult> getMovieRecommend(int id, int page) {
        return mAPI.getRecommend(id, BuildConfig.API_KEY, page);
    }

    @Override
    public Observable<ReviewResult> getReview(int id, int page) {
        return mAPI.getReview(id, BuildConfig.API_KEY, page);
    }
}
