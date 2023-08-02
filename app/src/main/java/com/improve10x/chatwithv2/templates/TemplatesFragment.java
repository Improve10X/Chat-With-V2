package com.improve10x.chatwithv2.templates;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
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
import com.improve10x.chatwithv2.base.BaseFragment;
import com.improve10x.chatwithv2.HomeFragment;
import com.improve10x.chatwithv2.databinding.FragmentTemplatesBinding;

import java.util.ArrayList;
import java.util.List;


public class TemplatesFragment extends BaseFragment {

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

    private void hideProgressBar() {
        templatesBinding.templateProgressbar.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        templatesBinding.templateProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
        templatesAdapter.notifyDataSetChanged();
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
                editTemplate(template);
            }

            @Override
            public void onClicked(Template template) {
                onClick(template);
            }
        });
        templatesBinding.templatesRv.setAdapter(templatesAdapter);
    }

    private void editTemplate(Template template) {
        Intent intent = new Intent(getActivity(), EditTemplateActivity.class);
        intent.putExtra("templates", template);
        startActivity(intent);
    }

    private void fetchData() {
        showProgressBar();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("/users/" + user.getUid() + "/templates")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        hideProgressBar();
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        if (task.isSuccessful()) {
                            List<Template> templates = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Template template = document.toObject(Template.class);
                                template.id = document.getId();
                                templates.add(template);
                            }
                            templatesAdapter.setTemplates(templates);
                        } else {
                            showToast("failed to fetch data");
                        }
                    }
                });
    }

    private void deleteTemplate(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("/users/" + user.getUid() + "/templates")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        showToast("Successfully deleted the template");
                        fetchData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       showToast("Failed to delete the Template");
                    }
                });
    }

    private void onClick(Template template) {
     HomeFragment homeFragment = new HomeFragment();
     Bundle bundle = new Bundle();
     bundle.putSerializable("templates", template);
     homeFragment.setArguments(bundle);
    }
}