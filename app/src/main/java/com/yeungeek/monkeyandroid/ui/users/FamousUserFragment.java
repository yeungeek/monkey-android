package com.yeungeek.monkeyandroid.ui.users;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.model.Language;
import com.yeungeek.monkeyandroid.ui.base.view.BaseToolbarFragment;
import com.yeungeek.monkeyandroid.util.AppCst;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeungeek on 2016/4/8.
 */
public class FamousUserFragment extends BaseToolbarFragment {
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
        List<Language> languagesArray = new ArrayList<>();

        public LanguagesPagerAdapter(FragmentManager fm) {
            super(fm);
            languagesArray.addAll(dataManager.getLanguageHelper().getLanguage());
            Language language = new Language(AppCst.USER_CHINA_ALL, AppCst.USER_LOCATION_CHINA);
            languagesArray.add(0, language);
        }

        @Override
        public Fragment getItem(int position) {
            return UserListFragment.newInstance(getContext(), languagesArray.get(position));
        }

        @Override
        public int getCount() {
            return languagesArray.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return languagesArray.get(position).name;
        }
    }
}
