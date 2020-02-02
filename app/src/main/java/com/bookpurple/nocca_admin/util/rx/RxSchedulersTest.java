package com.bookpurple.nocca_admin.util.rx;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/*
 * Written by Gaurav Sharma on 2019-05-19.
 */
public class RxSchedulersTest extends RxSchedulersAbstractBase {

    public RxSchedulersTest() {
    }

    public Scheduler getMainThreadScheduler() {
        return Schedulers.trampoline();
    }

    public Scheduler getIOScheduler() {
        return Schedulers.trampoline();
    }

    public Scheduler getComputationScheduler() {
        return Schedulers.trampoline();
    }
}
