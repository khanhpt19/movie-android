package com.pt.khanh.movie.screen.genre;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pt.khanh.movie.R;

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
        return inflater.inflate(R.layout.fragment_genre, container, false);
    }
}
