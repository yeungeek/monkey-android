package com.yeungeek.monkeyandroid.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.model.User;
import com.yeungeek.monkeyandroid.data.remote.GithubApi;
import com.yeungeek.monkeyandroid.rxbus.RxBus;
import com.yeungeek.monkeyandroid.rxbus.event.SignInEvent;
import com.yeungeek.monkeyandroid.ui.base.view.BaseLceActivity;
import com.yeungeek.monkeyandroid.ui.signin.SignInDialogFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class MainActivity extends BaseLceActivity<View, User, MainMvpView, MainPresenter> implements MainMvpView {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.draw_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Nullable
    @Bind(R.id.id_drawer_header_avatar)
    ImageView mAvatarView;
    @Nullable
    @Bind(R.id.id_drawer_header_name)
    TextView mNameView;
    @Nullable
    @Bind(R.id.id_drawer_header_email)
    TextView mEmailView;

    @Inject
    RxBus rxBus;
    @Inject
    MainPresenter mainPresenter;

    private CompositeSubscription mSubscriptions;

    private ActionBar actionBar;
    private String code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        if (null != navigationView) {
            setupDrawerContent(navigationView);
            //default
            navigationView.setCheckedItem(R.id.menu_users);
            View headerView = navigationView.getHeaderView(0);
            mAvatarView = ButterKnife.findById(headerView, R.id.id_drawer_header_avatar);
            mNameView = ButterKnife.findById(headerView, R.id.id_drawer_header_name);
            mEmailView = ButterKnife.findById(headerView, R.id.id_drawer_header_email);

            mAvatarView.setOnClickListener(this);
            mNameView.setOnClickListener(this);

        }
        selectFragment(R.id.menu_users);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();
        activityComponent().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSubscriptions = new CompositeSubscription();
        mSubscriptions.add(rxBus.toObservable()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Timber.d("### receive event: %s", o);
                        if (o instanceof SignInEvent) {
                            SignInEvent event = (SignInEvent) o;
                            if (null != event.getUri().getQueryParameter("code")) {
                                code = event.getUri().getQueryParameter("code");
                                Timber.d("### receive event code: %s", code);
                                //getAccessToken
                                loadData(true);
                            }
                        }
                    }
                }));
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSubscriptions.unsubscribe();
    }

    private void updateUser(User data) {
        if (null == data) {
            return;
        }

        Glide.with(this).load(data.getAvatar_url()).into(mAvatarView);

        mNameView.setText(data.getLogin());
        mEmailView.setText(data.getEmail());
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
                selectFragment(item.getItemId());
                return true;
            }
        });
    }

    private void selectFragment(final int fragmentId) {

    }

    private void openSignInBrowser() {
        SignInDialogFragment fragment = SignInDialogFragment.newInstance(GithubApi.AUTH_URL);
        fragment.show(getSupportFragmentManager(), "SignIn");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.id_drawer_header_avatar:
            case R.id.id_drawer_header_name:
                openSignInBrowser();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return mainPresenter;
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        Timber.d("### login start");
    }

    @Override
    public void showContent() {
        Timber.d("### login success");
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        Timber.e(e,"### login error");
    }

    @Override
    public void setData(User data) {
        Timber.d("### login success user info: %s", data.getLogin());
        updateUser(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        if (TextUtils.isEmpty(code)) {
            return;
        }

        mainPresenter.getAccessToken(code);
    }
}
