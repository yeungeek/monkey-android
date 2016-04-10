package com.yeungeek.monkeyandroid.ui.trending;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.model.Language;
import com.yeungeek.monkeyandroid.ui.base.view.BaseToolbarFragment;
import com.yeungeek.monkeyandroid.util.AppCst;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by yeungeek on 2016/4/9.
 */
public class TrendingFragment extends BaseToolbarFragment {
    private LanguagesPagerAdapter mPagerAdapter;
    String mTimeSpan = "daily";
    int mCurrentPosition = 0;

    @Override
    protected void initToolbar() {
        getActionBar().setTitle(null);

        View spinnerContainer = LayoutInflater.from(getActivity()).inflate(R.layout.layout_trending_repos, getToolbar(), false);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getToolbar().addView(spinnerContainer, lp);

        Spinner spinner = (Spinner) spinnerContainer.findViewById(R.id.id_trending_time_spinner);
        spinner.setAdapter(new SinceSpinnerAdapter());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // daily
                        mTimeSpan = "daily";
                        break;
                    case 1: // weekly
                        mTimeSpan = "weekly";
                        break;
                    case 2: // monthly
                        mTimeSpan = "monthly";
                        break;

                }
                Timber.d("### onItemSelected position: %d", mCurrentPosition);
                updateTimeSpan(mCurrentPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void initViews() {
        super.initViews();

        mPagerAdapter = new LanguagesPagerAdapter(getChildFragmentManager());
        getViewPager().setAdapter(mPagerAdapter);
        getTabLayout().setupWithViewPager(getViewPager());

        getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Timber.d("### onItemSelected onPageSelected position: %d", position);
                mCurrentPosition = position;
                updateTimeSpan(mCurrentPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateTimeSpan(final int position) {
        Fragment fragment = getChildFragmentManager().findFragmentByTag(mPagerAdapter.getFragmentTag(R.id.pager, position));
        if (fragment instanceof TrendingListFragment) {
            if (fragment.isAdded()) {
                ((TrendingListFragment) fragment).updateTimeSpan(mTimeSpan);
            }
        }
    }

    public class SinceSpinnerAdapter extends BaseAdapter {
        final String[] timeSpanTextArray = new String[]{"Today", "This Week", "This Month"};

        @Override
        public int getCount() {
            return timeSpanTextArray.length;
        }

        @Override
        public String getItem(int position) {
            return timeSpanTextArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView != null ? convertView :
                    LayoutInflater.from(getActivity()).inflate(R.layout.layout_spinner_item_actionbar, parent, false);

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText("Trends " + getItem(position));
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view = convertView != null ? convertView :
                    LayoutInflater.from(getActivity()).inflate(R.layout.layout_spinner_item_dropdown, parent, false);

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));

            return view;
        }
    }

    public class LanguagesPagerAdapter extends FragmentPagerAdapter {
        List<Language> languagesArray = new ArrayList<>();

        public LanguagesPagerAdapter(FragmentManager fm) {
            super(fm);
            Language language = new Language(AppCst.LANGUAGE_ALL, "");
            languagesArray.addAll(dataManager.getLanguageHelper().getLanguage());
            languagesArray.add(0, language);
        }

        @Override
        public Fragment getItem(int position) {
            return TrendingListFragment.newInstance(getContext(), languagesArray.get(position), mTimeSpan);
        }

        @Override
        public int getCount() {
            return languagesArray.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return languagesArray.get(position).name;
        }

        public String getFragmentTag(int viewPagerId, int fragmentPosition) {
            return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
        }
    }
}
