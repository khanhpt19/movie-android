package com.pt.khanh.movie.data.source.local.sqlite;


import com.pt.khanh.movie.data.model.Movie;

import java.util.List;

public interface MovieDAO {
    List<Movie> getMovies();

    Movie getMovie(long id);

    boolean insertMovie(Movie movie);

    boolean deleteMovie(Movie movie);

    boolean isFavourite(Movie movie);
}
