package com.pt.khanh.movie.screen.search;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.model.MovieResult;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.screen.movies.MoviesAdapter;
import com.pt.khanh.movie.utils.Constants;
import com.pt.khanh.movie.utils.NetworkUtils;
import com.pt.khanh.movie.utils.StringUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends BaseObservable implements TextView.OnEditorActionListener,
        MoviesAdapter.ItemBookmarkListener {
    public static final int TIME_DELAY = 300;//miliseconds
    public ObservableBoolean mIsLoading = new ObservableBoolean();
    public ObservableField<String> mTextSearch = new ObservableField<>();
    private MoviesAdapter mAdapter;
    private AppCompatActivity mActivity;
    private MovieRepository mRepository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public SearchViewModel(AppCompatActivity activity, MovieRepository repository) {
        mAdapter = new MoviesAdapter();
        mAdapter.setListener(this);
        mActivity = activity;
        mRepository = repository;
    }

    public void setUpSearchObservable(EditText searchView) {
        RxSearchObservable.fromEdittext(searchView)
                .debounce(TIME_DELAY, TimeUnit.MILLISECONDS)
                .filter(text -> {
                    if (text.isEmpty()) {
                        return false;
                    } else {
                        return true;
                    }
                })
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    searchMovie(result);
                });
    }

    public void searchMovie(String name) {
        mIsLoading.set(true);
        Disposable disposable = mRepository.searchMovie(name, Constants.PAGE_DEFAULT)
                .debounce(TIME_DELAY, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> handleResponse(response),
                        error -> handleError(error));
        mCompositeDisposable.add(disposable);
    }

    private void handleError(Throwable error) {
        mIsLoading.set(false);
    }

    private void handleResponse(MovieResult response) {
        mIsLoading.set(false);
        if (response.getMovies().size() == 0) {
            Toast.makeText(mActivity, R.string.toast_movie_not_exist,
                    Toast.LENGTH_SHORT).show();
        } else {
            mAdapter.setMovies(response.getMovies());
        }
    }

    public MoviesAdapter getAdapterMovie() {
        return mAdapter;
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(!NetworkUtils.isConnectedToNetwork(mActivity)){
            Toast.makeText(mActivity, mActivity.getString(R.string.toast_no_internet),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            mTextSearch.set(v.getText().toString());
            hideKeyboard(mActivity);
            if (!StringUtils.isEmpty(mTextSearch.get())) {
                searchMovie(mTextSearch.get());
                return true;
            }
        }
        return false;
    }

    public void onBack(View view) {
        mActivity.onBackPressed();
    }

    public void onClearQuery(View view) {
        mTextSearch.set("");
    }

    @Override
    public void onBookmarkClick(Movie movie) {
        if (mRepository.isFavourite(movie)) {
            mRepository.deleteMovie(movie);
        } else
            mRepository.insertMovie(movie);
    }
}
