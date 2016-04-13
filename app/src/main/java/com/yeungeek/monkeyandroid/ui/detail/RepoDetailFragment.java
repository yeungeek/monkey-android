package com.yeungeek.monkeyandroid.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.model.Repo;
import com.yeungeek.monkeyandroid.ui.base.view.BaseFragment;
import com.yeungeek.monkeyandroid.util.AppCst;

import butterknife.Bind;

/**
 * Created by yeungeek on 2016/4/10.
 */
public class RepoDetailFragment extends BaseFragment {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_repo_owner_avatar)
    ImageView mAvatar;
    @Bind(R.id.id_repo_name)
    TextView mRepoName;
    @Bind(R.id.id_repo_stars)
    TextView mStar;
    @Bind(R.id.id_repo_fork)
    TextView mFork;
    @Bind(R.id.id_repo_desc)
    TextView mRepoDesc;

    private ActionBar actionBar;

    private Repo mRepo;
    private String mStarString;
    private String mForkString;

    public static Fragment newInstance(final Context context, final Repo repo) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppCst.EXTRA_REPO, repo);
        return Fragment.instantiate(context, RepoDetailFragment.class.getName(), bundle);
    }

    @Override
    protected void init() {
        super.init();
        mRepo = (Repo) getArguments().getSerializable(AppCst.EXTRA_REPO);
        mStarString = getString(R.string.title_star);
        mForkString = getString(R.string.title_fork);
    }

    @Override
    protected void initViews() {
        super.initViews();
        if (null == mRepo) {
            return;
        }

        if (null != toolbar) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (null != actionBar) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
                actionBar.setTitle(mRepo.getName());
            }
        }

        mRepoName.setText(mRepo.getFull_name());
        mStar.setText(String.format(mStarString, mRepo.getStargazers_count()));
        mFork.setText(String.format(mForkString, mRepo.getForks_count()));
        mRepoDesc.setText(mRepo.getDescription());

        if (null != mRepo.getOwner()) {
            Glide.with(this).load(mRepo.getOwner().getAvatarUrl()).into(mAvatar);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_repo_detail;
    }
}
