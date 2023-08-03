package com.improve10x.chatwithv2.historyItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.chatwithv2.databinding.HistoryItemBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

        holder.binding.timeTextTxt.setText(getDisplayDate(history.sentMessageTimestamp));
        holder.binding.messageTextTxt.setText(history.message);
        holder.binding.actionLayout.setVisibility(View.GONE);
        holder.binding.getRoot().setOnLongClickListener(view -> {
            holder.binding.actionLayout.setVisibility(View.VISIBLE);
            return true;
        });
        holder.binding.deleteBtn.setOnClickListener(view -> {
            onItemActionListener.onItemDelete(history.id);
            holder.binding.actionLayout.setVisibility(View.GONE);
        });
        holder.binding.addToTemplatesBtn.setOnClickListener(view -> {
            onItemActionListener.saveTemplates(history.message);
            holder.binding.actionLayout.setVisibility(View.GONE);
        });
    }

    @Override
    public int getItemCount() {
        return historyItem.size();
    }

    public String getDisplayDate(long secondsInMills) {
        if (secondsInMills <= 0) {
            throw new InvalidTimeStampException();
        }
        Calendar instantCal = Calendar.getInstance();
        instantCal.setTimeInMillis(secondsInMills);
        Calendar currentCal = Calendar.getInstance();
        if (isSameDay(instantCal, currentCal)) {
            Date date = new Date(secondsInMills);
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
            String displayDate = dateFormat.format(date);
            return displayDate;
        }
        currentCal.add(Calendar.DAY_OF_MONTH, -1);
        if (isSameDay(instantCal, currentCal)) {
            return "Yesterday";
        }

        int inputYear = instantCal.get(Calendar.YEAR);
        int currentYear = currentCal.get(Calendar.YEAR);
        SimpleDateFormat dateFormat;
        if (inputYear == currentYear) {
            dateFormat = new SimpleDateFormat("dd MMM");
        } else {
            dateFormat = new SimpleDateFormat("dd MMM yyyy");
        }
        return dateFormat.format(instantCal.getTime());
    }

    private boolean isSameDay(Calendar instantCal, Calendar currentCal) {
        return instantCal.get(Calendar.YEAR) == currentCal.get(Calendar.YEAR) &&
                instantCal.get(Calendar.DAY_OF_MONTH) == currentCal.get(Calendar.DAY_OF_MONTH);
    }
    public class InvalidTimeStampException extends RuntimeException {
    }
}
