package com.improve10x.chatwithv2;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.chatwithv2.databinding.TemplateItemBinding;

public class TemplateViewHolder extends RecyclerView.ViewHolder {

    TemplateItemBinding binding;

    public TemplateViewHolder(TemplateItemBinding templateItemBinding) {
        super(templateItemBinding.getRoot());
        binding = templateItemBinding;
    }
}
