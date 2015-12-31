package com.yeungeek.monkeyandroid.mvp.core.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.yeungeek.monkeyandroid.mvp.common.MvpPresenter;
import com.yeungeek.monkeyandroid.mvp.common.MvpView;

/**
 * A delegate for Activities to attach them to mosbies mvp.
 *
 * <p>
 * The following methods must be invoked from the corresponding Activities lifecycle methods:
 * <ul>
 * <li>{@link #onCreate(Bundle)}</li>
 * <li>{@link #onDestroy()}</li>
 * <li>{@link #onPause()} </li>
 * <li>{@link #onResume()} </li>
 * <li>{@link #onStart()} </li>
 * <li>{@link #onStop()} </li>
 * <li>{@link #onRestart()} </li>
 * <li>{@link #onContentChanged()} </li>
 * <li>{@link #onSaveInstanceState(Bundle)} </li>
 * <li>{@link #onPostCreate(Bundle)} </li>
 * <li></li>
 * </ul>
 * </p>
 *
 * @param <V> The type of {@link MvpView}
 * @param <P> The type of {@link MvpPresenter}
 * @author Hannes Dorfmann
 * @since 1.1.0
 */
public interface ActivityMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {

  /**
   * This method must be called from {@link Activity#onCreate(Bundle)}.
   * This method internally creates the presenter and attaches the view to it.
   */
  void onCreate(Bundle bundle);

  /**
   * This method must be called from {@link Activity#onDestroy()}}.
   * This method internally detaches the view from presenter
   */
  void onDestroy();

  /**
   * This method must be called from {@link Activity#onPause()}
   */
  void onPause();

  /**
   * This method must be called from {@link Activity#onResume()}
   */
  void onResume();

  /**
   * This method must be called from {@link Activity#onStart()}
   */
  void onStart();

  /**
   * This method must be called from {@link Activity#onStop()}
   */
  void onStop();

  /**
   * This method must be called from {@link Activity#onRestart()}
   */
  void onRestart();

  /**
   * This method must be called from {@link Activity#onContentChanged()}
   */
  void onContentChanged();

  /**
   * This method must be called from {@link Activity#onSaveInstanceState(Bundle)}
   */
  void onSaveInstanceState(Bundle outState);

  /**
   * This method must be called from {@link Activity#onPostCreate(Bundle)}
   */
  void onPostCreate(Bundle savedInstanceState);

  /**
   * This method must be called from {@link FragmentActivity#onRetainCustomNonConfigurationInstance()}
   *
   * @return Don't forget to return the value returned by this delegate method
   */
  Object onRetainCustomNonConfigurationInstance();

  /**
   * @return the value returned from {@link ActivityMvpDelegateCallback#onRetainNonMosbyCustomNonConfigurationInstance()}
   */
  Object getNonMosbyLastCustomNonConfigurationInstance();
}
