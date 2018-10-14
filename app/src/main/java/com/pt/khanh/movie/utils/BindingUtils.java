package com.pt.khanh.movie.utils;

import android.databinding.BindingAdapter;
import android.support.design.widget.BottomNavigationView;

public final class BindingUtils {
    @BindingAdapter("onNavigationItemSelected")
    public static void setOnNavigationItemSelectedListener(
            BottomNavigationView view,
            BottomNavigationView.OnNavigationItemSelectedListener listener) {
        view.setOnNavigationItemSelectedListener(listener);
    }
}
