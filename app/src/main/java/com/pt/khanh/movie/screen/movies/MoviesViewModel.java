package com.pt.khanh.movie.screen.movies;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;

import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.model.MovieResult;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.utils.Constants;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MoviesViewModel extends BaseObservable {
    public ObservableBoolean mIsLoading = new ObservableBoolean();
    private MovieRepository mRepository;
    private MoviesAdapter mAdapter;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private List<Movie> mMovies;
    private int mPage = 1;
    private long mId;
    private String mType;
    private int mTotalPage = 0;

    public MoviesViewModel(MovieRepository repository) {
        mIsLoading.set(true);
        mRepository = repository;
        mAdapter = new MoviesAdapter();
    }

    public void getMoviesByGenre(long id) {
        mIsLoading.set(true);
        mId = id;
        Disposable disposable = mRepository.getMoviesByGenre(id, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response),
                        error -> handleError(error));
        mCompositeDisposable.add(disposable);
    }

    public void getMoviesByCategory(String type) {
        mIsLoading.set(true);
        mType = type;
        Disposable disposable = mRepository.getMoviesCategory(type, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response),
                        error -> handleError(error));
        mCompositeDisposable.add(disposable);
    }

    public void getMoviesByCompany(long id) {
        mIsLoading.set(true);
        mId = id;
        Disposable disposable = mRepository.getMoviesByCompany(id, Constants.PAGE_DEFAULT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response),
                        error -> handleError(error));
        mCompositeDisposable.add(disposable);
    }

    private void handleError(Throwable error) {
        mIsLoading.set(true);
    }

    private void handleResponse(MovieResult response) {
        mIsLoading.set(false);
        mMovies = response.getMovies();
        mTotalPage = response.getTotalPage();
        mAdapter.setMovies(mMovies);
    }

    public MoviesAdapter getAdapter() {
        return mAdapter;
    }

    public void onLoadMore() {
        mIsLoading.set(true);
        ++mPage;
        getMoviesByCategory(mType);
        getMoviesByGenre(mId);
        if (mPage < mTotalPage) {
            mIsLoading.set(true);
            getMoviesByCompany(mId);
        } else return;
    }
}
