package com.improve10x.chatwithv2.historyItem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.improve10x.chatwithv2.databinding.FragmentHistoryBinding;
import com.improve10x.chatwithv2.templates.Template;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {

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
                        Toast.makeText(getContext(), "Successfully deleted", Toast.LENGTH_SHORT).show();
                        fetchData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to delete History", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("/users/" + user.getUid() + "/history")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<HistoryItem> historyList = task.getResult().toObjects(HistoryItem.class);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HistoryItem historyItem = document.toObject(HistoryItem.class);
                                historyItem.id = document.getId();
                                historyList.add(historyItem);
                            }
                            historyAdapter.setHistoryItem(historyList);
                        } else {
                            Toast.makeText(getContext(), "failed to fetch data", Toast.LENGTH_SHORT).show();
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
        });
        historyBinding.historyRv.setAdapter(historyAdapter);
    }

    private void setupData() {
        historyItem = new ArrayList<>();
    }
}