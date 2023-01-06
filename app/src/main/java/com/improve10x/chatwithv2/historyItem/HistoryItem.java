package com.improve10x.chatwithv2.historyItem;

public class HistoryItem {

    public String id;
    public String name;
    public String number;
    public String message;
    public long sentMessageTimestamp;

    public HistoryItem() {
    }

    public HistoryItem(String messageTxt, String nameTxt, String numberTxt, long sentMessageTimestamp) {
        this.message = messageTxt;
        this.name = nameTxt;
        this.number = numberTxt;
        this.sentMessageTimestamp = sentMessageTimestamp;
    }
}
