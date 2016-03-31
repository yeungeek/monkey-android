package com.yeungeek.monkeyandroid.ui.repos;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.model.Language;
import com.yeungeek.monkeyandroid.ui.base.view.BaseToolbarFragment;

/**
 * Created by yeungeek on 2016/3/29.
 */
public class HotReposFragment extends BaseToolbarFragment {
    private LanguagesPagerAdapter mPagerAdapter;

    @Override
    protected void initToolbar() {
        getActionBar().setTitle(R.string.menu_title_user);
    }

    @Override
    protected void initViews() {
        super.initViews();

        mPagerAdapter = new LanguagesPagerAdapter(getChildFragmentManager());
        getViewPager().setAdapter(mPagerAdapter);
        getTabLayout().setupWithViewPager(getViewPager());
    }

    public class LanguagesPagerAdapter extends FragmentPagerAdapter {
        Language[] languagesArray;

        public LanguagesPagerAdapter(FragmentManager fm) {
            super(fm);
            languagesArray = dataManager.getLanguageHelper().getLanguage();
        }

        @Override
        public Fragment getItem(int position) {
            return RepoListFragment.newInstance(getContext(), languagesArray[position]);
        }

        @Override
        public int getCount() {
            return languagesArray.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return languagesArray[position].name;
        }
    }
}
