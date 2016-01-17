package com.yeungeek.monkeyandroid.injection.component;

import com.yeungeek.monkeyandroid.injection.PerActivity;
import com.yeungeek.monkeyandroid.injection.module.ActivityModule;
import com.yeungeek.monkeyandroid.ui.sample.SampleActivity;

import dagger.Component;

/**
 * Created by yeungeek on 2016/1/17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SampleActivity sampleActivity);
}
