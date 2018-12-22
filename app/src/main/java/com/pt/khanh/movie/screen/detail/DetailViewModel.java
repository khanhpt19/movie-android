package com.pt.khanh.movie.screen.detail;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Company;
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

public class DetailViewModel implements CompanyAdapter.ItemClickListener {
    public static final int ERROR_CODE = 111;
    public ObservableField<YouTubePlayer.OnInitializedListener> initListener = new ObservableField<>();
    public ObservableField<TrailerMovieAdapter> trailerMovieAdapter = new ObservableField<>();
    public ObservableField<Movie> movieDetailObservableField = new ObservableField<>();
    public ObservableField<DividerItemDecoration> observableDecoration = new ObservableField<>();
    public ObservableBoolean isFavourite = new ObservableBoolean();
    public ObservableBoolean mHasReview = new ObservableBoolean();
    public ObservableBoolean mHasRecommend = new ObservableBoolean();
    public ObservableBoolean mHasCast = new ObservableBoolean();
    public ObservableBoolean mHasGenres = new ObservableBoolean();
    public ObservableBoolean mHasCompany = new ObservableBoolean();

    private YouTubePlayer mYouTubePlayer;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private GenreDetailAdapter mGenreDetailAdapter;
    private CastAdapter mCastAdapter;
    private MovieByAdapter mMovieByAdapter;
    private ReviewAdapter mReviewAdapter;
    private CompanyAdapter mCompanyAdapter;
    private MovieRepository mRepository;
    private Context mContext;
    private Movie mMovie;

    public DetailViewModel() {

    }

    public void start(Context context, MovieRepository repository, Movie movie) {
        mContext = context;
        movieDetailObservableField.set(movie);
        mMovie = movie;
        mRepository = repository;
        observableDecoration.set(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mGenreDetailAdapter = new GenreDetailAdapter(context);
        mCastAdapter = new CastAdapter();
        mCompanyAdapter = new CompanyAdapter();
        mCompanyAdapter.setItemClickListener(this);
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
                        if (movieResult.getMovies().size() > 0)
                            mHasRecommend.set(true);
                    }
                }, error -> handleError(error));
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
                        if (reviewResult.getReviews().size() > 0)
                            mHasReview.set(true);
                    }
                }, error -> handleError(error));
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
        if (error.getMessage().equals(mContext.getString(R.string.error_no_internet)))
            Toast.makeText(mContext,
                    mContext.getString(R.string.toast_no_internet), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(mContext,
                    mContext.getString(R.string.toast_error_api), Toast.LENGTH_SHORT).show();
    }

    private void handleResponse(Movie response) {
        movieDetailObservableField.set(response);
        mGenreDetailAdapter.setGenres(response.getGenres());
        if (response.getGenres().size() > 0)
            mHasGenres.set(true);
        mCastAdapter.setCasts(response.getCastResult().getCasts());
        mCompanyAdapter.setCompanies(response.getCompanies());
        if (response.getCompanies().size() > 0) {
            mHasCompany.set(true);
        }
        if (response.getCastResult().getCasts().size() > 0)
            mHasCast.set(true);
        trailerMovieAdapter.set(
                new TrailerMovieAdapter(
                        response.getTrailerMovieResult().getTrailerMovies()));

        initListener.set(new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean wasRestored) {
                mYouTubePlayer = youTubePlayer;
                if (!wasRestored) {
                    if (response.getTrailerMovieResult().getTrailerMovies().size() != 0)
                        mYouTubePlayer.cueVideo(
                                response.getTrailerMovieResult().getTrailerMovies().get(0).getKey());
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult errorReason) {
                if (errorReason.isUserRecoverableError()) {
                    errorReason.getErrorDialog((Activity) mContext, ERROR_CODE).show();
                } else {
                    Toast.makeText(mContext, errorReason.toString(), Toast.LENGTH_SHORT).show();
                }
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

    public CompanyAdapter getCompanyAdapter() {
        return mCompanyAdapter;
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
        if (mYouTubePlayer != null) {
            mYouTubePlayer.release();
            mYouTubePlayer = null;
        }
    }

    @Override
    public void onItemClick(Company company) {

    }
}
