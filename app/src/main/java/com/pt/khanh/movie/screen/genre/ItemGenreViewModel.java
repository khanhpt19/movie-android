package com.pt.khanh.movie.screen.genre;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.pt.khanh.movie.data.model.Genre;

public class ItemGenreViewModel extends BaseObservable {
    public ObservableField<Genre> mGenreObservableField = new ObservableField<>();

    public ItemGenreViewModel() {
    }

    public void setGenres(Genre genres) {
        mGenreObservableField.set(genres);
    }

    public void onClickItemGenres() {
        Genre genres = mGenreObservableField.get();
    }
}
