package com.improve10x.chatwithv2.base;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected void showToast(String template) {
        Toast.makeText(this, template, Toast.LENGTH_SHORT).show();
    }

}
