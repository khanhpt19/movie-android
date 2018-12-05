package com.pt.khanh.movie.screen.genre;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Parcelable;
import android.view.View;

import com.pt.khanh.movie.data.model.Genre;
import com.pt.khanh.movie.screen.movies.MoviesActivity;
import com.pt.khanh.movie.utils.Constants;

public class ItemGenreViewModel extends BaseObservable {
    public ObservableBoolean isItemClick = new ObservableBoolean();
    public ObservableField<Genre> mGenreObservableField = new ObservableField<>();
    private Context mContext;
    private GenreAdapter.onItemClickListener mItemClickListener;

    public ItemGenreViewModel(Context context, GenreAdapter.onItemClickListener itemClickListener) {
        isItemClick.set(false);
        mContext = context;
        mItemClickListener = itemClickListener;
    }

    public void setGenres(Genre genres) {
        mGenreObservableField.set(genres);
    }

    public void onItemClick(View view) {
        if(isItemClick.get() == false){
            isItemClick.set(true);
            mItemClickListener.onItemChoose(mGenreObservableField.get());
        }
        else {
            isItemClick.set(false);
            mItemClickListener.onItemNotChoose(mGenreObservableField.get());
        }
    }

//    public void onClickItemGenres() {
//        Log.d("AMEN", "onClickItemGenres: "  + mGenreObservableField.get().getName());
//        mContext.startActivity(getMovieIntent(mContext, mGenreObservableField.get()));
//    }

    public static Intent getMovieIntent(Context context, Genre genre) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putExtra(Constants.EXTRA_GENRE, (Parcelable) genre);
        return intent;
    }
}
