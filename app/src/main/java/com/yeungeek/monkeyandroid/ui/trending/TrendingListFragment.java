package com.yeungeek.monkeyandroid.ui.trending;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.model.Language;
import com.yeungeek.monkeyandroid.data.model.Repo;
import com.yeungeek.monkeyandroid.ui.base.view.BaseLceActivity;
import com.yeungeek.monkeyandroid.ui.base.view.BaseLceFragment;
import com.yeungeek.monkeyandroid.ui.repos.RepoAdapter;
import com.yeungeek.monkeyandroid.util.AppCst;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by yeungeek on 2016/3/30.
 */
public class TrendingListFragment extends BaseLceFragment<PtrClassicFrameLayout, List<Repo>, TrendingMvpView, TrendingPresenter> implements TrendingMvpView {
    @Bind(R.id.id_recycler_view)
    RecyclerView recyclerView;

    @Inject
    TrendingPresenter trendingPresenter;

    Language language;
    String timeSpan;

    private RepoAdapter adapter;

    public static Fragment newInstance(Context context, Language language, String timeSpan) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppCst.EXTRA_LANGUAGE, language);
        bundle.putSerializable(AppCst.EXTRA_TIME_SPAN, timeSpan);
        return Fragment.instantiate(context, TrendingListFragment.class.getName(), bundle);
    }

    @Override
    public TrendingPresenter createPresenter() {
        return trendingPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        language = (Language) getArguments().getSerializable(AppCst.EXTRA_LANGUAGE);
        timeSpan = (String) getArguments().getSerializable(AppCst.EXTRA_TIME_SPAN);
    }

    @Override
    protected void initView() {
        super.initView();
        initRefresh();

        adapter = new RepoAdapter(getActivity(), language);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadData(false);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_pull_refresh_list;
    }


    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return getString(R.string.error_repositories) + "/n" + e.getMessage();
    }

    @Override
    public void setData(List<Repo> data) {
        if (null != data) {
            adapter.addTopAll(data);
        }
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().listTrending(language.path, timeSpan, pullToRefresh);
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.refreshComplete();
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        contentView.refreshComplete();
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();
        if (getActivity() instanceof BaseLceActivity) {
            ((BaseLceActivity) getActivity()).activityComponent().inject(this);
        }
    }

    public void updateTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
        loadData(false);
    }

    private void initRefresh() {
        contentView.setLastUpdateTimeRelateObject(this);
        contentView.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //update page
                loadData(true);
            }
        });

        // the following are default settings
        contentView.setResistance(1.7f);
        contentView.setRatioOfHeaderHeightToRefresh(1.2f);
        contentView.setDurationToClose(200);
        contentView.setDurationToCloseHeader(1000);
        // default is false
        contentView.setPullToRefresh(false);
        // default is true
        contentView.setKeepHeaderWhenRefresh(true);
    }
}
