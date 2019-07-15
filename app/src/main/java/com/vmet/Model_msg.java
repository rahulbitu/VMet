package com.vmet;

import android.widget.TextView;

public class Model_msg {

    private TextView text;

    public Model_msg(TextView text) {
        this.text = text;
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Model_msg{" +
                "text=" + text +
                '}';
    }
}
