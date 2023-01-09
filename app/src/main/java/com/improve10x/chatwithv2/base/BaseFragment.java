package com.improve10x.chatwithv2.base;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    protected void showToast(String fragment) {
        Toast.makeText(getContext(), fragment, Toast.LENGTH_SHORT).show();
    }
}
