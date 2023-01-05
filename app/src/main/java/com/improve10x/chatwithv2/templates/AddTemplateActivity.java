package com.improve10x.chatwithv2.templates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.chatwithv2.databinding.ActivityAddTemplateBinding;

public class AddTemplateActivity extends AppCompatActivity {

    private ActivityAddTemplateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTemplateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Add Template");
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
                        Toast.makeText(AddTemplateActivity.this, "Successfully added the Template", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddTemplateActivity.this, "Failed to add Template", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}