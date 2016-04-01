package com.yeungeek.monkeyandroid.ui.base.view;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yeungeek.mvp.common.MvpPresenter;
import com.yeungeek.mvp.common.lce.MvpLceView;
import com.yeungeek.mvp.core.lce.MvpLceFragment;

import butterknife.ButterKnife;

/**
 * Created by yeungeek on 2016/1/10.
 */
public abstract class BaseLceFragment<C extends View, M, V extends MvpLceView<M>, P extends MvpPresenter<V>>
        extends MvpLceFragment<C, M, V, P> {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        injectDependencies();
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    /**
     * Inject the dependencies
     */
    protected void injectDependencies() {

    }

    protected void initView(){}
}
