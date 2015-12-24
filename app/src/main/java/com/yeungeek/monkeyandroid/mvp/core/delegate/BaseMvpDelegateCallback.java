package com.yeungeek.monkeyandroid.mvp.core.delegate;

import android.app.Activity;

import com.yeungeek.monkeyandroid.mvp.common.MvpPresenter;
import com.yeungeek.monkeyandroid.mvp.common.MvpView;

/**
 * The MvpDelegate callback that will be called from  {@link
 * FragmentMvpDelegate} or {@link ViewGroupMvpDelegate}. This interface must be implemented by all
 * Fragment or android.view.View that you want to support mosbys mvp. Please note that Activties
 * need a special callback {@link ActivityMvpDelegateCallback}
 *
 * @param <V> The type of {@link MvpView}
 * @param <P> The type of {@link MvpPresenter}
 */
public interface BaseMvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>> {
    P createPresenter();

    P getPresenter();

    void setPresenter(P presenter);

    V getMvpView();

    /**
     * Indicate whether the retain instance feature is enabled by this view or not
     */
    boolean isRetainInstance();

    /**
     * Mark this instance as retaining. This means that the feature of a retaining instance is
     * enabled.
     */
    void setRetainInstance(boolean retainingInstance);

    /**
     * Indicates whether or not the the view will be retained during next screen orientation change.
     * This boolean flag is used for {@link MvpPresenter#detachView(boolean)}
     * as parameter. Usually you should take {@link Activity#isChangingConfigurations()} into
     * account. The difference between {@link #shouldInstanceBeRetained()} and {@link
     * #isRetainInstance()} is that {@link #isRetainInstance()} indicates that retain instance
     * feature is enabled or disabled while {@link #shouldInstanceBeRetained()} indicates if the
     * view is going to be destroyed permanently and hence should no more be retained (i.e. Activity
     * is finishing and not just screen orientation changing)
     *
     * @return true if the instance should be retained, otherwise false
     */
    boolean shouldInstanceBeRetained();
}
