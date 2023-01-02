package com.improve10x.chatwithv2.templates;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
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
        db.collection("templates")
                .add(template)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddTemplateActivity.this, "successfully added the template", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }
}