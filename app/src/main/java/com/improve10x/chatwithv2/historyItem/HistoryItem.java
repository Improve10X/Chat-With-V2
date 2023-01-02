package com.improve10x.chatwithv2.historyItem;

public class HistoryItem {
    public String id;
    public String name;
    public String number;
    public String time;
    public String message;

    public HistoryItem() {
    }

    public HistoryItem(String messageTxt, String nameTxt, String numberTxt, String timeTxt) {
        this.message = messageTxt;
        this.name = nameTxt;
        this.number = numberTxt;
        this.time = timeTxt;

    }
}
