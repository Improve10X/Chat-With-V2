package com.improve10x.chatwithv2.templates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.chatwithv2.databinding.TemplateItemBinding;

import java.util.List;

public class TemplatesAdapter extends RecyclerView.Adapter<TemplateViewHolder> {

    private List<Template> templates;
    private OnItemActionListener onItemActionListener;

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
        notifyDataSetChanged();
    }

    public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
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
        holder.binding.messageTextTxt.setText(template.messageText);
        if (template.titleText == null || template.titleText.isEmpty()) {
            holder.binding.titleTxt.setVisibility(View.GONE);
        } else {
            holder.binding.titleTxt.setVisibility(View.VISIBLE);
            holder.binding.titleTxt.setText(template.titleText);
        }
        holder.binding.getRoot().setOnLongClickListener(view -> {
            holder.binding.actionLayout.setVisibility(View.VISIBLE);
            return true;
        });
        holder.binding.deleteBtn.setOnClickListener(view -> {
            onItemActionListener.onDelete(template.id);
            holder.binding.actionLayout.setVisibility(View.GONE);
        });
        holder.binding.editBtn.setOnClickListener(view -> {
            onItemActionListener.onEdit(template);
            holder.binding.actionLayout.setVisibility(View.GONE);
        });
        holder.binding.getRoot().setOnClickListener(view -> {
            onItemActionListener.onClicked(template);
        });
    }

    @Override
    public int getItemCount() {
        return templates.size();
    }
}
