package com.yeungeek.monkeyandroid.ui.users;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;

import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.model.Language;
import com.yeungeek.monkeyandroid.data.model.User;
import com.yeungeek.monkeyandroid.data.model.WrapList;
import com.yeungeek.monkeyandroid.ui.base.adapter.HeaderAndFooterRecyclerViewAdapter;
import com.yeungeek.monkeyandroid.ui.base.view.BaseLceActivity;
import com.yeungeek.monkeyandroid.ui.base.view.BasePageFragment;
import com.yeungeek.monkeyandroid.util.AppCst;

import javax.inject.Inject;

/**
 * Created by yeungeek on 2016/4/8.
 */
public class UserListFragment extends BasePageFragment<WrapList<User>, UserMvpView, UserPresenter> implements UserMvpView {
    @Inject
    UserPresenter userPresenter;
    Language language;

    private UserAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAdapter;

    public static Fragment newInstance(Context context, Language language) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppCst.EXTRA_LANGUAGE, language);
        return Fragment.instantiate(context, UserListFragment.class.getName(), bundle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        language = (Language) getArguments().getSerializable(AppCst.EXTRA_LANGUAGE);
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
        return getString(R.string.error_users) + "/n" + e.getMessage();
    }

    @Override
    public UserPresenter createPresenter() {
        return userPresenter;
    }

    @Override
    public void setData(WrapList<User> data) {
        mCount = data.getTotal_count();
        if (!mLoadMore) {
            adapter.addTopAll(data.getItems());
            mCurrentSize = data.getItems().size();
        } else {
            adapter.addAll(data.getItems());
            mCurrentSize += data.getItems().size();
        }

        checkViewState();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        String query = "";
        if (language.name.equals(AppCst.USER_CHINA_ALL)) {
            query = language.path;
        } else {
            query += AppCst.LANGUAGE_PREFIX + language.path;
        }

        getPresenter().listUsers(query, mPage, pullToRefresh);
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();
        if (getActivity() instanceof BaseLceActivity) {
            ((BaseLceActivity) getActivity()).activityComponent().inject(this);
        }
    }
}
