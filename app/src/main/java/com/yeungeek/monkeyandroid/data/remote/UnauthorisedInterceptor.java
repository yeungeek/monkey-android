package com.yeungeek.monkeyandroid.data.remote;

import android.content.Context;

import com.yeungeek.monkeyandroid.MonkeyApplication;
import com.yeungeek.monkeyandroid.rxbus.RxBus;
import com.yeungeek.monkeyandroid.rxbus.event.BusEvent;
import com.yeungeek.monkeyandroid.util.HttpStatus;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by yeungeek on 2016/4/8.
 */
public class UnauthorisedInterceptor implements Interceptor {
    @Inject
    RxBus rxBus;

    public UnauthorisedInterceptor(Context context) {
        MonkeyApplication.get(context).getComponent().inject(this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.code() == HttpStatus.HTTP_UNAUTHORIZED) {
            Timber.e("### unauthorized");
            rxBus.send(new BusEvent.AuthenticationError());
        }
        return response;
    }
}
