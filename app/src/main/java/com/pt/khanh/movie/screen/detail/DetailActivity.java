package com.pt.khanh.movie.screen.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.data.source.local.MovieLocalDatasource;
import com.pt.khanh.movie.data.source.remote.MovieRemoteDataSource;
import com.pt.khanh.movie.databinding.ActivityDetailBinding;
import com.pt.khanh.movie.utils.Constants;

public class DetailActivity extends YouTubeBaseActivity {
    private DetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Movie movie = getIntent().getParcelableExtra(Constants.EXTRA_MOVIE);
        MovieRepository repository = MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance(),
                MovieLocalDatasource.getInstance(this));
        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mViewModel = new DetailViewModel();
        mViewModel.start(this, repository, movie);
        binding.setViewModel(mViewModel);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewModel.onStop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewModel.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.onStop();
    }
}
