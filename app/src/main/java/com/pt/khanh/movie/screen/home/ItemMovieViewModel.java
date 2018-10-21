package com.pt.khanh.movie.screen.home;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.data.source.remote.MovieRemoteDataSource;
import com.pt.khanh.movie.utils.StringUtils;

public class ItemMovieViewModel extends BaseObservable {
    private static final String TAG = "AMEN";
    public ObservableField<Movie> mMovieObservableField = new ObservableField<>();
    private MovieRepository mRepository;

    public ItemMovieViewModel(Context activity) {
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
        Movie movie = mMovieObservableField.get();
        Log.d(TAG, "onClickItemTrending: " + movie.getTitle());
    }

    public void onItemClick() {
        Movie movie = mMovieObservableField.get();
        Log.d(TAG, "onItemClick: " + movie.getTitle());
    }

    public void onItemFavoriteClick() {
        Movie movie = mMovieObservableField.get();
        Log.d(TAG, "onItemFavoriteClick: " + movie.getTitle());
    }
}
