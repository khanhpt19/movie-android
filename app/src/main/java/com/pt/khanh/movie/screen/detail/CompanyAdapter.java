package com.pt.khanh.movie.screen.detail;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Company;
import com.pt.khanh.movie.databinding.ItemCompanyBinding;

import java.util.ArrayList;
import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ItemViewHolder> {
    private List<Company> mCompanies;
    private ItemClickListener mItemClickListener;

    public CompanyAdapter() {
        mCompanies = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCompanyBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_company, parent, false);
        return new ItemViewHolder(binding, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bindData(mCompanies.get(position));
    }

    @Override
    public int getItemCount() {
        return mCompanies != null ? mCompanies.size() : 0;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setCompanies(List<Company> companies) {
        mCompanies.clear();
        mCompanies.addAll(companies);
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemCompanyViewModel mCompanyViewModel;
        private ItemCompanyBinding mBinding;

        public ItemViewHolder(ItemCompanyBinding binding, ItemClickListener itemClickListener) {
            super(binding.getRoot());
            mBinding = binding;
            mCompanyViewModel = new ItemCompanyViewModel(itemClickListener);
            mBinding.setViewModel(mCompanyViewModel);
        }

        public void bindData(Company company) {
            mCompanyViewModel.setCompany(company);
            mBinding.executePendingBindings();
        }
    }

    interface ItemClickListener {
        void onItemClick(Company company);
    }
}

