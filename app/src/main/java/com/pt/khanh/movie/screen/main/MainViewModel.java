package com.pt.khanh.movie.screen.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.screen.favorite.FavoriteFragment;
import com.pt.khanh.movie.screen.genre.GenreFragment;
import com.pt.khanh.movie.screen.home.HomeFragment;

import java.util.List;

public class MainViewModel extends AndroidViewModel implements
        BottomNavigationView.OnNavigationItemSelectedListener {
    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;
    private Fragment mCurrentFragment;
    private FavoriteFragment mFavoriteFragment;
    private GenreFragment mGenreFragment;
    private MovieRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void setup(MainActivity activity, MovieRepository repository) {
        mHomeFragment = HomeFragment.getInstance();
        mGenreFragment = GenreFragment.getInstance();
        mRepository = repository;
        List<Movie> movies = mRepository.getMoviesLocal();
        mFavoriteFragment = FavoriteFragment.getInstance(movies);
        mFragmentManager = activity.getSupportFragmentManager();
        mCurrentFragment = mHomeFragment;
        mFragmentManager.beginTransaction()
                .add(R.id.frame_container, mGenreFragment).hide(mGenreFragment)
                .add(R.id.frame_container, mFavoriteFragment).hide(mFavoriteFragment)
                .add(R.id.frame_container, mHomeFragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                showFragment(mHomeFragment, mCurrentFragment);
                mCurrentFragment = mHomeFragment;
                return true;
            case R.id.navigation_favorite:
                List<Movie> movies = mRepository.getMoviesLocal();
                mFragmentManager.beginTransaction().remove(mFavoriteFragment).commit();
                mFavoriteFragment = FavoriteFragment.getInstance(movies);
                mFragmentManager.beginTransaction().add(R.id.frame_container, mFavoriteFragment)
                        .hide(mGenreFragment).hide(mHomeFragment).commit();
                mCurrentFragment = mFavoriteFragment;
                return true;
            case R.id.navigation_genre:
                showFragment(mGenreFragment, mCurrentFragment);
                mCurrentFragment = mGenreFragment;
                return true;
        }
        return false;
    }

    private void showFragment(Fragment fShow, Fragment fHide) {
        mFragmentManager.beginTransaction().show(fShow).hide(fHide).commit();
    }
}
