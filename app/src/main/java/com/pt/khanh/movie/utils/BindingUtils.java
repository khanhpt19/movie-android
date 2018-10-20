package com.pt.khanh.movie.utils;

import android.databinding.BindingAdapter;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.rd.PageIndicatorView;

public final class BindingUtils {
    @BindingAdapter("onNavigationItemSelected")
    public static void setOnNavigationItemSelectedListener(
            BottomNavigationView view,
            BottomNavigationView.OnNavigationItemSelectedListener listener) {
        view.setOnNavigationItemSelectedListener(listener);
    }

    @BindingAdapter("currentItem")
    public static void setCurrentItem(ViewPager viewPager, int position) {
        viewPager.setCurrentItem(position);
    }

    @BindingAdapter("setAdapterViewPager")
    public static void setAdapterViewPager(ViewPager viewPager, PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @BindingAdapter("setViewPagerIndicator")
    public static void setViewPagerIndicator(PageIndicatorView pageIndicatorView,
                                      ViewPager viewPager) {
        pageIndicatorView.setViewPager(viewPager);
    }
}
