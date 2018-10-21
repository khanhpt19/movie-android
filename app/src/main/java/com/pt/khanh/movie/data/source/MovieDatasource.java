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

        Observable<Movie> getMovie(long id);

        Observable<MovieResult> getMoviesByGenre(long id, int page);

        Observable<MovieResult> getMoviesByCast(long id, int page);

        Observable<MovieResult> getMoviesByCompany(long id, int page);

        Observable<MovieResult> getMoviesTrend();

        Observable<Cast> getCastDetail(long id);

        Observable<MovieResult> getMovieRecommend(long id, int page);

        Observable<ReviewResult> getReview(long id, int page);
    }

    interface LocalDataSource {
        List<Movie> getMoviesLocal();

        Movie getMovieLocal(long id);

        boolean insertMovie(Movie movie);

        boolean deleteMovie(Movie movie);

        boolean isFavourite(Movie movie);
    }
}
