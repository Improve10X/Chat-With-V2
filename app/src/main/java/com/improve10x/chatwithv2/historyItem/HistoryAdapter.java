package com.improve10x.chatwithv2.historyItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.chatwithv2.databinding.HistoryItemBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    private List<HistoryItem> historyItem;
    private OnItemActionListener onItemActionListener;

    public void setHistoryItem(List<HistoryItem> historyItem) {
        this.historyItem = historyItem;
        notifyDataSetChanged();
    }

    public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HistoryItemBinding binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        HistoryViewHolder historyViewHolder = new HistoryViewHolder(binding);
        return historyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryItem history = this.historyItem.get(position);
        holder.binding.nameTextTxt.setText(history.name);
        holder.binding.numberTextTxt.setText(history.number);
        Date date = new Date(history.sentMessageTimestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
        String displayDate = dateFormat.format(date);
        holder.binding.timeTextTxt.setText(displayDate);
        holder.binding.messageTextTxt.setText(history.message);
        holder.binding.getRoot().setOnLongClickListener(view -> {
            holder.binding.actionLayout.setVisibility(View.VISIBLE);
            holder.binding.deleteBtn.setVisibility(View.VISIBLE);
            holder.binding.addToTemplatesBtn.setVisibility(View.VISIBLE);
            return true;
        });
        holder.binding.deleteBtn.setOnClickListener(view -> {
            onItemActionListener.onItemDelete(history.id);
            holder.binding.deleteBtn.setVisibility(View.GONE);
            holder.binding.addToTemplatesBtn.setVisibility(View.GONE);
        });
        holder.binding.addToTemplatesBtn.setOnClickListener(view -> {
            onItemActionListener.saveTemplates(history.message);
            holder.binding.deleteBtn.setVisibility(View.GONE);
            holder.binding.addToTemplatesBtn.setVisibility(View.GONE);
        });
    }

    @Override
    public int getItemCount() {
        return historyItem.size();
    }
}
