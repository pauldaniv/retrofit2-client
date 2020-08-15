package com.pauldaniv.template.lib.second.consume;

import com.pauldaniv.template.lib.first.util.TestUtil;

public class User {

    private String first;
    private String second;

    public User(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String doTest() {
        return TestUtil.join(first, second);
    }
}
