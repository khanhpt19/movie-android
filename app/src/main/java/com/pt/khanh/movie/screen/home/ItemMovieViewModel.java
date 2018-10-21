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

public class ItemMovieViewModel extends BaseObservable {
    private static final String TAG = "AMEN";
    public ObservableField<Movie> mMovieObservableField = new ObservableField<>();
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
        mContext.startActivity(getMovieIntent(mContext, mMovieObservableField.get()));

    }

    public void onItemClick() {
        mContext.startActivity(getMovieIntent(mContext, mMovieObservableField.get()));
    }

    public void onItemFavoriteClick() {
        Movie movie = mMovieObservableField.get();
        Log.d(TAG, "onItemFavoriteClick: " + movie.getTitle());
    }

    public static Intent getMovieIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Constants.EXTRA_MOVIE, (Parcelable) movie);
        return intent;
    }
}
