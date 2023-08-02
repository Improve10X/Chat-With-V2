package com.improve10x.chatwithv2.historyItem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.improve10x.chatwithv2.base.BaseFragment;
import com.improve10x.chatwithv2.databinding.FragmentHistoryBinding;
import com.improve10x.chatwithv2.templates.Template;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends BaseFragment {

    private FragmentHistoryBinding historyBinding;
    private ArrayList<HistoryItem> historyItem;
    private HistoryAdapter historyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        historyBinding = FragmentHistoryBinding.inflate(getLayoutInflater());
        setupData();
        setHistoryRv();
        return historyBinding.getRoot();
    }

    private void hideProgressBar() {
        historyBinding.groupProgressbar.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        historyBinding.groupProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    private void deleteHistory(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("/users/" + user.getUid() + "/history")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        showToast("Successfully deleted");
                        fetchData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast("Failed to delete History");
                    }
                });
    }

    private void fetchData() {
        showProgressBar();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("/users/" + user.getUid() + "/history")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        hideProgressBar();
                        if (task.isSuccessful()) {
                            List<HistoryItem> historyList = task.getResult().toObjects(HistoryItem.class);
                            historyAdapter.setHistoryItem(historyList);
                        } else {
                            showToast("failed to fetch data");
                        }
                    }
                });
    }

    private void setHistoryRv() {
        historyBinding.historyRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyAdapter = new HistoryAdapter();
        historyAdapter.setHistoryItem(historyItem);
        historyAdapter.setOnItemActionListener(new OnItemActionListener() {
            @Override
            public void onItemDelete(String id) {
                deleteHistory(id);
            }

            @Override
            public void saveTemplates(String message) {
                addToTemplates(message);
            }
        });
        historyBinding.historyRv.setAdapter(historyAdapter);
    }

    private void addToTemplates(String messageTxt) {
        Template template = new Template(messageTxt);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        template.id = db.collection("templates").document().getId();
        db.collection("/users/" + user.getUid() + "/templates")
                .document(template.id)
                .set(template)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        showToast("Successfully added the Template");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast("Failed to add Template");
                    }
                });
    }

    private void setupData() {
        historyItem = new ArrayList<>();
    }
}