package com.pt.khanh.movie.screen.detail;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Review;
import com.pt.khanh.movie.databinding.ItemReviewBinding;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ItemViewHolder> {
    private List<Review> mReviews;

    public ReviewAdapter() {
        mReviews = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemReviewBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_review, viewGroup, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.bindData(mReviews.get(i));
    }

    @Override
    public int getItemCount() {
        return mReviews != null ? mReviews.size() : 0;
    }

    public void setReviews(List<Review> reviews) {
        mReviews.addAll(reviews);
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemReviewBinding mBinding;
        private ItemReviewViewModel mItemReviewViewModel;

        public ItemViewHolder(ItemReviewBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mItemReviewViewModel = new ItemReviewViewModel();
            mBinding.setViewModel(mItemReviewViewModel);
        }

        public void bindData(Review review) {
            mItemReviewViewModel.setReview(review);
            mBinding.executePendingBindings();
        }
    }
}
