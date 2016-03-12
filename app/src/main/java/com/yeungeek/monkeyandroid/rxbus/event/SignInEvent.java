package com.yeungeek.monkeyandroid.rxbus.event;

import android.net.Uri;

/**
 * Created by yeungeek on 2016/3/12.
 */
public class SignInEvent {
    private Uri uri;
    public SignInEvent(final Uri uri){
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }
}
