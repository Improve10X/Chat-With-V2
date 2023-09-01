package com.improve10x.chatwithv2.templates;

import androidx.annotation.NonNull;

import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.chatwithv2.base.BaseActivity;
import com.improve10x.chatwithv2.databinding.ActivityAddTemplateBinding;

import java.util.Objects;

public class AddTemplateActivity extends BaseActivity {

    private ActivityAddTemplateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTemplateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Template");
        handleAddBtn();
    }

    private void handleAddBtn() {
        binding.addBtn.setOnClickListener(view -> {
            String message = binding.messageTxt.getText().toString();
            String title = binding.titleTxt.getText().toString();
            addTemplate(message, title);
            finish();
        });
    }

    private void addTemplate(String messageTxt, String titleTxt) {
        Template template = new Template(messageTxt, titleTxt);
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
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast("Failed to add Template");
                    }
                });
    }
}