package com.pt.khanh.movie.screen.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.pt.khanh.movie.data.model.MovieDetail;
import com.pt.khanh.movie.data.repository.MovieRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DetailViewModel extends AndroidViewModel {
    public static final String TAG = "AMEN";
    public ObservableField<TrailerMovieAdapter> trailerMovieAdapter = new ObservableField<>();
    public GenreDetailAdapter mGenreDetailAdapter;
    private CastAdapter mCastAdapter;
    public ObservableField<MovieDetail> movieDetailObservableField = new ObservableField<>();
    public ViewPager mViewPager;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MovieRepository mRepository;

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void start(Context context, MovieRepository repository, long id){
        mRepository = repository;
        mGenreDetailAdapter = new GenreDetailAdapter(context);
        mCastAdapter = new CastAdapter();
        loadTrailers(id);
        Log.d(TAG, "start: ID = " + id);
    }

    public ObservableField<MovieDetail> getMovieDetailObservableField() {
        return movieDetailObservableField;
    }

    private void loadTrailers(long id){
        Disposable disposable = mRepository.getMovie(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<MovieDetail>() {
                    @Override
                    public void accept(MovieDetail movieDetail) throws Exception {
                        movieDetailObservableField.set(movieDetail);
                        Log.d(TAG, "getGenres: " + movieDetail.getGenres().size());
                        mGenreDetailAdapter.setGenres(movieDetail.getGenres());
                        mCastAdapter.setCasts(movieDetail.getCastResult().getCasts());
                        trailerMovieAdapter.set(
                                new TrailerMovieAdapter(
                                        movieDetail.getTrailerMovieResult().getTrailerMovies()));
                        Log.d(TAG, "accept: " + movieDetail.getTrailerMovieResult().getTrailerMovies().get(0).getName()
                        + "KEY = " + movieDetail.getTrailerMovieResult().getTrailerMovies().get(0).getKey());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("AMEN", "accept: " + throwable.getMessage());
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    public GenreDetailAdapter getGenreDetailAdapter() {
        return mGenreDetailAdapter;
    }

    public CastAdapter getCastAdapter() {
        return mCastAdapter;
    }

    public ObservableField<TrailerMovieAdapter> getTrailerMovieAdapter() {
        return trailerMovieAdapter;
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
    }

    public ViewPager getViewPager(){
        return mViewPager;
    }
}
