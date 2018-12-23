package com.pt.khanh.movie.screen.favorite;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.screen.movies.MoviesAdapter;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel implements MoviesAdapter.ItemBookmarkListener {
    public ObservableField<Boolean> mHasFavorites = new ObservableField<>();
    private MovieRepository mRepository;
    private MoviesAdapter mAdapter;
    private List<Movie> mMovies;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
    }

    public void setup(MovieRepository repository, List<Movie> movies) {
        mHasFavorites.set(true);
        mRepository = repository;
        mAdapter = new MoviesAdapter();
        mMovies = movies;
        mMovies = mRepository.getMoviesLocal();
        mAdapter.setMovies(mMovies);
        if (mAdapter != null) {
            mHasFavorites.set(false);
        }
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
        }
    }
}
