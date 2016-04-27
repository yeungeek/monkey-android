package com.yeungeek.monkeyandroid;

import com.yeungeek.monkeyandroid.data.DataManager;
import com.yeungeek.monkeyandroid.data.local.DatabaseHelper;
import com.yeungeek.monkeyandroid.data.local.LanguageHelper;
import com.yeungeek.monkeyandroid.data.local.PreferencesHelper;
import com.yeungeek.monkeyandroid.data.remote.GithubApi;
import com.yeungeek.monkeyandroid.data.remote.SimpleApi;
import com.yeungeek.monkeyandroid.rxbus.RxBus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;

/**
 * Created by yeungeek on 2016/4/27.
 */
@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {
    @Mock
    GithubApi mGithubApi;
    @Mock
    SimpleApi mSimpleApi;
    @Mock
    RxBus mRxBus;
    @Mock
    PreferencesHelper mPreferencesHelper;
    @Mock
    DatabaseHelper mDatabaseHelper;
    @Mock
    LanguageHelper mLanguageHelper;

    DataManager mDataManager;
    MonkeyApplication mApplication;

    @Before
    public void setUp() {
        mApplication = Mockito.mock(MonkeyApplication.class);
        mDataManager = new DataManager(mApplication, mGithubApi, mSimpleApi, mRxBus, mPreferencesHelper, mDatabaseHelper, mLanguageHelper);
    }

    @Test
    public void clearTables() {
        Mockito.doReturn(Observable.empty())
                .when(mDatabaseHelper)
                .clearTables();
    }
}
