package com.bookpurple.nocca_admin.util.rx;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.SingleTransformer;

/*
 * Written by Gaurav Sharma on 2019-05-19.
 */
public abstract class RxSchedulersAbstractBase {

    public RxSchedulersAbstractBase() {
    }

    public abstract Scheduler getMainThreadScheduler();

    public abstract Scheduler getIOScheduler();

    public abstract Scheduler getComputationScheduler();

    public <T> ObservableTransformer<T, T> getIOToMainTransformer() {
        return (objectObservable) -> {
            return objectObservable.subscribeOn(this.getIOScheduler()).observeOn(this.getMainThreadScheduler());
        };
    }

    public <T> SingleTransformer<T, T> getIOToMainTransformerSingle() {
        return (objectObservable) -> {
            return objectObservable.subscribeOn(this.getIOScheduler()).observeOn(this.getMainThreadScheduler());
        };
    }

    public <T> ObservableTransformer<T, T> getComputationToMainTransformer() {
        return (objectObservable) -> {
            return objectObservable.subscribeOn(this.getComputationScheduler()).observeOn(this.getMainThreadScheduler());
        };
    }

    public <T> SingleTransformer<T, T> getComputationToMainTransformerSingle() {
        return (objectObservable) -> {
            return objectObservable.subscribeOn(this.getComputationScheduler()).observeOn(this.getMainThreadScheduler());
        };
    }
}
