package com.pt.khanh.movie.screen.movies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.model.MovieResult;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.utils.Constants;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MoviesViewModel extends AndroidViewModel implements MoviesAdapter.ItemBookmarkListener {
    public ObservableBoolean mIsLoadingMore = new ObservableBoolean();
    public ObservableBoolean mIsLoadingProgress = new ObservableBoolean();
    private MovieRepository mRepository;
    private MoviesAdapter mAdapter;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private List<Movie> mMovies;
    private int mPage = 1;
    private long mId;
    private String mType;
    private int mTotalPage = 0;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
    }

    public void setupViewModel(MovieRepository repository) {
        mRepository = repository;
        mAdapter = new MoviesAdapter();
        mAdapter.setListener(this);
    }


    public void getMoviesByGenre(long id) {
        mId = id;
        Disposable disposable = mRepository.getMoviesByGenre(id, mPage)
                .doOnSubscribe(disposable1 -> mIsLoadingProgress.set(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response),
                        error -> handleError(error));
        mCompositeDisposable.add(disposable);
    }

    public void getMoviesByCategory(String type) {
        mType = type;
        Disposable disposable = mRepository.getMoviesCategory(type, mPage)
                .doOnSubscribe(disposable1 -> mIsLoadingProgress.set(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response),
                        error -> handleError(error));
        mCompositeDisposable.add(disposable);
    }

    public void getMoviesByCompany(long id) {
        mId = id;
        Disposable disposable = mRepository.getMoviesByCompany(id, Constants.PAGE_DEFAULT)
                .doOnSubscribe(disposable1 -> mIsLoadingProgress.set(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response),
                        error -> handleError(error));
        mCompositeDisposable.add(disposable);
    }

    private void handleError(Throwable error) {
        mIsLoadingProgress.set(false);
        if (error.getMessage().equals(getApplication().getString(R.string.error_no_internet)))
            Toast.makeText(getApplication(),
                    getApplication().getString(R.string.toast_no_internet), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplication(),
                    getApplication().getString(R.string.toast_error_api), Toast.LENGTH_SHORT).show();
    }

    private void handleResponse(MovieResult response) {
        mIsLoadingProgress.set(false);
        mMovies = response.getMovies();
        mTotalPage = response.getTotalPage();
        mAdapter.addMovies(mMovies);
    }

    public MoviesAdapter getAdapter() {
        return mAdapter;
    }

    public void onLoadMore() {
        ++mPage;
        getMoviesByCategory(mType);
        getMoviesByGenre(mId);
        if (mPage < mTotalPage) {
            getMoviesByCompany(mId);
        } else return;
    }

    @Override
    public void onBookmarkClick(Movie movie) {
        if (mRepository.isFavourite(movie)) {
            mRepository.deleteMovie(movie);
        } else {
            mRepository.insertMovie(movie);
        }
        mAdapter.notifyDataSetChanged();
    }
}
