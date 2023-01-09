package com.improve10x.chatwithv2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.chatwithv2.base.BaseFragment;
import com.improve10x.chatwithv2.databinding.FragmentHomeBinding;
import com.improve10x.chatwithv2.historyItem.HistoryItem;


public class HomeFragment extends BaseFragment {

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
            long time = System.currentTimeMillis();
            saveData(name, number, message,time);
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
            long time = System.currentTimeMillis();
            saveData(name, number, message, time);
        });
    }

    private void saveData(String name, String number, String message, long time) {
        HistoryItem historyItem = new HistoryItem(message, name, number, time);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        historyItem.id = db.collection("/users/" + user.getUid() + "/history").document().getId();
        db.collection("/users/" + user.getUid() + "/history")
                .document(historyItem.id)
                .set(historyItem)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        showToast("Successfully added the data");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast("Failed to add data");
                    }
                });
    }
}