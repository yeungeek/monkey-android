package com.yeungeek.monkeyandroid.data.remote;

import android.content.Context;
import android.text.TextUtils;

import com.yeungeek.monkeyandroid.MonkeyApplication;
import com.yeungeek.monkeyandroid.data.local.PreferencesHelper;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yeungeek on 2016/4/19.
 */
public class TokenInterceptor implements Interceptor {
    @Inject
    PreferencesHelper preferencesHelper;

    public TokenInterceptor(Context context) {
        MonkeyApplication.get(context).getComponent().inject(this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .header(GithubApi.AUTH_HEADER, TextUtils.isEmpty(preferencesHelper.getAccessToken()) ? "" : GithubApi.AUTH_TOKEN + preferencesHelper.getAccessToken())
                .method(original.method(), original.body());

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
