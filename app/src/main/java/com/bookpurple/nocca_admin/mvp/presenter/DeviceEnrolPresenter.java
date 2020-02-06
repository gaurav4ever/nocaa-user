package com.bookpurple.nocca_admin.mvp.presenter;

import com.bookpurple.nocca_admin.logger.Logger;
import com.bookpurple.nocca_admin.mvp.NewDeviceRequest;
import com.bookpurple.nocca_admin.mvp.interactor.DeviceEnrolInteractor;
import com.bookpurple.nocca_admin.mvp.interfaces.DeviceEnrolPresenterContract;
import com.bookpurple.nocca_admin.util.rx.RxSchedulersAbstractBase;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/*
 * Written by Gaurav Sharma on 2020-02-06.
 */
public class DeviceEnrolPresenter extends DeviceEnrolPresenterContract.Presenter {

    private static final String TAG = DeviceEnrolPresenter.class.getSimpleName();

    private DeviceEnrolInteractor deviceEnrolInteractor;
    private RxSchedulersAbstractBase rxSchedulers;
    private CompositeDisposable compositeDisposable;

    public DeviceEnrolPresenter(DeviceEnrolPresenterContract.View view,
                                DeviceEnrolInteractor interactor,
                                RxSchedulersAbstractBase rxSchedulers,
                                CompositeDisposable compositeDisposable) {
        attachView(view);
        this.deviceEnrolInteractor = interactor;
        this.rxSchedulers = rxSchedulers;
        this.compositeDisposable = compositeDisposable;

    }

    @Override
    public void addNewDevice(NewDeviceRequest deviceRequest) {
        Disposable disposable = deviceEnrolInteractor.addNewDevice(deviceRequest)
                .subscribeOn(rxSchedulers.getIOScheduler())
                .observeOn(rxSchedulers.getMainThreadScheduler())
                .subscribe(response -> {
                    Logger.log(TAG, response);
                    if (null != response) {
                        if (null != getView()) {
                            getView().onListingDataFetched(response);
                        } else {
                            getView().dataFetchFailure(new Throwable("Could not save the device details"));
                        }
                    }
                }, throwable -> Logger.logException(TAG, throwable));
        compositeDisposable.add(disposable);
    }
}
