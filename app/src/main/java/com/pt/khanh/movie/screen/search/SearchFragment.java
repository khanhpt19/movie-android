package com.pt.khanh.movie.screen.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pt.khanh.movie.R;

public class SearchFragment extends Fragment {
    private static SearchFragment sInstance;

    public static SearchFragment getInstance() {
        if (sInstance == null) {
            sInstance = new SearchFragment();
        }
        return sInstance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}
