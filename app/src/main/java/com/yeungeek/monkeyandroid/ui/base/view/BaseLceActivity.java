package com.yeungeek.monkeyandroid.ui.base.view;

import android.os.Bundle;
import android.view.View;

import com.yeungeek.monkeyandroid.MonkeyApplication;
import com.yeungeek.monkeyandroid.injection.component.ActivityComponent;
import com.yeungeek.monkeyandroid.injection.component.ApplicationComponent;
import com.yeungeek.monkeyandroid.injection.component.DaggerActivityComponent;
import com.yeungeek.monkeyandroid.injection.module.ActivityModule;
import com.yeungeek.mvp.common.MvpPresenter;
import com.yeungeek.mvp.common.lce.MvpLceView;
import com.yeungeek.mvp.core.lce.MvpLceActivity;

import butterknife.ButterKnife;

/**
 * Created by yeungeek on 2016/1/10.
 */
public abstract class BaseLceActivity<C extends View, M, V extends MvpLceView<M>, P extends MvpPresenter<V>>
        extends MvpLceActivity<C, M, V, P> implements View.OnClickListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * mvp
     */
    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public void loadData(boolean pullToRefresh) {

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
}
