package com.pt.khanh.movie.screen.detail.castDetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Cast;
import com.pt.khanh.movie.data.model.MovieResult;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.screen.detail.MovieByAdapter;
import com.pt.khanh.movie.utils.Constants;
import com.pt.khanh.movie.utils.StringUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CastDetailViewModel extends AndroidViewModel {
    public ObservableBoolean mIsLoading = new ObservableBoolean();
    public ObservableBoolean mHasBiography = new ObservableBoolean();
    public ObservableBoolean mHasMovies = new ObservableBoolean();
    public ObservableField<Cast> mCastObservableField = new ObservableField<>();
    private MovieRepository mRepository;
    private MovieByAdapter mAdapter;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public CastDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void setupViewModel(Cast cast, MovieRepository repository) {
        mRepository = repository;
        mCastObservableField.set(cast);
        mAdapter = new MovieByAdapter();
        getCastDetail(cast.getId());
        getMoviesByCast(cast.getId());
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

    public void getMoviesByCast(long id) {
        Disposable disposable = mRepository.getMoviesByCast(id, Constants.PAGE_DEFAULT)
                .doOnSubscribe(disposable1 -> mIsLoading.set(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResult>() {
                    @Override
                    public void accept(MovieResult movieResult) throws Exception {
                        mIsLoading.set(false);
                        mAdapter.setMovies(movieResult.getMovies());
                        if (movieResult.getMovies() != null)
                            mHasMovies.set(true);
                    }
                }, error -> handleError(error));
        mCompositeDisposable.add(disposable);
    }

    private void handleError(Throwable error) {
        mIsLoading.set(false);
        if (error.getMessage().equals(getApplication().getString(R.string.error_no_internet)))
            Toast.makeText(getApplication(),
                    getApplication().getString(R.string.toast_no_internet), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplication(),
                    getApplication().getString(R.string.toast_error_api), Toast.LENGTH_SHORT).show();
    }

    public void getCastDetail(long id) {
        Disposable disposable = mRepository.getCastDetail(id)
                .doOnSubscribe(disposable1 -> mIsLoading.set(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Cast>() {
                    @Override
                    public void accept(Cast cast) throws Exception {
                        mIsLoading.set(false);
                        mCastObservableField.set(cast);
                        if (!cast.getBiography().isEmpty())
                            mHasBiography.set(true);
                    }
                }, error -> handleError(error));
        mCompositeDisposable.add(disposable);
    }

    public ObservableField<Cast> getCastObservableField() {
        return mCastObservableField;
    }

    public MovieByAdapter getAdapter() {
        return mAdapter;
    }
}
