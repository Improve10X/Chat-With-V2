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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.improve10x.chatwithv2.databinding.FragmentHistoryBinding;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding historyBinding;
    private ArrayList<HistoryItem> histories;
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
        db.collection("history")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Successfully deleted", Toast.LENGTH_SHORT).show();
                        fetchData();
                    }
                });
    }

    private void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("history")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<HistoryItem> historyList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HistoryItem history = document.toObject(HistoryItem.class);
                                history.id = document.getId();
                                historyList.add(history);
                            }
                            historyAdapter.setHistories(historyList);
                        } else {
                            Toast.makeText(getContext(), "failed to fetch data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setHistoryRv() {
        historyBinding.historyRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyAdapter = new HistoryAdapter();
        historyAdapter.setHistories(histories);
        historyAdapter.setOnItemActionListener(new OnItemActionListener() {
            @Override
            public void onItemDelete(String id) {
                deleteHistory(id);
            }
        });
        historyBinding.historyRv.setAdapter(historyAdapter);
    }

    private void setupData() {
        histories = new ArrayList<>();
    }
}