package com.yeungeek.monkeyandroid.mvp;

import android.support.v4.app.Fragment;

import com.yeungeek.monkeyandroid.mvp.common.MvpPresenter;
import com.yeungeek.monkeyandroid.mvp.common.MvpView;
import com.yeungeek.monkeyandroid.mvp.core.delegate.BaseMvpDelegateCallback;

/**
 * Created by yeungeek on 2015/12/24.
 */
public abstract class MvpFragment<V extends MvpView, P extends MvpPresenter<V>> extends Fragment
        implements BaseMvpDelegateCallback<V, P>, MvpView {


}
