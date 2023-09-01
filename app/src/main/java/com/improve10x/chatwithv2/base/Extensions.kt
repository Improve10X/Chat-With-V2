package com.improve10x.chatwithv2.base

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}