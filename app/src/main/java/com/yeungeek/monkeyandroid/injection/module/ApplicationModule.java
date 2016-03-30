package com.yeungeek.monkeyandroid.injection.module;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.yeungeek.monkeyandroid.BuildConfig;
import com.yeungeek.monkeyandroid.data.remote.GithubApi;
import com.yeungeek.monkeyandroid.injection.ApplicationContext;
import com.yeungeek.monkeyandroid.rxbus.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by yeungeek on 2016/1/14.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(final Application application) {
        this.mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    GithubApi provideGithubApi(Retrofit retrofit) {
        return retrofit.create(GithubApi.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(){
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            okHttpClient.interceptors().add()
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        //@see https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor
        final OkHttpClient okHttpClient = builder.addInterceptor(logging)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        return okHttpClient;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GithubApi.ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    RxBus provideRxBus(){
        return new RxBus();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }
}
