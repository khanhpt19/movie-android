package com.pt.khanh.movie.screen.detail;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.TrailerMovie;
import com.pt.khanh.movie.databinding.ItemTrailerBinding;

import java.util.List;

public class TrailerMovieAdapter extends PagerAdapter {
    private List<TrailerMovie> mTrailerMovies;
    private ItemTrailerViewModel mItemTrailerViewModel;

    public TrailerMovieAdapter(List<TrailerMovie> trailerMovies) {
        mTrailerMovies = trailerMovies;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mItemTrailerViewModel = new ItemTrailerViewModel();
        ItemTrailerBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(container.getContext()),
                R.layout.item_trailer, container, false);
        binding.setViewModel(mItemTrailerViewModel);
        mItemTrailerViewModel.setTrailerMovie(mTrailerMovies.get(position));
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public int getCount() {
        return mTrailerMovies != null ? mTrailerMovies.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
