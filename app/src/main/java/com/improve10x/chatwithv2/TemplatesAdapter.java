package com.improve10x.chatwithv2;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.chatwithv2.databinding.TemplateItemBinding;

import java.util.ArrayList;

public class TemplatesAdapter extends RecyclerView.Adapter<TemplateViewHolder> {
private ArrayList<Template> templates;

    public void setTemplates(ArrayList<Template> templates) {
        this.templates = templates;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TemplateItemBinding binding = TemplateItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        TemplateViewHolder viewHolder = new TemplateViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TemplateViewHolder holder, int position) {
        Template template = this.templates.get(position);
        holder.binding.messageTextTxt.setText(template.messageTxt);
    }

    @Override
    public int getItemCount() {
        return templates.size();
    }
}
