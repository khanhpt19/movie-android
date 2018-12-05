package com.pt.khanh.movie.data.source.local;

import android.content.Context;

import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.source.MovieDatasource;
import com.pt.khanh.movie.data.source.local.sqlite.DBHelper;
import com.pt.khanh.movie.data.source.local.sqlite.MovieDAO;
import com.pt.khanh.movie.data.source.local.sqlite.MovieDAOImpl;

import java.util.List;

public class MovieLocalDatasource implements MovieDatasource.LocalDataSource {
    private static MovieLocalDatasource sInstance;
    private MovieDAO mMovieDAO;

    public MovieLocalDatasource(MovieDAO movieDAO) {
        mMovieDAO = movieDAO;
    }

    public static MovieLocalDatasource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MovieLocalDatasource(
                    MovieDAOImpl.getInstance(DBHelper.getInstance(context)));
        }
        return sInstance;
    }

    @Override
    public List<Movie> getMoviesLocal() {
        return mMovieDAO.getMovies();
    }

    @Override
    public Movie getMovieLocal(int id) {
        return mMovieDAO.getMovie(id);
    }

    @Override
    public boolean insertMovie(Movie movie) {
        return mMovieDAO.insertMovie(movie);
    }

    @Override
    public boolean deleteMovie(Movie movie) {
        return mMovieDAO.deleteMovie(movie);
    }

    @Override
    public boolean isFavourite(Movie movie) {
        return mMovieDAO.getMovie(movie.getId()) != null;
    }
}
