package com.yeungeek.monkeyandroid;

import com.yeungeek.monkeyandroid.data.local.PreferencesHelper;
import com.yeungeek.monkeyandroid.util.DefaultConfig;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Created by yeungeek on 2016/4/25.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class PreferencesHelperTest {
    private PreferencesHelper mPreferencesHelper =
            new PreferencesHelper(RuntimeEnvironment.application);

    @Before
    public void setUp() {
        mPreferencesHelper.clear();
    }

    @Test
    public void testPutGetAccessToken() {
        String token = "token";
        mPreferencesHelper.putAccessToken(token);
        Assert.assertEquals(token, mPreferencesHelper.getAccessToken());
    }
}
