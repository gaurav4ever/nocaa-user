package com.bookpurple.nocca_admin.mvp.core;

import java.lang.ref.WeakReference;

/*
 * Written by Gaurav Sharma on 2020-02-02.
 */
public class MvpBasePresenterV2<V> {

    private WeakReference<V> weakRef;

    protected void attachView(V view) {
        weakRef = new WeakReference<>(view);
    }

    protected boolean isViewAttached() {
        return weakRef != null && weakRef.get() != null;
    }

    protected V getView() {
        return weakRef.get();
    }
}

