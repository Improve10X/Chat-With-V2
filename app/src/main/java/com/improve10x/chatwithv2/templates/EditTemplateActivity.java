package com.improve10x.chatwithv2.templates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.chatwithv2.databinding.ActivityEditTemplateBinding;

public class EditTemplateActivity extends AppCompatActivity {

    private ActivityEditTemplateBinding binding;
    private Template template;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTemplateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getIntent().hasExtra("templates")) {
            getSupportActionBar().setTitle("Edit Template");
            template = (Template) getIntent().getSerializableExtra("templates");
            showData();
            handleEditBtn();
        }
    }

    private void showData() {
        binding.titleTxt.setText(template.titleText);
        binding.messageTxt.setText(template.messageText);
    }

    private void editTemplate(String id, String messageTxt, String titleTxt) {
        template = new Template(messageTxt, titleTxt);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("/users/" + user.getUid() + "/templates")
                .document(id)
                .set(template)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditTemplateActivity.this, "Successfully edited the Template", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditTemplateActivity.this, "Failed to edit the Template", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleEditBtn() {
        binding.editBtn.setOnClickListener(view -> {
            String messageTxt = binding.messageTxt.getText().toString();
            String titleTxt = binding.titleTxt.getText().toString();
            editTemplate(template.id, messageTxt, titleTxt);
        });
    }
}