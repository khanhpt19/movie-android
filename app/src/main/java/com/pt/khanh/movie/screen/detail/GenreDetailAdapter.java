package com.pt.khanh.movie.screen.detail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Genre;
import com.pt.khanh.movie.databinding.ItemGenreDetailBinding;
import com.pt.khanh.movie.screen.genre.ItemGenreViewModel;

import java.util.ArrayList;
import java.util.List;

public class GenreDetailAdapter extends RecyclerView.Adapter<GenreDetailAdapter.ItemViewHolder> {
    private List<Genre> mGenres;
    private Context mContext;

    public GenreDetailAdapter(Context context) {
        mContext = context;
        mGenres = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGenreDetailBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_genre_detail, parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bindData(mGenres.get(position));
    }

    @Override
    public int getItemCount() {
        return mGenres != null ? mGenres.size() : 0;
    }

    public void setGenres(List<Genre> genres) {
        mGenres.addAll(genres);
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemGenreDetailBinding mBinding;
        private ItemGenreViewModel mItemGenreViewModel;

        public ItemViewHolder(ItemGenreDetailBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mItemGenreViewModel = new ItemGenreViewModel(mContext);
            mBinding.setViewModel(mItemGenreViewModel);
        }

        void bindData(Genre genre) {
            mItemGenreViewModel.setGenres(genre);
            mBinding.executePendingBindings();
        }
    }
}

