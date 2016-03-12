package com.yeungeek.monkeyandroid.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.remote.GithubApi;
import com.yeungeek.monkeyandroid.rxbus.RxBus;
import com.yeungeek.monkeyandroid.rxbus.event.SignInEvent;
import com.yeungeek.monkeyandroid.ui.base.view.BaseActivity;
import com.yeungeek.monkeyandroid.ui.signin.SignInDialogFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class MainActivity extends BaseActivity {
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

    @Inject
    RxBus rxBus;
    private CompositeSubscription mSubscriptions;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

            mAvatarView.setOnClickListener(this);
            mNameView.setOnClickListener(this);

        }
        selectFragment(R.id.menu_users);
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
                                String code = event.getUri().getQueryParameter("code");
                                Timber.d("### receive event code: %s", code);
                                //getAccessToken

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
}
