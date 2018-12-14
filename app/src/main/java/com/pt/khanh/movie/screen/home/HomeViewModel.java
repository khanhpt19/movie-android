package com.pt.khanh.movie.screen.home;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Category;
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.model.MovieResult;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.screen.movies.MoviesActivity;
import com.pt.khanh.movie.utils.Constants;
import com.pt.khanh.movie.utils.StringUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends AndroidViewModel {
    private ObservableField<TrendingMovieAdapter> trendingMovieAdapter = new ObservableField<>();
    private ObservableField<Movie> moviePopular = new ObservableField<>();
    private ObservableField<Movie> movieNowPlaying = new ObservableField<>();
    private ObservableField<Movie> movieTopRated = new ObservableField<>();
    private ObservableField<Movie> movieUpComing = new ObservableField<>();
    private ViewPager mViewPager;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MovieRepository mRepository;
    private Category mCategory = new Category();

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
                .subscribe(movieResult -> movieNowPlaying.set(movieResult.getMovies().get(1)),
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
                .subscribe(movieResult -> movieUpComing.set(movieResult.getMovies().get(1)),
                        throwable -> {
                        });
        mCompositeDisposable.add(disposable);
    }

    public void onUpcomingClick(View view) {
        mCategory.setKey(view.getContext().getString(R.string.key_upcoming));
        mCategory.setName(view.getContext().getString(R.string.name_upcoming));
        view.getContext().startActivity(getMovieIntent(view.getContext(), mCategory));
    }

    public void onTopRatedClick(View view) {
        mCategory.setKey(view.getContext().getString(R.string.key_top_rated));
        mCategory.setName(view.getContext().getString(R.string.name_top_rated));
        view.getContext().startActivity(getMovieIntent(view.getContext(), mCategory));
    }

    public void onNowPlayingClick(View view) {
        mCategory.setKey(view.getContext().getString(R.string.key_now_playing));
        mCategory.setName(view.getContext().getString(R.string.name_now_playing));
        view.getContext().startActivity(getMovieIntent(view.getContext(), mCategory));
    }

    public void onPopularClick(View view) {
        mCategory.setKey(view.getContext().getString(R.string.key_popular));
        mCategory.setName(view.getContext().getString(R.string.name_popular));
        view.getContext().startActivity(getMovieIntent(view.getContext(), mCategory));
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

    public static Intent getMovieIntent(Context context, Category category) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putExtra(Constants.EXTRA_CATEGORY, (Parcelable) category);
        return intent;
    }
}
