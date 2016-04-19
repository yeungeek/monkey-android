package com.yeungeek.monkeyandroid.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;

import com.yeungeek.monkeyandroid.data.model.Pair;
import com.yeungeek.monkeyandroid.data.model.User;
import com.yeungeek.monkeyandroid.data.model.WrapUser;
import com.yeungeek.monkeyandroid.ui.base.adapter.HeaderAndFooterRecyclerViewAdapter;
import com.yeungeek.monkeyandroid.ui.base.view.BaseActivity;
import com.yeungeek.monkeyandroid.ui.base.view.BaseLceActivity;
import com.yeungeek.monkeyandroid.ui.base.view.BasePageFragment;
import com.yeungeek.monkeyandroid.ui.users.UserAdapter;
import com.yeungeek.monkeyandroid.util.AppCst;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yeungeek on 2016/4/18.
 */
public class FollowUserFragment extends BasePageFragment<List<User>, FollowUserMvpView, FollowUserPresenter> implements FollowUserMvpView {
    @Inject
    FollowUserPresenter followUserPresenter;

    private Pair<String, WrapUser> pair;

    private UserAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAdapter;

    public static Fragment newInstance(Context context, final Pair<String, WrapUser> pair) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppCst.EXTRA_USER_TYPE, pair);
        return Fragment.instantiate(context, FollowUserFragment.class.getName(), bundle);
    }

    @Override
    public FollowUserPresenter createPresenter() {
        return followUserPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pair = (Pair<String, WrapUser>) getArguments().getSerializable(AppCst.EXTRA_USER_TYPE);
    }

    @Override
    protected void initAdapter() {
        adapter = new UserAdapter(getActivity());
        mHeaderAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(mHeaderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return e.getMessage();
    }

    @Override
    public void setData(List<User> data) {
        switch (pair.first) {
            case AppCst.TITLE_FOLLOWERS:
                mCount = pair.second.getFollowers();
                break;
            case AppCst.TITLE_FOLLOWING:
                mCount = pair.second.getFollowing();
                break;
        }

        if (!mLoadMore) {
            adapter.addTopAll(data);
            mCurrentSize = data.size();
        } else {
            adapter.addAll(data);
            mCurrentSize += data.size();
        }

        checkViewState();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().getUsers(pair.first, pair.second.getLogin(), mPage, pullToRefresh);
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).activityComponent().inject(this);
        }
    }
}
