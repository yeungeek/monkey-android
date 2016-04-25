package com.yeungeek.monkeyandroid;

import com.yeungeek.monkeyandroid.util.EncodingUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by yeungeek on 2016/4/24.
 */
public class EncodingUtilTest {
    @Test
    public void testFromBase64() throws Exception {
        String str = "YW5kcm9pZA==";
        Assert.assertEquals(null, EncodingUtil.fromBase64(str));
    }
}
