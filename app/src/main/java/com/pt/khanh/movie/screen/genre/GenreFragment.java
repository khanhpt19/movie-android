package com.pt.khanh.movie.screen.genre;

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
import com.pt.khanh.movie.databinding.FragmentGenreBinding;

public class GenreFragment extends Fragment {
    private static GenreFragment sInstance;

    public static GenreFragment getInstance() {
        if (sInstance == null) {
            sInstance = new GenreFragment();
        }
        return sInstance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentGenreBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_genre, container, false);
        MovieRepository repository =
                MovieRepository.getInstance(MovieRemoteDataSource.getInstance());
        GenreViewModel viewModel = new GenreViewModel(repository);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }
}
