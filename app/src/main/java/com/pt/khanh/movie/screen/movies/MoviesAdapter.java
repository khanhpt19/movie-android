package com.pt.khanh.movie.screen.movies;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.databinding.ItemMovieBinding;
import com.pt.khanh.movie.screen.home.ItemMovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ItemViewHolder> {
    private List<Movie> mMovies;

    public MoviesAdapter() {
        mMovies = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_movie, parent, false);
        return new ItemViewHolder(parent.getContext(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bindData(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.size() : 0;
    }

    public void setMovies(List<Movie> movie) {
        if (movie != null)
            mMovies.addAll(movie);
        notifyItemRangeChanged(mMovies.size(), movie.size());
    }

    public void addMovies(List<Movie> movie) {
        mMovies.clear();
        if (movie != null)
            mMovies.addAll(movie);
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemMovieBinding mBinding;
        private ItemMovieViewModel mItemMoviesViewModel;

        public ItemViewHolder(Context activity, ItemMovieBinding binding) {
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
