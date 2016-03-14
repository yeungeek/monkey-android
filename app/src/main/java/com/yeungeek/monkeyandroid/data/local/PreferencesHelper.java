package com.yeungeek.monkeyandroid.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.yeungeek.monkeyandroid.injection.ApplicationContext;

import javax.inject.Inject;

/**
 * Created by yeungeek on 2016/3/13.
 */
public class PreferencesHelper {
    public static final String PREF_FILE_NAME = "monkey_app_pref_file";

    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void putAccessToken(String accessToken) {
        mPref.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Nullable
    public String getAccessToken() {
        return mPref.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }
}
