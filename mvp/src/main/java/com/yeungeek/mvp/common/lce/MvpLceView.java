package com.yeungeek.mvp.common.lce;


import com.yeungeek.mvp.common.MvpView;

/**
 * Created by yeungeek on 2015/12/24.
 */
public interface MvpLceView<V> extends MvpView {

    /**
     * Display a loading view while loading data in background.
     * <b>The loading view must have the id = R.id.loadingView</b>
     *
     * @param pullToRefresh true, if pull-to-refresh has been invoked loading.
     */
    void showLoading(boolean pullToRefresh);

    void showContent();

    void showError(Throwable e, boolean pullToRefresh);

    void setData(V data);

    /**
     * Load the data. Typically invokes the presenter method to load the desired data.
     * <p>
     * <b>Should not be called from presenter</b> to prevent infinity loops. The method is declared
     * in
     * the views interface to add support for view state easily.
     * </p>
     *
     * @param pullToRefresh true, if triggered by a pull to refresh. Otherwise false.
     */
    void loadData(boolean pullToRefresh);
}
