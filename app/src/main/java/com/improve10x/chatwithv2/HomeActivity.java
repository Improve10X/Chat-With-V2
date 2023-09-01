package com.improve10x.chatwithv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.tabs.TabLayoutMediator;
import com.improve10x.chatwithv2.databinding.ActivityHomeScreenBinding;
import com.improve10x.chatwithv2.templates.Template;
import com.improve10x.chatwithv2.ui.main.HomeTabsAdapter;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeScreenBinding binding;
    private HomeTabsAdapter homeTabsAdapter;
    Template selectedTemplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Chat With");
        }
        homeTabsAdapter = new HomeTabsAdapter(this);
        binding.viewPager.setAdapter(homeTabsAdapter);
        new TabLayoutMediator(binding.tabs, binding.viewPager, (tab, position) -> {
            tab.setText(homeTabsAdapter.tabTitles[position]);
        }).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout){
            logout();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(task -> {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                });
    }

    public void openHome(Template template) {
        selectedTemplate = template;
        binding.viewPager.setCurrentItem(0, true);
    }
}