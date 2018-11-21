package com.pt.khanh.movie.screen.detail;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.model.MovieResult;
import com.pt.khanh.movie.data.model.ReviewResult;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.utils.Constants;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DetailViewModel {
    private static final String TAG = "AMEN";
    public static final int ERROR_CODE = 111;
    public ObservableField<YouTubePlayer.OnInitializedListener> initListener = new ObservableField<>();
    public ObservableField<TrailerMovieAdapter> trailerMovieAdapter = new ObservableField<>();
    public ObservableField<Movie> movieDetailObservableField = new ObservableField<>();
    public ObservableBoolean isFavourite = new ObservableBoolean();
    public ObservableBoolean mHasReview = new ObservableBoolean();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private GenreDetailAdapter mGenreDetailAdapter;
    private CastAdapter mCastAdapter;
    private MovieByAdapter mMovieByAdapter;
    private ReviewAdapter mReviewAdapter;
    private MovieRepository mRepository;
    private Context mContext;
    private Movie mMovie;

    public DetailViewModel() {

    }

    public void start(Context context, MovieRepository repository, Movie movie) {
        mContext = context;
        mMovie = movie;
        mRepository = repository;
        mGenreDetailAdapter = new GenreDetailAdapter(context);
        mCastAdapter = new CastAdapter();
        mMovieByAdapter = new MovieByAdapter();
        mReviewAdapter = new ReviewAdapter();
        loadRecommend(movie.getId());
        loadReview(movie.getId());
        loadVideo(movie);
        if (mRepository.isFavourite(mMovie)) {
            isFavourite.set(true);
        } else {
            isFavourite.set(false);
        }
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
        Disposable disposable = mRepository.getReview(id, Constants.PAGE_DEFAULT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ReviewResult>() {
                    @Override
                    public void accept(ReviewResult reviewResult) throws Exception {
                        mReviewAdapter.setReviews(reviewResult.getReviews());
                        if(reviewResult.getReviews().size()>0)
                            mHasReview.set(true);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        mCompositeDisposable.add(disposable);
    }

    public void loadVideo(Movie movie) {
        mCompositeDisposable.add(mRepository.getMovie(movie.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response),
                        error -> handleError(error)));
    }

    private void handleError(Throwable error) {
    }

    private void handleResponse(Movie response) {
        movieDetailObservableField.set(response);
        mGenreDetailAdapter.setGenres(response.getGenres());
        mCastAdapter.setCasts(response.getCastResult().getCasts());
        trailerMovieAdapter.set(
                new TrailerMovieAdapter(
                        response.getTrailerMovieResult().getTrailerMovies()));

        initListener.set(new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    if (response.getTrailerMovieResult().getTrailerMovies().size() != 0)
                        youTubePlayer.cueVideo(
                                response.getTrailerMovieResult().getTrailerMovies().get(0).getKey());
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                youTubeInitializationResult.getErrorDialog((Activity) mContext, ERROR_CODE);
            }
        });
    }

    public ObservableField<Movie> getMovieDetailObservableField() {
        return movieDetailObservableField;
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

    public void onFavoriteClick(View view) {
        if (mRepository.isFavourite(mMovie)) {
            mRepository.deleteMovie(mMovie);
            isFavourite.set(false);
        } else {
            mRepository.insertMovie(mMovie);
            isFavourite.set(true);
        }
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }
}
