package com.yeungeek.monkeyandroid.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.model.Repo;
import com.yeungeek.monkeyandroid.ui.base.view.BaseFragment;
import com.yeungeek.monkeyandroid.util.AppCst;

/**
 * Created by yeungeek on 2016/4/10.
 */
public class RepoDetailFragment extends BaseFragment {
    public static Fragment newInstance(final Context context, final Repo repo) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppCst.EXTRA_REPO, repo);
        return Fragment.instantiate(context, RepoDetailFragment.class.getName(), bundle);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_repo_detail;
    }
}
