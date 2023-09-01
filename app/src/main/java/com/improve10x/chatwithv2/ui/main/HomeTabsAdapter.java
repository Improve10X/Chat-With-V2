package com.improve10x.chatwithv2.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.improve10x.chatwithv2.HomeFragment;
import com.improve10x.chatwithv2.historyItem.HistoryFragment;
import com.improve10x.chatwithv2.templates.TemplatesFragment;

public class HomeTabsAdapter extends FragmentStateAdapter {

    public String[] tabTitles = new String[]{"Home", "Templates", "History"};

    public HomeTabsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = new HomeFragment();
        }else if (position == 1) {
            fragment =  new TemplatesFragment();
        }else {
            fragment = new HistoryFragment();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return tabTitles.length;
    }
}