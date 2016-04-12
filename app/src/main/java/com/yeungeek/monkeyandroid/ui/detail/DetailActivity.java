package com.yeungeek.monkeyandroid.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.model.Repo;
import com.yeungeek.monkeyandroid.data.model.User;
import com.yeungeek.monkeyandroid.ui.base.view.BaseActivity;

import java.io.Serializable;

/**
 * Created by yeungeek on 2016/4/10.
 */
public class DetailActivity extends BaseActivity {
    private static final String EXTRA_DETAIL = "EXTRA_DETAIL";

    private Serializable serializable;

    public static Intent getStartIntent(Context context, Serializable serializable) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_DETAIL, serializable);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        serializable = getIntent().getSerializableExtra(EXTRA_DETAIL);
        initViews();
    }

    private void initViews() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        if (null != serializable) {
            if (serializable instanceof Repo) {
                transaction.replace(R.id.id_detail, RepoDetailFragment.newInstance(this, (Repo) serializable));
            } else if (serializable instanceof User) {
                transaction.replace(R.id.id_detail, UserDetailFragment.newInstance());
            }
        }

        transaction.commit();
    }
}
