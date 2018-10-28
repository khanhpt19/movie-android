package com.pt.khanh.movie.screen.movies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.pt.khanh.movie.R;
import com.pt.khanh.movie.data.model.Category;
import com.pt.khanh.movie.data.model.Company;
import com.pt.khanh.movie.data.model.Genre;
import com.pt.khanh.movie.data.repository.MovieRepository;
import com.pt.khanh.movie.data.source.local.MovieLocalDatasource;
import com.pt.khanh.movie.data.source.remote.MovieRemoteDataSource;
import com.pt.khanh.movie.databinding.ActivityMoviesBinding;
import com.pt.khanh.movie.utils.Constants;

public class MoviesActivity extends AppCompatActivity {
    private MoviesViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieRepository repository = MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance(),
                MovieLocalDatasource.getInstance(this));
        mViewModel = new MoviesViewModel(repository);
        ActivityMoviesBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_movies);
        binding.setViewModel(mViewModel);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        handleGetMovies();
    }

    private void handleGetMovies() {
        if (getIntent().hasExtra(Constants.EXTRA_CATEGORY)) {
            Category category = (Category) getIntent()
                    .getParcelableExtra(Constants.EXTRA_CATEGORY);
            getSupportActionBar().setTitle(category.getName());
            mViewModel.getMoviesByCategory(category.getKey());
        } else if (getIntent().hasExtra(Constants.EXTRA_GENRE)) {
            Genre genre = (Genre) getIntent().
                    getParcelableExtra(Constants.EXTRA_GENRE);
            getSupportActionBar().setTitle(genre.getName());
            mViewModel.getMoviesByGenre(genre.getId());
        } else if (getIntent().hasExtra(Constants.EXTRA_COMPANY)) {
            Company company = (Company) getIntent()
                    .getParcelableExtra(Constants.EXTRA_COMPANY);
            getSupportActionBar().setTitle(company.getName());
            mViewModel.getMoviesByCompany(company.getId());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
