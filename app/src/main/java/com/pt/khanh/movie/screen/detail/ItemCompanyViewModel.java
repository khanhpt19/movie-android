package com.pt.khanh.movie.screen.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Company;
import com.pt.khanh.movie.screen.movies.MoviesActivity;
import com.pt.khanh.movie.utils.Constants;
import com.pt.khanh.movie.utils.StringUtils;

public class ItemCompanyViewModel extends BaseObservable {
    public ObservableField<Company> mCompanyObservableField = new ObservableField<>();
    public CompanyAdapter.ItemClickListener mItemClickListener;

    public ItemCompanyViewModel(CompanyAdapter.ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @BindingAdapter("imageUrlCompany")
    public static void imageUrlCompany(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions().error(R.drawable.default_company);
        Glide.with(imageView.getContext())
                .load(StringUtils.getUrlImage(url))
                .apply(requestOptions).into(imageView);
    }

    public void setCompany(Company company) {
        mCompanyObservableField.set(company);
    }

    public void onItemClick(View view) {
        if (mItemClickListener == null || mCompanyObservableField.get() == null) {
            return;
        }
        view.getContext().startActivity(getMoviesIntent(view.getContext(),
                mCompanyObservableField.get()));
        mItemClickListener.onItemClick(mCompanyObservableField.get());
    }

    public static Intent getMoviesIntent(Context context, Company company) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putExtra(Constants.EXTRA_COMPANY, (Parcelable) company);
        return intent;
    }
}
