package com.pt.khanh.movie.screen.home;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.databinding.ItemTrendingBinding;
import com.pt.khanh.movie.screen.movies.MoviesAdapter;

import java.util.List;

public class TrendingMovieAdapter extends PagerAdapter {
    private List<Movie> mMovies;
    private ItemMovieViewModel mItemMovieViewModel;
    private MoviesAdapter.ItemBookmarkListener mListener;

    public TrendingMovieAdapter(List<Movie> movies) {
        mMovies = movies;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mItemMovieViewModel = new ItemMovieViewModel(container.getContext(), mListener);
        ItemTrendingBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(container.getContext()),
                R.layout.item_trending, container, true);
        binding.setViewModel(mItemMovieViewModel);
        mItemMovieViewModel.setMovie(mMovies.get(position));
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public int getCount() {
        return mMovies != null ? HomeViewModel.MAX_ITEM_TRENDING_MOVIE : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
