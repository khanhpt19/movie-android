package com.pt.khanh.movie.screen.genre;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.os.Parcelable;
import android.util.Log;

import com.pt.khanh.movie.data.model.Genre;
import com.pt.khanh.movie.screen.movies.MoviesActivity;
import com.pt.khanh.movie.utils.Constants;

public class ItemGenreViewModel extends BaseObservable {
    public ObservableField<Genre> mGenreObservableField = new ObservableField<>();
    private Context mContext;

    public ItemGenreViewModel(Context context) {
        mContext = context;
    }

    public void setGenres(Genre genres) {
        mGenreObservableField.set(genres);
    }

    public void onClickItemGenres() {
        mContext.startActivity(getMovieIntent(mContext, mGenreObservableField.get()));
    }

    public static Intent getMovieIntent(Context context, Genre genre) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putExtra(Constants.EXTRA_GENRE, (Parcelable) genre);
        return intent;
    }
}
