package com.yeungeek.monkeyandroid.ui.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yeungeek.monkeyandroid.MonkeyApplication;
import com.yeungeek.monkeyandroid.injection.component.ActivityComponent;
import com.yeungeek.monkeyandroid.injection.component.ApplicationComponent;
import com.yeungeek.monkeyandroid.injection.component.DaggerActivityComponent;
import com.yeungeek.monkeyandroid.injection.module.ActivityModule;

import butterknife.ButterKnife;

/**
 * Created by yeungeek on 2016/1/8.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }

    protected void injectDependencies() {

    }

    public ActivityComponent activityComponent() {
        if (null == mActivityComponent) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(getActivityModule())
                    .applicationComponent(getApplicationComponent())
                    .build();
        }
        return mActivityComponent;
    }

    protected ApplicationComponent getApplicationComponent() {
        return MonkeyApplication.get(this).getComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    private ActivityComponent mActivityComponent;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
