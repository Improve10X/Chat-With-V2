package com.improve10x.chatwithv2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.improve10x.chatwithv2.databinding.FragmentTemplatesBinding;

import java.util.ArrayList;
import java.util.List;


public class TemplatesFragment extends Fragment {

    private FragmentTemplatesBinding templatesBinding;
    private ArrayList<Template> templates = new ArrayList<>();
    private TemplatesAdapter templatesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        templatesBinding = FragmentTemplatesBinding.inflate(getLayoutInflater());
        setTemplatesRv();
        handleAddFab();
        return templatesBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    private void handleAddFab() {
        templatesBinding.addFab.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AddTemplateActivity.class);
            startActivity(intent);
        });
    }

    private void setTemplatesRv() {
        templatesBinding.templatesRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        templatesAdapter = new TemplatesAdapter();
        templatesAdapter.setTemplates(templates);
        templatesAdapter.setOnItemActionListener(new OnItemActionListener() {
            @Override
            public void onDelete(String id) {
                deleteTemplate(id);
            }

            @Override
            public void onEdit(Template template) {

            }
        });
        templatesBinding.templatesRv.setAdapter(templatesAdapter);
    }

    private void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("templates")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Template> messages = task.getResult().toObjects(Template.class);
                            if (messages != null) {
                                templatesAdapter.setTemplates(messages);
                            }

                        } else {
                            Toast.makeText(getContext(), "failed to fetch data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void deleteTemplate(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("templates")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Successfully deleted the ", Toast.LENGTH_SHORT).show();
                        fetchData();
                    }
                });

    }
}