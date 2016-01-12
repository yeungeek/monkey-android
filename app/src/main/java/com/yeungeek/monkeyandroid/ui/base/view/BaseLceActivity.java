package com.yeungeek.monkeyandroid.ui.base.view;

import android.os.Bundle;
import android.view.View;

import com.yeungeek.mvp.common.MvpPresenter;
import com.yeungeek.mvp.common.lce.MvpLceView;
import com.yeungeek.mvp.core.lce.MvpLceActivity;

import butterknife.ButterKnife;

/**
 * Created by yeungeek on 2016/1/10.
 */
public abstract class BaseLceActivity<CV extends View, M, V extends MvpLceView<M>, P extends MvpPresenter<V>>
        extends MvpLceActivity<CV, M, V, P> {
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

    protected void injectDependencies() {
    }
}
