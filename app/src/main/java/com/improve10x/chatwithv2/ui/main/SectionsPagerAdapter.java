package com.improve10x.chatwithv2.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.improve10x.chatwithv2.historyItem.HistoryFragment;
import com.improve10x.chatwithv2.HomeFragment;
import com.improve10x.chatwithv2.templates.TemplatesFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStateAdapter {


    private static final String[] TAB_TITLES = new String[]{"Home", "Templates", "History"};

    public SectionsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new HomeFragment();
        }else if (position == 1) {
            return  new TemplatesFragment();
        }else {
            return new HistoryFragment();
        }
    }

    @Override
    public int getItemCount() {
        return TAB_TITLES.length;
    }
}