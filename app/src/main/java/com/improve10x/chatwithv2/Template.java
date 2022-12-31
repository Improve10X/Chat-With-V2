package com.improve10x.chatwithv2;

import java.io.Serializable;

public class Template implements Serializable {

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
