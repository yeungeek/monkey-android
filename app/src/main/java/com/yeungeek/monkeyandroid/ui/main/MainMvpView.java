package com.yeungeek.monkeyandroid.ui.main;

import com.yeungeek.monkeyandroid.data.model.User;
import com.yeungeek.mvp.common.lce.MvpLceView;

/**
 * Created by yeungeek on 2016/3/13.
 */
public interface MainMvpView extends MvpLceView<User> {
    void unauthorized();
}
