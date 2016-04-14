package com.yeungeek.monkeyandroid.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.model.User;
import com.yeungeek.monkeyandroid.ui.base.view.BaseFragment;
import com.yeungeek.monkeyandroid.util.AppCst;

/**
 * Created by yeungeek on 2016/4/10.
 */
public class UserDetailFragment extends BaseFragment {

    private User mUser;

    public static Fragment newInstance(final Context context, final User user) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppCst.EXTRA_USER, user);
        return Fragment.instantiate(context, UserDetailFragment.class.getName(), bundle);
    }

    @Override
    protected void init() {
        super.init();
        mUser = (User) getArguments().getSerializable(AppCst.EXTRA_USER);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_user_detail;
    }
}
