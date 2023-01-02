package com.improve10x.chatwithv2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.improve10x.chatwithv2.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding fragmentHomeBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(getLayoutInflater());
        handleWhatsappBtn();
        handleWhatsappBusinessBtn();
        return fragmentHomeBinding.getRoot();
    }

    private void handleWhatsappBtn() {
        fragmentHomeBinding.whatsappBtn.setOnClickListener(view -> {
            String name = fragmentHomeBinding.nameTxt.getText().toString();
            String number = fragmentHomeBinding.numberTxt.getText().toString();
            String message = fragmentHomeBinding.messageTxt.getText().toString();
            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + message;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            i.setPackage("com.whatsapp");
            startActivity(i);
        });
    }

    private void handleWhatsappBusinessBtn() {
        fragmentHomeBinding.wabusinessBtn.setOnClickListener(view -> {
            String name = fragmentHomeBinding.nameTxt.getText().toString();
            String number = fragmentHomeBinding.numberTxt.getText().toString();
            String message = fragmentHomeBinding.messageTxt.getText().toString();
            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + message;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            i.setPackage("com.whatsapp");
            startActivity(i);
        });
    }
}