package com.yeungeek.monkeyandroid.ui.detail;

import android.support.v4.app.Fragment;

import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.ui.base.view.BaseFragment;

/**
 * Created by yeungeek on 2016/4/10.
 */
public class RepoDetailFragment extends BaseFragment {
    public static Fragment newInstance() {
        return new RepoDetailFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_repo_detail;
    }
}
