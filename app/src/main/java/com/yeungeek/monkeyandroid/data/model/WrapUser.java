package com.yeungeek.monkeyandroid.data.model;

/**
 * Created by yeungeek on 2016/4/18.
 */
public class WrapUser extends User {
    //check if is followed
    private boolean isFollowed;

    public boolean isFollowed() {
        return isFollowed;
    }

    public WrapUser setFollowed(boolean followed) {
        isFollowed = followed;
        return this;
    }
}
