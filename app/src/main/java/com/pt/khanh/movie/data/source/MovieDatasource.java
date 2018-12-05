package com.pt.khanh.movie.data.source;

import com.pt.khanh.movie.data.model.Cast;
import com.pt.khanh.movie.data.model.GenresResult;
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.model.MovieResult;
import com.pt.khanh.movie.data.model.ReviewResult;

import java.util.List;

import io.reactivex.Observable;

public interface MovieDatasource {
    interface RemoteDataSource {
        Observable<MovieResult> searchMovie(String name, int page);

        Observable<MovieResult> getMoviesCategory(String type, int page);

        Observable<GenresResult> getGenres();

        Observable<Movie> getMovie(int id);

        Observable<MovieResult> getMoviesByGenre(int page, int id);

        Observable<MovieResult> getMoviesByGenres(int page, int... id);

        Observable<MovieResult> getMoviesByListGenre(int page, String id);

        Observable<MovieResult> getMoviesByCast(int id, int page);

        Observable<MovieResult> getMoviesByCompany(int id, int page);

        Observable<MovieResult> getMoviesTrend();

        Observable<Cast> getCastDetail(int id);

        Observable<MovieResult> getMovieRecommend(int id, int page);

        Observable<ReviewResult> getReview(int id, int page);
    }

    interface LocalDataSource {
        List<Movie> getMoviesLocal();

        Movie getMovieLocal(int id);

        boolean insertMovie(Movie movie);

        boolean deleteMovie(Movie movie);

        boolean isFavourite(Movie movie);
    }
}
