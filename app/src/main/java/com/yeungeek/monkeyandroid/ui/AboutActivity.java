package com.yeungeek.monkeyandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.snilius.aboutit.AboutIt;
import com.snilius.aboutit.meta.LibAndroidSupportLibrary;
import com.snilius.aboutit.meta.LibOkHttp;
import com.snilius.aboutit.meta.LibRetrofit;
import com.snilius.aboutit.meta.LibTimber;
import com.yeungeek.monkeyandroid.BuildConfig;
import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.DataManager;
import com.yeungeek.monkeyandroid.data.meta.LibRxJava;
import com.yeungeek.monkeyandroid.ui.base.view.BaseActivity;
import com.yeungeek.monkeyandroid.util.AppCst;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by yeungeek on 2016/4/11.
 */
public class AboutActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_toolbar_image)
    ImageView avatar;
    @Bind(R.id.adView)
    AdView mAdView;

    @Inject
    DataManager dataManager;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (null != ab) {
            ab.setTitle(R.string.menu_title_about);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        new AboutIt(this).app(R.string.app_name)
                .buildInfo(BuildConfig.DEBUG, BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME)
                .copyright(AppCst.AUTHOR)
                .libLicense(new LibAndroidSupportLibrary())
                .libLicense(new LibRxJava())
                .libLicense(new LibRetrofit())
                .libLicense(new LibOkHttp())
                .libLicense(new LibTimber())
                .description(R.string.title_about_desc)
                .toTextView(R.id.id_about);

        if (null != dataManager) {
            Glide.with(this).load(dataManager.getPreferencesHelper().getUserAvatar()).into(avatar);
        }


        Observable.empty().delay(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                initAds();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    private void initAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();
        activityComponent().inject(this);
    }
}
