package com.improve10x.chatwithv2.historyItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.chatwithv2.databinding.HistoryItemBinding;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    private List<HistoryItem> histories;
    private OnItemActionListener onItemActionListener;

    public void setHistories(List<HistoryItem> histories) {
        this.histories = histories;
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
        HistoryItem history = this.histories.get(position);
        holder.binding.nameTextTxt.setText(history.name);
        holder.binding.numberTextTxt.setText(history.number);
        holder.binding.timeTextTxt.setText(history.sentMessageTimestamp + " ");
        holder.binding.messageTextTxt.setText(history.message);
        holder.binding.getRoot().setOnLongClickListener(view -> {
            holder.binding.actionLayout.setVisibility(View.VISIBLE);
            return true;
        });
        holder.binding.deleteBtn.setOnClickListener(view -> {
            onItemActionListener.onItemDelete(history.id);
        });


    }

    @Override
    public int getItemCount() {
        return histories.size();
    }
}
