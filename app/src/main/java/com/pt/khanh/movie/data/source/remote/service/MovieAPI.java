package com.pt.khanh.movie.data.source.remote.service;

import com.pt.khanh.movie.data.model.Cast;
import com.pt.khanh.movie.data.model.GenresResult;
import com.pt.khanh.movie.data.model.MovieDetail;
import com.pt.khanh.movie.data.model.MovieResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPI {
    @GET("movie/{type}")
    Observable<MovieResult> getMoviesCategory(@Path("type") String type,
                                              @Query("api_key") String api_key,
                                              @Query("page") int page);

    @GET("genre/movie/list")
    Observable<GenresResult> getGenres(@Query("api_key") String api_key);

    @GET("movie/{id}")
    Observable<MovieDetail> getMovie(@Path("id") long id,
                                     @Query("api_key") String api_key,
                                     @Query("append_to_response") String append);

    @GET("discover/movie")
    Observable<MovieResult> getMoviesByGenre(@Query("api_key") String api_key,
                                             @Query("with_genres") long idGenre,
                                             @Query("page") int page);

    @GET("discover/movie")
    Observable<MovieResult> getMoviesByCast(@Query("api_key") String api_key,
                                            @Query("with_cast") long idCast,
                                            @Query("page") int page);

    @GET("discover/movie")
    Observable<MovieResult> getMoviesByCompany(@Query("api_key") String api_key,
                                               @Query("with_companies") long idCompany,
                                               @Query("page") int page);

    @GET("search/movie")
    Observable<MovieResult> searchMovie(@Query("api_key") String api_key,
                                        @Query("query") String name,
                                        @Query("page") int page);

    @GET("trending/movie/week")
    Observable<MovieResult> getMoviesTrend(@Query("api_key") String api_key);

    @GET("person/{person_id}")
    Observable<Cast> getCastDetail(@Path("person_id") long personId, @Query("api_key") String apiKey);
}
