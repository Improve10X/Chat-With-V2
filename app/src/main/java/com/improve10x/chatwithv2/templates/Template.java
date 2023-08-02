package com.improve10x.chatwithv2.templates;

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

    public Template(String messageTxt) {
        this.messageText = messageTxt;
    }
}
