package com.yeungeek.monkeyandroid.ui.repos;

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
public class RepoListFragment extends BaseLceFragment<PtrClassicFrameLayout, List<Repo>, RepoMvpView, RepoPresenter> implements RepoMvpView {
    @Bind(R.id.id_recycler_view)
    RecyclerView recyclerView;

    @Inject
    RepoPresenter repoPresenter;

    Language language;
    private RepoAdapter adapter;

    public static Fragment newInstance(Context context, Language language) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("extra_language", language);
        return Fragment.instantiate(context, RepoListFragment.class.getName(), bundle);
    }

    @Override
    public RepoPresenter createPresenter() {
        return repoPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        language = (Language) getArguments().getSerializable("extra_language");
    }

    @Override
    protected void initView() {
        super.initView();
        initRefresh();

        adapter = new RepoAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadData(false);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_repo_list;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        contentView.refreshComplete();
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.refreshComplete();
    }

    @Override
    public void setData(List<Repo> data) {
        adapter.setDatas(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().listRepos("yeungeek",pullToRefresh);
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();
        if(getActivity() instanceof BaseLceActivity){
            ((BaseLceActivity)getActivity()).activityComponent().inject(this);
        }
    }

    private void initRefresh(){
        contentView.setLastUpdateTimeRelateObject(this);
        contentView.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //update
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
        contentView.postDelayed(new Runnable() {
            @Override
            public void run() {
                contentView.autoRefresh();
            }
        }, 100);
    }
}
