package com.yeungeek.monkeyandroid.ui.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.model.Repo;
import com.yeungeek.monkeyandroid.ui.base.view.BaseLceActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import timber.log.Timber;

/**
 * Created by yeungeek on 2016/1/10.
 */
public class SampleActivity extends BaseLceActivity<SwipeRefreshLayout, List<Repo>, ReposView, ReposPresenter>
        implements ReposView, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private ReposAdapter adapter;

    @Inject
    ReposPresenter reposPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);

        contentView.setOnRefreshListener(this);

        adapter = new ReposAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData(false);
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();
        activityComponent().inject(this);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        contentView.setRefreshing(false);
        Timber.e(e, "showError");
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @NonNull
    @Override
    public ReposPresenter createPresenter() {
        return reposPresenter;
    }

    @Override
    public void setData(List<Repo> data) {
        adapter.setDatas(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().listRepos("yeungeek", pullToRefresh);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }
}
