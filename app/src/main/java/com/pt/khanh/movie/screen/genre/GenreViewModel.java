package com.pt.khanh.movie.screen.genre;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.widget.Toast;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Genre;
import com.pt.khanh.movie.data.model.GenresResult;
import com.pt.khanh.movie.data.repository.MovieRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GenreViewModel extends BaseObservable {
    public ObservableBoolean mIsLoading = new ObservableBoolean();
    private static final int SPAN_COUT = 2;
    private static final int SPACING = 20;
    private GenreAdapter mGenreAdapter;
    public ObservableField<GridSpacingItemDecoration> observableDecoration = new ObservableField<>();
    private MovieRepository mMoviesRepository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Context mContext;

    public GenreViewModel(Context context, MovieRepository moviesRepository) {
        mContext = context;
        mMoviesRepository = moviesRepository;
        observableDecoration.set(new GridSpacingItemDecoration(SPAN_COUT, SPACING, true));
        mGenreAdapter = new GenreAdapter(context);
        loadGenres();
    }

    private void loadGenres() {
        Disposable disposable = mMoviesRepository.getGenres()
                .doOnSubscribe(disposable1 -> mIsLoading.set(true))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(genresResult -> {
                    mIsLoading.set(false);
                    mGenreAdapter.setGenres(getGenres(genresResult));
                }, error->handleError(error));
        mCompositeDisposable.add(disposable);
    }

    private void handleError(Throwable error) {
        mIsLoading.set(false);
        if (error.getMessage().equals(mContext.getString(R.string.error_no_internet)))
            Toast.makeText(mContext,
                    mContext.getString(R.string.toast_no_internet), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(mContext,
                    mContext.getString(R.string.toast_error_api), Toast.LENGTH_SHORT).show();
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
