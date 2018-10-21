package com.pt.khanh.movie.screen.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Movie;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.data.source.remote.MovieRemoteDataSource;
import com.pt.khanh.movie.databinding.ActivityDetailBinding;
import com.pt.khanh.movie.utils.Constants;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Movie movie = (Movie) getIntent().getParcelableExtra(Constants.EXTRA_MOVIE);
        MovieRepository repository = MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance());
        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        DetailViewModel viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        viewModel.setViewPager(binding.viewPager);
        viewModel.start(this, repository, movie.getId());
        binding.setViewModel(viewModel);

    }
}
