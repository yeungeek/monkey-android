package com.yeungeek.monkeyandroid.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.model.Pair;
import com.yeungeek.monkeyandroid.data.model.User;
import com.yeungeek.monkeyandroid.data.model.WrapUser;
import com.yeungeek.monkeyandroid.ui.base.view.BaseActivity;
import com.yeungeek.monkeyandroid.ui.base.view.BaseLceFragment;
import com.yeungeek.monkeyandroid.util.AppCst;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by yeungeek on 2016/4/10.
 */
public class UserDetailFragment extends BaseLceFragment<View, WrapUser, UserDetailMvpView, UserDetailPresenter> implements UserDetailMvpView {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager viewPager;

    @Bind(R.id.fab)
    FloatingActionButton mUserFollowing;

    @Bind(R.id.id_user_avatar)
    ImageView mUserAvatar;
    @Bind(R.id.id_user_name)
    TextView mUserName;
    @Bind(R.id.id_user_location)
    TextView mUserLocation;
    @Bind(R.id.id_user_company)
    TextView mUserCompany;
    @Bind(R.id.id_user_blog)
    TextView mUserBlog;
    @Bind(R.id.id_user_create_time)
    TextView mCreateTime;

    @Inject
    UserDetailPresenter userDetailPresenter;

    private ActionBar actionBar;
    private User mUser;
    private UserPagerAdapter mUserPagerAdapter;
    private boolean mCurrentFollowing;

    private String mFollowing;
    private String mFollowers;

    public static Fragment newInstance(final Context context, final User user) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppCst.EXTRA_USER, user);
        return Fragment.instantiate(context, UserDetailFragment.class.getName(), bundle);
    }

    @Override
    protected void init() {
        super.init();
        mUser = (User) getArguments().getSerializable(AppCst.EXTRA_USER);
        mFollowing = getString(R.string.title_following);
        mFollowers = getString(R.string.title_followers);
    }

    @Override
    protected void initViews() {
        super.initViews();
        if (null == mUser) {
            return;
        }

        if (null != toolbar) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (null != actionBar) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
                actionBar.setTitle(mUser.getLogin());
            }
        }

        loadData(false);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_user_detail;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return e.getMessage();
    }

    @Override
    public UserDetailPresenter createPresenter() {
        return userDetailPresenter;
    }

    @Override
    public void setData(WrapUser data) {
        updateUser(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().getSingleUser(mUser.getLogin(), pullToRefresh);
    }

    @Override
    public void followStatus(boolean isFollowing) {
        mCurrentFollowing = isFollowing;
        checkFollowing(isFollowing);
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        if (mCurrentFollowing) {
            getPresenter().unfollowUser(mUser.getLogin());
        } else {
            getPresenter().followUser(mUser.getLogin());
        }
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).activityComponent().inject(this);
        }
    }

    private void updateUser(final WrapUser user) {
        if (null == user) {
            return;
        }

        mCurrentFollowing = user.isFollowed();
        checkFollowing(mCurrentFollowing);

        mUserName.setText(user.getName());
        mUserLocation.setText(user.getLocation());
        mUserCompany.setText(user.getCompany());
        mUserBlog.setText(user.getBlog());
        mCreateTime.setText(user.getCreated_at());

        Glide.with(this).load(user.getAvatarUrl()).into(mUserAvatar);

        mUserPagerAdapter = new UserPagerAdapter(getChildFragmentManager(), new SparseArray<Pair<String, WrapUser>>() {
            {
                put(0, new Pair<>(AppCst.TITLE_FOLLOWERS, user));
                put(1, new Pair<>(AppCst.TITLE_FOLLOWING, user));
            }
        });
        viewPager.setAdapter(mUserPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText(String.format(mFollowers, user.getFollowers()));
        tabLayout.getTabAt(1).setText(String.format(mFollowing, user.getFollowing()));
    }

    private void checkFollowing(final boolean following) {
        if (following) {
            mUserFollowing.setImageResource(R.drawable.ic_person);
        } else {
            mUserFollowing.setImageResource(R.drawable.ic_person_add);
        }
    }

    public class UserPagerAdapter extends FragmentPagerAdapter {
        SparseArray<Pair<String, WrapUser>> follows = new SparseArray<>();

        public UserPagerAdapter(FragmentManager fm, SparseArray<Pair<String, WrapUser>> arrays) {
            super(fm);
            this.follows = arrays;
        }

        @Override
        public Fragment getItem(int position) {
            return FollowUserFragment.newInstance(getActivity(), follows.get(position));
        }

        @Override
        public int getCount() {
            return follows.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return follows.get(position).first;
        }

        public String getFragmentTag(int viewPagerId, int fragmentPosition) {
            return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
        }
    }
}
