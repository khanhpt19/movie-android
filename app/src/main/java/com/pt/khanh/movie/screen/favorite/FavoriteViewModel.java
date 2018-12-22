package com.pt.khanh.movie.screen.favorite;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.screen.movies.MoviesAdapter;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel implements MoviesAdapter.ItemBookmarkListener {
    private MovieRepository mRepository;
    private MoviesAdapter mAdapter;
    private List<Movie> mMovies;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
    }

    public void setup(MovieRepository repository, List<Movie> movies) {
        mRepository = repository;
        mAdapter = new MoviesAdapter();
        mMovies = mRepository.getMoviesLocal();
        mAdapter.setMovies(movies);
        mAdapter.setListener(this);
    }

    public MoviesAdapter getFavoriteAdapter() {
        return mAdapter;
    }

    @Override
    public void onBookmarkClick(Movie movie) {
        if (mRepository.isFavourite(movie)) {
            mRepository.deleteMovie(movie);
            mMovies.remove(movie);
            mAdapter.setMovies(mMovies);
        } else {
            mRepository.insertMovie(movie);
            mMovies.add(movie);
            mAdapter.setMovies(mMovies);
        }
    }
}
