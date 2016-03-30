package com.yeungeek.monkeyandroid.ui.base.view;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.DataManager;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by yeungeek on 2016/3/29.
 */
public abstract class BaseToolbarFragment extends BaseFragment {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager viewPager;

    @Inject
    protected DataManager dataManager;

    private ActionBar actionBar;

    @Override
    protected int getLayoutRes() {
        return R.layout.layout_appbar;
    }

    @Override
    protected void initViews() {
        super.initViews();

        if (null != toolbar) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (null != actionBar) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
                initToolbar();
            }
        }
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();
        if (getActivity() instanceof BaseLceActivity) {
            ((BaseLceActivity) getActivity()).activityComponent().inject(this);
        }
    }

    protected abstract void initToolbar();

    public ActionBar getActionBar() {
        return actionBar;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }
}
