package com.pt.khanh.movie.screen.favorite;

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
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.data.source.local.MovieLocalDatasource;
import com.pt.khanh.movie.data.source.remote.MovieRemoteDataSource;
import com.pt.khanh.movie.databinding.FragmentFavoriteBinding;
import com.pt.khanh.movie.utils.Constants;

import java.io.Serializable;
import java.util.List;

public class FavoriteFragment extends Fragment {
    private static List<Movie> sMovies;

    public static FavoriteFragment getInstance(List<Movie> movies) {
        FavoriteFragment favouriteFragment = new FavoriteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.BUNDLE_MOVIE_FAVOURITE, (Serializable) movies);
        sMovies = movies;
        favouriteFragment.setArguments(bundle);
        return favouriteFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentFavoriteBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_favorite, container, false);
        MovieRepository repository = MovieRepository.getInstance(MovieRemoteDataSource.getInstance(),
                MovieLocalDatasource.getInstance(getContext()));
        FavoriteViewModel viewModel = ViewModelProviders.of(getActivity())
                .get(FavoriteViewModel.class);
        viewModel.setup(repository, sMovies);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }
}
