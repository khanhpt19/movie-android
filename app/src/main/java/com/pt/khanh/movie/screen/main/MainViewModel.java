package com.pt.khanh.movie.screen.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.screen.genre.GenreFragment;
import com.pt.khanh.movie.screen.home.HomeFragment;
import com.pt.khanh.movie.screen.search.SearchFragment;

public class MainViewModel extends AndroidViewModel implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainViewModel.class.getName();
    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;
    private Fragment mCurrentFragment;
    private SearchFragment mSearchFragment;
    private GenreFragment mGenreFragment;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void setup(MainActivity activity) {
        mHomeFragment = HomeFragment.getInstance();
        mGenreFragment = GenreFragment.getInstance();
        mSearchFragment = SearchFragment.getInstance();

        mFragmentManager = activity.getSupportFragmentManager();
        mCurrentFragment = mHomeFragment;
        mFragmentManager.beginTransaction()
                .add(R.id.frame_container, mGenreFragment).hide(mGenreFragment)
                .add(R.id.frame_container, mSearchFragment).hide(mSearchFragment)
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
            case R.id.navigation_search:
                showFragment(mSearchFragment, mCurrentFragment);
                mCurrentFragment = mSearchFragment;
                return true;
            case R.id.navigation_genre:
                showFragment(mGenreFragment, mCurrentFragment);
                mCurrentFragment = mGenreFragment;
                return true;
        }
        return false;
    }

    private void showFragment(Fragment fShow, Fragment fHide){
        mFragmentManager.beginTransaction().show(fShow).hide(fHide).commit();
    }
}
