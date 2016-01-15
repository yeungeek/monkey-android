package com.yeungeek.monkeyandroid.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by yeungeek on 2016/1/14.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationContext {
}
