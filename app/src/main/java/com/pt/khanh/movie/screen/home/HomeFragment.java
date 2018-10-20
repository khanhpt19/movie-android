package com.pt.khanh.movie.screen.home;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.data.source.remote.MovieRemoteDataSource;
import com.pt.khanh.movie.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private static HomeFragment sInstance;
    private HomeViewModel mHomeViewModel;

    public static HomeFragment getInstance() {
        if (sInstance == null) {
            sInstance = new HomeFragment();
        }
        return sInstance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentHomeBinding mHomeBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home, container, false);
        View view = mHomeBinding.getRoot();

        MovieRepository repository =
                MovieRepository.getInstance(MovieRemoteDataSource.getInstance());
        mHomeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        mHomeBinding.setViewModel(mHomeViewModel);
        mHomeViewModel.setupView(repository);
        mHomeViewModel.setViewPager(mHomeBinding.viewPager);
        return view;
    }
}
