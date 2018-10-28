package com.pt.khanh.movie.screen.detail.castDetail;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Cast;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.data.source.local.MovieLocalDatasource;
import com.pt.khanh.movie.data.source.remote.MovieRemoteDataSource;
import com.pt.khanh.movie.databinding.ActivityCastDetailBinding;
import com.pt.khanh.movie.utils.Constants;

public class CastDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCastDetailBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_cast_detail);
        Cast cast = getIntent().getParcelableExtra(Constants.EXTRA_CAST);
        MovieRepository repository = MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance(), MovieLocalDatasource.getInstance(this));
        CastDetailViewModel detailViewModel = ViewModelProviders.of(this)
                .get(CastDetailViewModel.class);
        detailViewModel.setupViewModel(cast, repository);
        binding.setViewModel(detailViewModel);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
