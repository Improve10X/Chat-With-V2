package com.improve10x.chatwithv2.templates

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.improve10x.chatwithv2.base.BaseActivity
import com.improve10x.chatwithv2.base.showToast
import com.improve10x.chatwithv2.databinding.ActivityEditTemplateBinding

class EditTemplateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTemplateBinding
    private var template: Template? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityEditTemplateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(intent.hasExtra("templates")) {
            supportActionBar?.title = "Edit Template"
            template = intent.getSerializableExtra("templates") as Template
            showData()
            handleEditBtn()
        }
    }

    private fun handleEditBtn() {
        binding.editBtn.setOnClickListener {
            val messageText = binding.messageTxt.text.toString()
            val titleText = binding.titleTxt.text.toString()
            template?.id?.let {
                editTemplate(it, messageText, titleText)
            }
        }
    }

    private fun editTemplate(id: String, messageText: String, titleText: String) {
        template = Template(messageText, titleText)
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            db.collection("/users/" + it.uid + "/templates")
                .document(id)
                .set(template!!)
                .addOnSuccessListener {
                    showToast("Successfully edited the Template")
                    finish()
                }.addOnFailureListener {
                    showToast("Failed to edit the Template")
                }
        }
    }

    private fun showData() {
        template?.let {
            binding.titleTxt.setText(it.titleText)
            binding.messageTxt.setText(it.messageText)
        }
    }
}