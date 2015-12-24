package com.yeungeek.monkeyandroid.mvp.common;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by yeungeek on 2015/12/24.
 */
public final class NoOp {
    private NoOp() {

    }

    private static final InvocationHandler DEFAULT_VALUE = new DefaultValueInvocationHandler();

    public static <T> T of(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, DEFAULT_VALUE);
    }

    private static class DefaultValueInvocationHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return Defaults.defaultValue(method.getReturnType());
        }
    }
}
