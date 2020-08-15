package com.pauldaniv.template.lib.first.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TestUtilTest {
    @Test
    public void join() {
        assertThat(TestUtil.join("one", "two")).isEqualTo("onetwo");
    }
}
