package com.pt.khanh.movie.screen.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Cast;
import com.pt.khanh.movie.data.model.Genre;
import com.pt.khanh.movie.screen.movies.MoviesActivity;
import com.pt.khanh.movie.utils.Constants;
import com.pt.khanh.movie.utils.StringUtils;

public class ItemCastViewModel extends BaseObservable {
    public ObservableField<Cast> mCastObservableField = new ObservableField<>();

    public ItemCastViewModel() {
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
                .into(imageView);
    }

    public void setCast(Cast cast) {
        mCastObservableField.set(cast);
    }

    public void onClickItemCast() {
        Log.d("AMEN", "onClickItemGenres: "  + mCastObservableField.get().getName());
    }

    public static Intent getMovieIntent(Context context, Genre genre) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putExtra(Constants.EXTRA_GENRE, (Parcelable) genre);
        return intent;
    }
}
