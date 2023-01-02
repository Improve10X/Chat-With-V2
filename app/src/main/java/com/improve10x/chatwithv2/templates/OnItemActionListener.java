package com.improve10x.chatwithv2.templates;

import com.improve10x.chatwithv2.templates.Template;

public interface OnItemActionListener {

    void onDelete(String id);

    void onEdit(Template template);

    void onClicked(Template template);

}
