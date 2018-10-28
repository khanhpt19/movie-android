package com.pt.khanh.movie.screen.search;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.data.source.remote.MovieRemoteDataSource;
import com.pt.khanh.movie.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieRepository movieRepository = MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance());
        SearchViewModel searchViewModel = new SearchViewModel(this, movieRepository);
        ActivitySearchBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_search);
        binding.setViewModel(searchViewModel);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
