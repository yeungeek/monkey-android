package com.yeungeek.monkeyandroid.ui.detail;

import android.text.TextUtils;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import com.yeungeek.monkeyandroid.data.DataManager;
import com.yeungeek.monkeyandroid.data.model.Repo;
import com.yeungeek.monkeyandroid.rxbus.event.BusEvent;
import com.yeungeek.monkeyandroid.ui.base.presenter.MvpLceRxPresenter;
import com.yeungeek.monkeyandroid.util.HttpStatus;
import com.yeungeek.mvp.common.MvpPresenter;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by yeungeek on 2016/4/13.
 */
public class RepoDetailPresenter extends MvpLceRxPresenter<RepoDetailMvpView, String> implements MvpPresenter<RepoDetailMvpView> {
    private final DataManager dataManager;
    private String cssFile = "file:///android_asset/markdown_css_themes/classic.css";

    private Subscriber<Response<Void>> mCheckStar;
    private StarSubscriber mStar;

    @Inject
    public RepoDetailPresenter(final DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void getReadme(final String owner, final String repo, final boolean pullToRefresh) {
        Timber.d("### getReadme owner:%s, repo: %s", owner, repo);
        subscribe(dataManager.getReadme(owner, repo, cssFile), pullToRefresh);
    }

    public void checkIfStaring(final Repo repo) {
        Timber.d("### checkIfStaring owner:%s, repo: %s", repo.getOwner().getLogin(), repo.getName());
        dataManager.checkIfStaring(repo.getOwner().getLogin(), repo.getName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mCheckStar = new Subscriber<Response<Void>>() {
                    @Override
                    public void onCompleted() {
                        if (isViewAttached()) {
                            getView().showContent();
                        }
                        unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            getView().showError(e, true);
                        }
                        unsubscribe();
                    }

                    @Override
                    public void onNext(Response<Void> response) {
                        if (null != response && response.code() == HttpStatus.HTTP_NO_CONTENT) {
                            getView().starStatus(true);
                        } else {
                            getView().starStatus(false);
                        }
                    }
                });
    }

    public void starRepo(final Repo repo) {
        Timber.d("### starRepo owner:%s, repo: %s", repo.getOwner().getLogin(), repo.getName());
        mStar = new StarSubscriber(true);
        dataManager.starRepo(repo.getOwner().getLogin(), repo.getName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mStar);
    }

    public void unstarRepo(final Repo repo) {
        Timber.d("### unstarRepo owner:%s, repo: %s", repo.getOwner().getLogin(), repo.getName());
        mStar = new StarSubscriber(false);
        dataManager.unstarRepo(repo.getOwner().getLogin(), repo.getName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mStar);
    }

    public boolean isLogined() {
        return !TextUtils.isEmpty(dataManager.getPreferencesHelper().getAccessToken());
    }

    public boolean checkLogin() {
        if (TextUtils.isEmpty(dataManager.getPreferencesHelper().getAccessToken())) {
//            dataManager.getRxBus().send(new BusEvent.AuthenticationError());
            getView().notLogined();
            return false;
        }

        return true;
    }

    @Override
    protected void unsubscribe() {
        super.unsubscribe();
        if (null != mCheckStar && mCheckStar.isUnsubscribed()) {
            mCheckStar.unsubscribe();
        }

        if (null != mStar && mStar.isUnsubscribed()) {
            mStar.unsubscribe();
        }

        mStar = null;
        mCheckStar = null;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        unsubscribe();
    }

    @RxLogSubscriber
    private class StarSubscriber extends Subscriber<Response<Void>> {
        private boolean isStar;

        public StarSubscriber(final boolean isStar) {
            this.isStar = isStar;
        }

        @Override
        public void onCompleted() {
            if (isViewAttached()) {
                getView().showContent();
            }
            unsubscribe();
        }

        @Override
        public void onError(Throwable e) {
            if (isViewAttached()) {
                getView().showError(e, true);
            }
            unsubscribe();
        }

        @Override
        public void onNext(Response<Void> response) {
            if (null != response && response.code() == HttpStatus.HTTP_NO_CONTENT) {
                if (isStar) {
                    getView().starStatus(true);
                } else {
                    getView().starStatus(false);
                }
            } else {
                if (isStar) {
                    getView().starStatus(false);
                } else {
                    getView().starStatus(true);
                }
            }
        }
    }
}
