package com.yeungeek.monkeyandroid.injection.component;

import com.yeungeek.monkeyandroid.injection.PerActivity;
import com.yeungeek.monkeyandroid.injection.module.ActivityModule;
import com.yeungeek.monkeyandroid.ui.LauncherActivity;
import com.yeungeek.monkeyandroid.ui.base.view.BaseToolbarFragment;
import com.yeungeek.monkeyandroid.ui.main.MainActivity;
import com.yeungeek.monkeyandroid.ui.repos.RepoListFragment;
import com.yeungeek.monkeyandroid.ui.signin.SignInDialogFragment;
import com.yeungeek.monkeyandroid.ui.users.UserListFragment;

import dagger.Component;

/**
 * Created by yeungeek on 2016/1/17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(LauncherActivity launcherActivity);

    void inject(SignInDialogFragment signInDialogFragment);
    void inject(BaseToolbarFragment baseToolbarFragment);
    void inject(RepoListFragment repoListFragment);
    void inject(UserListFragment userListFragment);
}
