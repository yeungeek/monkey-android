package com.yeungeek.monkeyandroid.mvp.core.delegate;

import android.os.Bundle;

import com.yeungeek.monkeyandroid.mvp.common.MvpPresenter;
import com.yeungeek.monkeyandroid.mvp.common.MvpView;

/**
 * The concrete implementation of {@link}
 */
public class ActivityMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>>
        implements ActivityMvpDelegate {
    protected MvpInternalDelegate<V, P> internalDelegate;
    protected ActivityMvpDelegateCallback<V, P> delegateCallback;

    public ActivityMvpDelegateImpl(ActivityMvpDelegateCallback<V, P> delegateCallback) {
        if (delegateCallback == null) {
            throw new NullPointerException("MvpDelegateCallback is null!");
        }
        this.delegateCallback = delegateCallback;
    }

    /**
     * Get the internal delegate.
     */
    protected MvpInternalDelegate<V, P> getInternalDelegate() {
        if (internalDelegate == null) {
            internalDelegate = new MvpInternalDelegate<>(delegateCallback);
        }

        return internalDelegate;
    }

    @Override
    public void onCreate(Bundle bundle) {
        ActivityMvpNonConfigurationInstances<V, P> nci =
                (ActivityMvpNonConfigurationInstances<V, P>) delegateCallback.getLastCustomNonConfigurationInstance();

        if (null != nci && null != nci.presenter) {
            delegateCallback.setPresenter(nci.presenter);
        } else {
            getInternalDelegate().createPresenter();
        }

        getInternalDelegate().attachView();
    }

    @Override
    public void onDestroy() {
        getInternalDelegate().detachView();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onContentChanged() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {

    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        P presenter = delegateCallback.shouldInstanceBeRetained() ? delegateCallback.getPresenter() : null;
        Object nonMosbyConfiguraionInstance =
                delegateCallback.onRetainNonMosbyCustomNonConfigurationInstance();

        if (presenter == null && nonMosbyConfiguraionInstance == null) {
            return null;
        }

        return new ActivityMvpNonConfigurationInstances<>(presenter, nonMosbyConfiguraionInstance);
    }

    @Override
    public Object getNonMosbyLastCustomNonConfigurationInstance() {
        ActivityMvpNonConfigurationInstances last =
                (ActivityMvpNonConfigurationInstances) delegateCallback.getLastCustomNonConfigurationInstance();
        return last == null ? null : last.nonMosbyCustomConfigurationInstance;
    }
}
