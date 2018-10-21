package com.pt.khanh.movie.screen.home;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.model.MovieResult;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.utils.StringUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends AndroidViewModel {
    public static final int MAX_ITEM_TRENDING_MOVIE = 5;
    private ObservableField<TrendingMovieAdapter> trendingMovieAdapter = new ObservableField<>();
    private ObservableField<Movie> moviePopular = new ObservableField<>();
    private ObservableField<Movie> movieNowPlaying = new ObservableField<>();
    private ObservableField<Movie> movieTopRated = new ObservableField<>();
    private ObservableField<Movie> movieUpComing = new ObservableField<>();
    private ViewPager mViewPager;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MovieRepository mRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void setupView(MovieRepository repository) {
        mRepository = repository;
        getMovieTrend();
        getMovieByPopular();
        getMovieByNowPlaying();
        getMovieByTopRated();
        getMovieByUpComing();
    }

    private void getMovieTrend() {
        Disposable disposable = mRepository.getMoviesTrend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleTrendingResponse(response),
                        error -> handleTrendingError(error));
        mCompositeDisposable.add(disposable);
    }

    public ObservableField<Movie> getMoviePopular() {
        return moviePopular;
    }

    public ObservableField<Movie> getMovieNowPlaying() {
        return movieNowPlaying;
    }

    public ObservableField<Movie> getMovieTopRated() {
        return movieTopRated;
    }

    public ObservableField<Movie> getMovieUpComing() {
        return movieUpComing;
    }

    public ObservableField<TrendingMovieAdapter> getTrendingMovieAdapter() {
        return trendingMovieAdapter;
    }

    private void handleTrendingError(Throwable error) {
        Log.d("AMEN", "handleTrendingError: " + error.getMessage());
    }

    private void handleTrendingResponse(MovieResult response) {
        List<Movie> movies = response.getMovies();
        trendingMovieAdapter.set(new TrendingMovieAdapter(movies));
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    private void getMovieByPopular() {
        Disposable disposable = mRepository.getMoviesCategory(
                getApplication().getString(R.string.key_popular), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResult -> moviePopular.set(movieResult.getMovies().get(0)),
                        throwable -> {
                        });
        mCompositeDisposable.add(disposable);
    }

    private void getMovieByNowPlaying() {
        Disposable disposable = mRepository.getMoviesCategory(
                getApplication().getString(R.string.key_now_playing), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResult -> movieNowPlaying.set(movieResult.getMovies().get(0)),
                        throwable -> {
                        });
        mCompositeDisposable.add(disposable);
    }

    private void getMovieByTopRated() {
        Disposable disposable = mRepository.getMoviesCategory(
                getApplication().getString(R.string.key_top_rated), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResult -> movieTopRated.set(movieResult.getMovies().get(0)),
                        throwable -> {
                        });
        mCompositeDisposable.add(disposable);
    }

    private void getMovieByUpComing() {
        Disposable disposable = mRepository.getMoviesCategory(
                getApplication().getString(R.string.key_upcoming), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResult -> movieUpComing.set(movieResult.getMovies().get(0)),
                        throwable -> {
                        });
        mCompositeDisposable.add(disposable);
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_foreground);
        Glide.with(imageView.getContext())
                .load(StringUtils.getUrlImage(url))
                .apply(requestOptions)
                .into(imageView);
    }
}
