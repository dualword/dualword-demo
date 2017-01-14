package org.dualword.android.notedemo;

import java.io.Serializable;

public class Note implements Serializable{
    private Long id;
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private static final long serialVersionUID = 1L;
}
