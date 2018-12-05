package com.pt.khanh.movie.data.source.remote.service;

import com.pt.khanh.movie.data.model.Cast;
import com.pt.khanh.movie.data.model.GenresResult;
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.model.MovieResult;
import com.pt.khanh.movie.data.model.ReviewResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MovieAPI {
    @GET("movie/{type}")
    Observable<MovieResult> getMoviesCategory(@Path("type") String type,
                                              @Query("api_key") String api_key,
                                              @Query("page") int page);

    @GET("genre/movie/list")
    Observable<GenresResult> getGenres(@Query("api_key") String api_key);

    @GET("movie/{id}")
    Observable<Movie> getMovie(@Path("id") int id,
                               @Query("api_key") String api_key,
                               @Query("append_to_response") String append);

    @GET("discover/movie")
    Observable<MovieResult> getMoviesByGenre(@Query("api_key") String api_key,
                                             @Query("with_genres") int idGenre,
                                             @Query("page") int page);

    @GET("discover/movie")
    Observable<MovieResult> getMoviesByListGenre(@Query("api_key") String api_key,
                                                 @Query("page") int page,
                                                 @Query("with_genres") String idGenre);

    @GET("discover/movie")
    Observable<MovieResult> getMoviesByGenres(@Query("api_key") String api_key,
                                              @Query("page") int page,
                                              @QueryMap Map<String, Integer> genres);

    @GET("discover/movie")
    Observable<MovieResult> getMoviesByCast(@Query("api_key") String api_key,
                                            @Query("with_cast") int idCast,
                                            @Query("page") int page);

    @GET("discover/movie")
    Observable<MovieResult> getMoviesByCompany(@Query("api_key") String api_key,
                                               @Query("with_companies") int idCompany,
                                               @Query("page") int page);

    @GET("search/movie")
    Observable<MovieResult> searchMovie(@Query("api_key") String api_key,
                                        @Query("query") String name,
                                        @Query("page") int page);

    @GET("trending/movie/week")
    Observable<MovieResult> getMoviesTrend(@Query("api_key") String api_key);

    @GET("person/{person_id}")
    Observable<Cast> getCastDetail(@Path("person_id") int personId, @Query("api_key") String apiKey);

    @GET("movie/{id}/recommendations")
    Observable<MovieResult> getRecommend(@Path("id") int movieId, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{id}/reviews")
    Observable<ReviewResult> getReview(@Path("id") int movieId, @Query("api_key") String apiKey, @Query("page") int page);
}
