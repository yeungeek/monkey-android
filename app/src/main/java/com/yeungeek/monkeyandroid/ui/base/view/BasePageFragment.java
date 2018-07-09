package com.yeungeek.monkeyandroid.ui.base.view;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.ui.base.adapter.EndlessRecyclerOnScrollListener;
import com.yeungeek.monkeyandroid.ui.base.adapter.LoadingFooter;
import com.yeungeek.monkeyandroid.ui.base.adapter.RecyclerViewStateUtils;
import com.yeungeek.monkeyandroid.util.AppCst;
import com.yeungeek.mvp.common.MvpPresenter;
import com.yeungeek.mvp.common.lce.MvpLceView;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import timber.log.Timber;

/**
 * Created by yeungeek on 2016/4/8.
 */
public abstract class BasePageFragment<M, V extends MvpLceView<M>, P extends MvpPresenter<V>> extends BaseLceFragment<PtrClassicFrameLayout, M, V, P> {
    @Bind(R.id.id_recycler_view)
    protected RecyclerView recyclerView;

    protected int mPage = 1;
    protected int mCount;
    protected int mCurrentSize = 0;
    protected boolean mLoadMore = false;

    protected void loadData(final int page, final boolean loadMore, final boolean pullToRefresh) {
        mPage = page;
        mLoadMore = loadMore;
        loadData(pullToRefresh);
    }

    protected void checkViewState() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.Normal);
    }

    @Override
    protected void initViews() {
        super.initViews();
        initRefresh();

        initAdapter();

        initLoadMoreListener();
        loadData(1, false, false);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_pull_refresh_list;
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
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        contentView.refreshComplete();
        RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, AppCst.DEFAULT_PAGESIZE, LoadingFooter.State.NetWorkError, mFooterClick);
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.refreshComplete();
    }

    public void scrollToTop() {
        recyclerView.smoothScrollToPosition(0);
    }

    protected void resetState() {
        mPage = 1;
        mLoadMore = false;
        mCurrentSize = 0;
    }

    private void initRefresh() {
        contentView.setLastUpdateTimeRelateObject(this);
        contentView.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                Timber.d("### checkCanDoRefresh: %s", getParentFragment());
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

    protected EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {
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
                RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, AppCst.DEFAULT_PAGESIZE, LoadingFooter.State.Loading, null);
                loadData(mPage, true, true);
            } else {
                //the end
                mLoadMore = false;
                RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, AppCst.DEFAULT_PAGESIZE, LoadingFooter.State.TheEnd, null);
            }
        }
    };

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, AppCst.DEFAULT_PAGESIZE, LoadingFooter.State.Loading, null);
            loadData(mPage, true, true);
        }
    };

    protected abstract void initAdapter();
}
