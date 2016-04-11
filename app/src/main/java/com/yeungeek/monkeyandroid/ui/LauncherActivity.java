package com.yeungeek.monkeyandroid.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yeungeek.monkeyandroid.data.DataManager;
import com.yeungeek.monkeyandroid.ui.base.view.BaseActivity;

import javax.inject.Inject;

/**
 * Created by yeungeek on 2016/3/16.
 */
public class LauncherActivity extends BaseActivity {
    @Inject
    DataManager dataManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
    }
}
