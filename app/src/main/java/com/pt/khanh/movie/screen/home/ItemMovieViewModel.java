package com.pt.khanh.movie.screen.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.data.source.remote.MovieRemoteDataSource;
import com.pt.khanh.movie.screen.detail.DetailActivity;
import com.pt.khanh.movie.utils.Constants;
import com.pt.khanh.movie.utils.StringUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ItemMovieViewModel extends BaseObservable {
    private static final String TAG = "AMEN";
    public ObservableField<Movie> mMovieObservableField = new ObservableField<>();
    public ObservableField<Movie> mMovieDetailObservableField = new ObservableField<>();
    private MovieRepository mRepository;
    private Context mContext;


    public ItemMovieViewModel(Context context) {
        mContext = context;
        mRepository = MovieRepository
                .getInstance(MovieRemoteDataSource.getInstance());
    }

    public void setMovie(Movie movie) {
        mMovieObservableField.set(movie);
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.default_movie);
        Glide.with(imageView.getContext())
                .load(StringUtils.getUrlImage(url))
                .apply(requestOptions)
                .into(imageView);
    }

    public void onClickItemTrending() {
        Disposable disposable = mRepository.getMovie(mMovieObservableField.get().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movieDetail) throws Exception {
                        mMovieDetailObservableField.set(movieDetail);
                        mContext.startActivity(getMovieIntent(mContext, mMovieObservableField.get(), movieDetail));
                    }
                });
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(disposable);
    }

    public ObservableField<Movie> getMovieDetailObservableField() {
        return mMovieDetailObservableField;
    }

    public void onItemClick() {
        Disposable disposable = mRepository.getMovie(mMovieObservableField.get().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movieDetail) throws Exception {
                        mMovieDetailObservableField.set(movieDetail);
                        mContext.startActivity(getMovieIntent(mContext, mMovieObservableField.get(), movieDetail));
                    }
                });
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(disposable);
    }

    public void onItemFavoriteClick() {
        Movie movie = mMovieObservableField.get();
        Log.d(TAG, "onItemFavoriteClick: " + movie.getTitle());
    }

    public static Intent getMovieIntent(Context context, Movie movie, Movie movieDetail) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Constants.EXTRA_MOVIE, (Parcelable) movie);
        intent.putExtra(Constants.EXTRA_TRAILER, (Parcelable) movieDetail);
        return intent;
    }
}
