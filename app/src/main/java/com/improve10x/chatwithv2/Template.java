package com.improve10x.chatwithv2;

public class Template {

    public String id;
    
    public String messageText;
    public String titleText;

    public Template() {
    }

    public Template(String messageTxt, String titleText) {
        this.messageText = messageTxt;
        this.titleText = titleText;
    }
}
