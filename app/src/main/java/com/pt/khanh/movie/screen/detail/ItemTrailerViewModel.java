package com.pt.khanh.movie.screen.detail;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.pt.khanh.movie.data.model.TrailerMovie;

public class ItemTrailerViewModel extends BaseObservable {
    public ObservableField<TrailerMovie> mTrailerMovieObservableField = new ObservableField<>();

    public ItemTrailerViewModel() {
    }

    public void setTrailerMovie(TrailerMovie trailerMovieObservableField) {
        mTrailerMovieObservableField.set(trailerMovieObservableField);
    }

}
