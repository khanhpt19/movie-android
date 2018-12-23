package com.pt.khanh.movie.screen.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.os.Parcelable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Cast;
import com.pt.khanh.movie.screen.detail.castDetail.CastDetailActivity;
import com.pt.khanh.movie.utils.Constants;
import com.pt.khanh.movie.utils.StringUtils;

public class ItemCastViewModel extends BaseObservable {
    public ObservableField<Cast> mCastObservableField = new ObservableField<>();
    private Context mContext;

    public ItemCastViewModel(Context context) {
        mContext = context;
    }

    @BindingAdapter("imageUrlCast")
    public static void imageUrlCast(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.loading)
                .error(R.drawable.default_person1)
                .circleCrop();
        Glide.with(imageView.getContext())
                .load(StringUtils.getUrlImage(url))
                .apply(requestOptions)
                .thumbnail(0.1f)
                .into(imageView);
    }

    public void setCast(Cast cast) {
        mCastObservableField.set(cast);
    }

    public void onClickItemCast() {
//        if(!NetworkUtils.isConnectedToNetwork(mContext)){
//            return;
//        }
        mContext.startActivity(getMoviesIntent(mContext, mCastObservableField.get()));
    }

    public static Intent getMoviesIntent(Context context, Cast cast) {
        Intent intent = new Intent(context, CastDetailActivity.class);
        intent.putExtra(Constants.EXTRA_CAST, (Parcelable) cast);
        return intent;
    }
}
