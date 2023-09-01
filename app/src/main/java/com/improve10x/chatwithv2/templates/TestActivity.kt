package com.improve10x.chatwithv2.templates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.improve10x.chatwithv2.R
import com.improve10x.chatwithv2.base.showToast

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        showToast("Hello ")
    }
}