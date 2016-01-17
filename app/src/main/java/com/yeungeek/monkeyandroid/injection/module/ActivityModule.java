package com.yeungeek.monkeyandroid.injection.module;

import android.app.Activity;
import android.content.Context;

import com.yeungeek.monkeyandroid.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yeungeek on 2016/1/17.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(final Activity activity) {
        this.mActivity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext(){
        return mActivity;
    }
}
