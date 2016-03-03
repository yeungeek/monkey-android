package com.yeungeek.monkeyandroid;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.tencent.bugly.crashreport.CrashReport;
import com.yeungeek.monkeyandroid.injection.component.ApplicationComponent;
import com.yeungeek.monkeyandroid.injection.component.DaggerApplicationComponent;
import com.yeungeek.monkeyandroid.injection.module.ApplicationModule;

import timber.log.Timber;

/**
 * Created by yeungeek on 2016/1/8.
 */
public class MonkeyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        initComponent();
        initAnalysis();
        initStetho();
    }

    private void initComponent() {
        if (BuildConfig.DEBUG) {
            Timber.d("dagger inject");
        }
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
    }

    private void initAnalysis() {
        CrashReport.initCrashReport(this, BuildConfig.BUGLY_APPID, false);
//        CrashReport.testJavaCrash();
    }

    private void initStetho(){
        Stetho.initializeWithDefaults(this);
    }

    public static MonkeyApplication get(final Context context) {
        return (MonkeyApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    ApplicationComponent mApplicationComponent;
}
