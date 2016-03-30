package com.yeungeek.monkeyandroid.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.yeungeek.monkeyandroid.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by yeungeek on 2016/3/13.
 */
@Singleton
public class PreferencesHelper {
    public static final String PREF_FILE_NAME = "monkey_app_pref_file";

    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    private static final String PREF_KEY_USER_LOGIN = "PREF_KEY_USER_LOGIN";
    private static final String PREF_KEY_USER_EMAIL = "PREF_KEY_USER_EMAIL";
    private static final String PREF_KEY_USER_AVATAR = "PREF_KEY_USER_AVATAR";

    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void putAccessToken(String accessToken) {
        mPref.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    public void putUserLogin(String login) {
        mPref.edit().putString(PREF_KEY_USER_LOGIN, login).apply();
    }

    public void putUserEmail(String email) {
        mPref.edit().putString(PREF_KEY_USER_EMAIL, email).apply();
    }

    public void putUserAvatar(String avatar) {
        mPref.edit().putString(PREF_KEY_USER_AVATAR, avatar).apply();
    }

    @Nullable
    public String getAccessToken() {
        return mPref.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Nullable
    public String getUserLogin() {
        return mPref.getString(PREF_KEY_USER_LOGIN, null);
    }

    @Nullable
    public String getUserEmail() {
        return mPref.getString(PREF_KEY_USER_EMAIL, null);
    }

    @Nullable
    public String getUserAvatar() {
        return mPref.getString(PREF_KEY_USER_AVATAR, null);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }
}
