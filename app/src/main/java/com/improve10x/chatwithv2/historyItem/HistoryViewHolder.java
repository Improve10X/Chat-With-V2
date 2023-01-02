package com.improve10x.chatwithv2.historyItem;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.chatwithv2.databinding.HistoryItemBinding;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    HistoryItemBinding binding;

    public HistoryViewHolder(HistoryItemBinding historyItemBinding) {
        super(historyItemBinding.getRoot());
        binding = historyItemBinding;
    }
}
