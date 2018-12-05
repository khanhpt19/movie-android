package com.pt.khanh.movie.screen.genre;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Genre;
import com.pt.khanh.movie.databinding.ItemGenreBinding;

import java.util.ArrayList;
import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ItemViewHolder> {
    private List<Genre> mGenres;
    private Context mContext;
    private onItemClickListener mItemClickListenere;

    public GenreAdapter(Context context) {
        mContext = context;
        mGenres = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGenreBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_genre, parent, false);
        return new ItemViewHolder(binding, mItemClickListenere);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bindData(mGenres.get(position));
    }

    @Override
    public int getItemCount() {
        return mGenres != null ? mGenres.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setGenres(List<Genre> genres) {
        mGenres.addAll(genres);
        notifyDataSetChanged();
    }

    public void setListener(onItemClickListener itemClickListenere) {
        mItemClickListenere = itemClickListenere;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemGenreBinding mBinding;
        private ItemGenreViewModel mItemGenreViewModel;

        public ItemViewHolder(ItemGenreBinding binding, onItemClickListener itemClickListener) {
            super(binding.getRoot());
            mBinding = binding;

            mItemGenreViewModel = new ItemGenreViewModel(mContext, itemClickListener);
            mBinding.setViewModel(mItemGenreViewModel);
        }

        void bindData(Genre genre) {
            mItemGenreViewModel.setGenres(genre);
            mBinding.executePendingBindings();
        }
    }

    interface onItemClickListener {
        void onItemChoose(Genre genre);
        void onItemNotChoose(Genre genre);
    }
}
