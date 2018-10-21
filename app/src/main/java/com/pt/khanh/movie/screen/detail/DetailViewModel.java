package com.pt.khanh.movie.screen.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.pt.khanh.movie.data.model.MovieDetail;
import com.pt.khanh.movie.data.model.MovieResult;
import com.pt.khanh.movie.data.model.Review;
import com.pt.khanh.movie.data.model.ReviewResult;
import com.pt.khanh.movie.data.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DetailViewModel extends AndroidViewModel {
    public ObservableField<TrailerMovieAdapter> trailerMovieAdapter = new ObservableField<>();
    public GenreDetailAdapter mGenreDetailAdapter;
    private CastAdapter mCastAdapter;
    private MovieByAdapter mMovieByAdapter;
    private ReviewAdapter mReviewAdapter;
    public ObservableField<MovieDetail> movieDetailObservableField = new ObservableField<>();
    public ViewPager mViewPager;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MovieRepository mRepository;

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void start(Context context, MovieRepository repository, long id) {
        mRepository = repository;
        mGenreDetailAdapter = new GenreDetailAdapter(context);
        mCastAdapter = new CastAdapter();
        mMovieByAdapter = new MovieByAdapter();
        mReviewAdapter = new ReviewAdapter();
        loadTrailers(id);
        loadRecommend(id);
        loadReview(id);
    }

    public ObservableField<MovieDetail> getMovieDetailObservableField() {
        return movieDetailObservableField;
    }

    private void loadTrailers(long id) {
        Disposable disposable = mRepository.getMovie(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<MovieDetail>() {
                    @Override
                    public void accept(MovieDetail movieDetail) throws Exception {
                        movieDetailObservableField.set(movieDetail);
                        mGenreDetailAdapter.setGenres(movieDetail.getGenres());
                        mCastAdapter.setCasts(movieDetail.getCastResult().getCasts());
                        trailerMovieAdapter.set(
                                new TrailerMovieAdapter(
                                        movieDetail.getTrailerMovieResult().getTrailerMovies()));
//                        Log.d(TAG, "accept: " + movieDetail.getTrailerMovieResult().getTrailerMovies().get(0).getName()
//                        + "KEY = " + movieDetail.getTrailerMovieResult().getTrailerMovies().get(0).getKey());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void loadRecommend(long id) {
        Disposable disposable = mRepository.getMovieRecommend(id, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResult>() {
                    @Override
                    public void accept(MovieResult movieResult) throws Exception {
                        mMovieByAdapter.setMovies(movieResult.getMovies());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void loadReview(long id) {
        Disposable disposable = mRepository.getReview(id, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ReviewResult>() {
                    @Override
                    public void accept(ReviewResult reviewResult) throws Exception {
                        List<Review> reviews = new ArrayList<>();
                        int n = 3;
                        if (reviewResult.getReviews().size() < 3) {
                            n = reviewResult.getReviews().size();
                        }
                        for (int i = 0; i < n; i++) {
                            if (reviewResult.getReviews().get(i) != null) {
                                reviews.add(reviewResult.getReviews().get(i));
                            }
                        }
                        mReviewAdapter.setReviews(reviews);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    public GenreDetailAdapter getGenreDetailAdapter() {
        return mGenreDetailAdapter;
    }

    public MovieByAdapter getMovieByAdapter() {
        return mMovieByAdapter;
    }

    public ReviewAdapter getReviewAdapter() {
        return mReviewAdapter;
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

    public ViewPager getViewPager() {
        return mViewPager;
    }
}
