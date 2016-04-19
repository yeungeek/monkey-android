package com.yeungeek.monkeyandroid.ui.detail;

import com.yeungeek.monkeyandroid.data.model.WrapUser;
import com.yeungeek.mvp.common.lce.MvpLceView;

/**
 * Created by yeungeek on 2016/4/13.
 */
public interface UserDetailMvpView extends MvpLceView<WrapUser> {
    void followStatus(boolean isFollowing);
    void notLogined();
}
