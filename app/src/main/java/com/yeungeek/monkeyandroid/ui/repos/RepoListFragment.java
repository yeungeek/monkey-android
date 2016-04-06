package com.yeungeek.monkeyandroid.ui.repos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.model.Language;
import com.yeungeek.monkeyandroid.data.model.Repo;
import com.yeungeek.monkeyandroid.data.model.WrapList;
import com.yeungeek.monkeyandroid.ui.base.adapter.EndlessRecyclerOnScrollListener;
import com.yeungeek.monkeyandroid.ui.base.adapter.HeaderAndFooterRecyclerViewAdapter;
import com.yeungeek.monkeyandroid.ui.base.adapter.LoadingFooter;
import com.yeungeek.monkeyandroid.ui.base.adapter.RecyclerViewStateUtils;
import com.yeungeek.monkeyandroid.ui.base.view.BaseLceActivity;
import com.yeungeek.monkeyandroid.ui.base.view.BaseLceFragment;

import javax.inject.Inject;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import timber.log.Timber;

/**
 * Created by yeungeek on 2016/3/30.
 */
public class RepoListFragment extends BaseLceFragment<PtrClassicFrameLayout, WrapList<Repo>, RepoMvpView, RepoPresenter> implements RepoMvpView {
    @Bind(R.id.id_recycler_view)
    RecyclerView recyclerView;

    @Inject
    RepoPresenter repoPresenter;

    Language language;
    private int mPage = 1;
    private int mCount;
    private int mCurrentSize = 0;
    private boolean mLoadMore = false;
    private RepoAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAdapter;

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

        adapter = new RepoAdapter(getActivity());

        mHeaderAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(mHeaderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        initLoadMoreListener();

        loadData(1, false, false);
    }

    private void initLoadMoreListener() {
        recyclerView.addOnScrollListener(mOnScrollListener);
        //RecyclerView clear
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (contentView.isRefreshing()) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_repo_list;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return getString(R.string.error_repositories);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        contentView.refreshComplete();
        RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, 30, LoadingFooter.State.NetWorkError, mFooterClick);
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.refreshComplete();
    }

    @Override
    public void setData(WrapList<Repo> data) {
        mCount = data.getTotal_count();
        if (!mLoadMore) {
            adapter.addTopAll(data.getItems());
            mCurrentSize = data.getItems().size();
        } else {
            adapter.addAll(data.getItems());
            mCurrentSize += data.getItems().size();
        }

        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.Normal);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().listRepos("language:" + language.name, mPage, pullToRefresh);
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();
        if (getActivity() instanceof BaseLceActivity) {
            ((BaseLceActivity) getActivity()).activityComponent().inject(this);
        }
    }


    private void loadData(final int page, final boolean loadMore, final boolean pullToRefresh) {
        mPage = page;
        mLoadMore = loadMore;
        loadData(pullToRefresh);
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
                loadData(1, false, true);
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


    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(recyclerView);
            if (state == LoadingFooter.State.Loading) {
                Timber.d("### is loading now");
                return;
            }

            if (mCurrentSize < mCount) {
                // loading more
                mPage++;
                Timber.d("### onLoadNextPage: %d", mPage);
                RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, 30, LoadingFooter.State.Loading, null);
                loadData(mPage, true, true);
            } else {
                //the end
                mLoadMore = false;
                RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, 30, LoadingFooter.State.TheEnd, null);
            }
        }
    };

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, 30, LoadingFooter.State.Loading, null);
            loadData(mPage, true, true);
        }
    };
}
