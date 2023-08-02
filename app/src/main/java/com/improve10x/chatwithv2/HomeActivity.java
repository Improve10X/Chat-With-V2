package com.improve10x.chatwithv2;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayoutMediator;
import com.improve10x.chatwithv2.ui.main.SectionsPagerAdapter;
import com.improve10x.chatwithv2.databinding.ActivityHomeScreenBinding;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeScreenBinding binding;
    private SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupAdapter();
        setupViewPager();
        connectTabsWithViewPager();
    }

    private void setupViewPager() {
        binding.viewPager.setAdapter(sectionsPagerAdapter);
    }

    private void setupAdapter() {
        sectionsPagerAdapter = new SectionsPagerAdapter(this);
    }

    private void connectTabsWithViewPager() {
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tabs, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 1:
                        tab.setText("TEMPLATES");
                        break;
                    case 2:
                        tab.setText("HISTORY");
                        break;
                    case 0:
                    default:
                        tab.setText("HOME");
                }
            }
        });
        tabLayoutMediator.attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}