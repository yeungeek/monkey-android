package com.yeungeek.monkeyandroid.ui.detail;

import com.yeungeek.mvp.common.lce.MvpLceView;

/**
 * Created by yeungeek on 2016/4/13.
 */
public interface RepoDetailMvpView extends MvpLceView<String> {
    void starStatus(boolean isStaring);
    void notLogined();
}
