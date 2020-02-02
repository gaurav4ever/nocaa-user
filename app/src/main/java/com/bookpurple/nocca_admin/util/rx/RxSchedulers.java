package com.bookpurple.nocca_admin.util.rx;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*
 * Written by Gaurav Sharma on 2019-05-19.
 */
public class RxSchedulers extends RxSchedulersAbstractBase {

    public RxSchedulers() {
    }

    public Scheduler getMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    public Scheduler getIOScheduler() {
        return Schedulers.io();
    }

    public Scheduler getComputationScheduler() {
        return Schedulers.computation();
    }
}
