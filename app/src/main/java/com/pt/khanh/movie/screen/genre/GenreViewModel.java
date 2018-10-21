package com.pt.khanh.movie.screen.genre;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.pt.khanh.movie.data.model.Genre;
import com.pt.khanh.movie.data.model.GenresResult;
import com.pt.khanh.movie.data.repository.MovieRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GenreViewModel extends BaseObservable {
    private static final int SPAN_COUT = 2;
    private static final int SPACING = 20;
    private GenreAdapter mGenreAdapter;
    public ObservableField<GridSpacingItemDecoration> observableDecoration = new ObservableField<>();
    private MovieRepository mMoviesRepository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public GenreViewModel(Context context, MovieRepository moviesRepository) {
        mMoviesRepository = moviesRepository;
        observableDecoration.set(new GridSpacingItemDecoration(SPAN_COUT, SPACING, true));
        mGenreAdapter = new GenreAdapter(context);
        loadGenres();
    }

    private void loadGenres() {
        Disposable disposable = mMoviesRepository.getGenres()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(genresResult -> {
                    mGenreAdapter.setGenres(getGenres(genresResult));
                }, throwable -> {
                });
        mCompositeDisposable.add(disposable);
    }

    public GenreAdapter getGenreAdapter() {
        return mGenreAdapter;
    }

    private List<Genre> getGenres(GenresResult resutls) {
        List<Genre> genres = resutls.getGenres();
        for (int i = 0; i < genres.size(); i++) {
            genres.get(i).setImageResource(Genre.Image.RESOURCE[i]);
        }
        return genres;
    }
}
