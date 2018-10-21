package com.pt.khanh.movie.screen.detail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.databinding.ItemMovieByBinding;
import com.pt.khanh.movie.screen.home.ItemMovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieByAdapter extends RecyclerView.Adapter<MovieByAdapter.ItemViewHolder> {
    private List<Movie> mMovies;

    public MovieByAdapter() {
        mMovies = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieByBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_movie_by, parent, false);
        return new ItemViewHolder(parent.getContext(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position) {
        itemViewHolder.bindData(mMovies.get(position));
    }


    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.size() : 0;
    }

    public void setMovies(List<Movie> movies) {
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemMovieByBinding mBinding;
        private ItemMovieViewModel mItemMoviesViewModel;

        public ItemViewHolder(Context activity, ItemMovieByBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mItemMoviesViewModel = new ItemMovieViewModel(activity);
            mBinding.setViewModel(mItemMoviesViewModel);
        }

        public void bindData(Movie movie) {
            mItemMoviesViewModel.setMovie(movie);
            mBinding.executePendingBindings();
        }
    }
}
