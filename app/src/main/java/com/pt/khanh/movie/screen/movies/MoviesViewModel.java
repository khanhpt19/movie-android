package com.pt.khanh.movie.screen.movies;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.util.Log;
import android.widget.Toast;

import com.pt.khanh.movie.data.model.Genre;
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.model.MovieResult;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MoviesViewModel extends BaseObservable implements MoviesAdapter.ItemBookmarkListener {
    public ObservableBoolean mIsLoading = new ObservableBoolean();
    private MovieRepository mRepository;
    private MoviesAdapter mAdapter;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private List<Movie> mMovies = new ArrayList<>();
    private int mPage = 1;
    private int mId;
    private String mType;
    private int mTotalPage = 0;
    private Context mContext;

    public MoviesViewModel(MovieRepository repository, Context context) {
        mIsLoading.set(true);
        mContext = context;
        mRepository = repository;
        mAdapter = new MoviesAdapter();
        mAdapter.setListener(this);
        mIsLoading.set(false);
    }

    public void getMoviesByGenre(List<Genre> genres) {
        mIsLoading.set(true);
//        mId = id;28%2C12%2C16%2C35
        Disposable disposable = mRepository.getMoviesByListGenre(mPage, genIdGenres(genres))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mIsLoading.set(true);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResult>() {
                               @Override
                               public void accept(MovieResult movieResult) throws Exception {
                                   mIsLoading.set(false);
                                   mMovies = movieResult.getMovies();
                                   mTotalPage = movieResult.getTotalPage();
                                   mAdapter.addMovies(mMovies);
                               }
                           },
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

    public void getMoviesByCompany(int id) {
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
//        mIsLoading.set(true);
    }

    private void handleResponse(MovieResult response) {
        mIsLoading.set(false);
        mMovies = response.getMovies();
        Log.d("AMEN", "handleResponse: " + response.getMovies().toString());
        mTotalPage = response.getTotalPage();
        mAdapter.addMovies(mMovies);
    }

    public MoviesAdapter getAdapter() {
        return mAdapter;
    }

    public void onLoadMore() {
        mIsLoading.set(true);
        ++mPage;
        getMoviesByCategory(mType);
//        getMoviesByGenre(mId);
        if (mPage < mTotalPage) {
            mIsLoading.set(true);
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

    public int[] genIds(List<Genre> genres) {
        int genreIds[] = new int[genres.size()];
        for (int i = 0; i < genres.size(); i++) {
            genreIds[i] = genres.get(i).getId();
            Toast.makeText(mContext, "id:   " + genreIds, Toast.LENGTH_SHORT).show();
        }
        return genreIds;
    }

    public String genIdGenres(List<Genre> genres) {
        String s = null;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < genres.size(); i++) {
            stringBuilder.append(genres.get(i).getId());
            if (i != genres.size() - 1)
                stringBuilder.append("%2C");
        }
        s = stringBuilder.toString();
        return s;
    }

    public void onStop(){
        mCompositeDisposable.clear();
    }
}
