package com.yeungeek.monkeyandroid.injection.component;

import android.app.Application;
import android.content.Context;

import com.yeungeek.monkeyandroid.MonkeyApplication;
import com.yeungeek.monkeyandroid.data.local.DatabaseHelper;
import com.yeungeek.monkeyandroid.data.remote.GithubApi;
import com.yeungeek.monkeyandroid.injection.ApplicationContext;
import com.yeungeek.monkeyandroid.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MonkeyApplication monkeyApplication);

    @ApplicationContext
    Context context();

    Application application();
    GithubApi githubApi();
    DatabaseHelper databaseHelper();
}
