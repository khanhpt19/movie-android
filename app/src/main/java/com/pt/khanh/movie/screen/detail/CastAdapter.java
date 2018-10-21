package com.pt.khanh.movie.screen.detail;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Cast;
import com.pt.khanh.movie.databinding.ItemCastBinding;

import java.util.ArrayList;
import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ItemViewHolder> {
    private List<Cast> mCasts;

    public CastAdapter() {
        mCasts = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCastBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                parent.getContext()), R.layout.item_cast, parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bindData(mCasts.get(position));
    }

    @Override
    public int getItemCount() {
        return mCasts != null ? mCasts.size() : 0;
    }

    public void setCasts(List<Cast> casts) {
        mCasts.clear();
        mCasts.addAll(casts);
        notifyDataSetChanged();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemCastBinding mCastBinding;
        private ItemCastViewModel mCastViewModel;

        public ItemViewHolder(ItemCastBinding binding) {
            super(binding.getRoot());
            mCastBinding = binding;
            mCastViewModel = new ItemCastViewModel(itemView.getContext());
            mCastBinding.setViewModel(mCastViewModel);
        }

        public void bindData(Cast cast) {
            mCastViewModel.setCast(cast);
            mCastBinding.executePendingBindings();
        }
    }
}
