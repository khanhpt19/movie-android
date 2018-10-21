package com.pt.khanh.movie.screen.detail;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.pt.khanh.movie.data.model.Review;

public class ItemReviewViewModel extends BaseObservable {
    public ObservableField<Review> mReviewObservableField = new ObservableField<>();

    public ItemReviewViewModel() {

    }

    public void setReview(Review review) {
        mReviewObservableField.set(review);
    }
}
