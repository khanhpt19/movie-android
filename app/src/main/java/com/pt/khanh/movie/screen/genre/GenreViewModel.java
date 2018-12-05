package com.pt.khanh.movie.screen.genre;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import com.pt.khanh.movie.data.model.Genre;
import com.pt.khanh.movie.data.model.GenresResult;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.screen.movies.MoviesActivity;
import com.pt.khanh.movie.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GenreViewModel extends BaseObservable implements GenreAdapter.onItemClickListener {
    public ObservableBoolean mLoadGenre = new ObservableBoolean();
    private static final int SPAN_COUT = 2;
    private static final int SPACING = 20;
    private GenreAdapter mGenreAdapter;
    public ObservableField<GridSpacingItemDecoration> observableDecoration = new ObservableField<>();
    private MovieRepository mMoviesRepository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private static List<Genre> mGenres;
    private Context mContext;

    public GenreViewModel(Context context, MovieRepository moviesRepository) {
        mLoadGenre.set(false);
        mContext = context;
        mMoviesRepository = moviesRepository;
        observableDecoration.set(new GridSpacingItemDecoration(SPAN_COUT, SPACING, true));
        mGenreAdapter = new GenreAdapter(context);
        mGenreAdapter.setListener(this);
        mGenres = new ArrayList<>();
        loadGenres();
    }

    private void loadGenres() {
        Disposable disposable = mMoviesRepository.getGenres()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(genresResult -> {
                    mGenreAdapter.setGenres(getGenres(genresResult));
                }, throwable -> {
                });
        mCompositeDisposable.add(disposable);
    }

    public GenreAdapter getGenreAdapter() {
        return mGenreAdapter;
    }

    private List<Genre> getGenres(GenresResult resutls) {
        List<Genre> genres = resutls.getGenres();
        for (int i = 0; i < genres.size(); i++) {
            genres.get(i).setImageResource(Genre.Image.RESOURCE[i]);
        }
        return genres;
    }

    @Override
    public void onItemChoose(Genre genre) {
            mLoadGenre.set(true);
        mGenres.add(genre);
        Log.d("AMEN", "onItemClick: " + genIds());
    }

    @Override
    public void onItemNotChoose(Genre genre) {
        mGenres.remove(genre);
        if (mGenres.size() == 0)
            mLoadGenre.set(false);
        Log.d("AMEN", "onItemClick: " + genIds());
    }

    public static Intent getMovieIntent(Context context, List<Genre> genres) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putParcelableArrayListExtra(Constants.EXTRA_GENRE, (ArrayList<Genre>) genres);
        return intent;
    }

    public void onGetMovieByGenresClick(View view){
           mContext.startActivity(getMovieIntent(mContext, mGenres));
    }

    public static long[] genIds() {
        long genreIds[] = new long[mGenres.size()];
        for (int i = 0; i < mGenres.size(); i++) {
            genreIds[i] = mGenres.get(i).getId();
            Log.d("MOVIEDB", "genIds: " + mGenres.get(i).getName());
        }
        return genreIds;
    }
}
