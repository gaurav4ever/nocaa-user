package com.bookpurple.nocca_admin.mvp.presenter;

import com.bookpurple.nocca_admin.logger.Logger;
import com.bookpurple.nocca_admin.mvp.DeviceRequestModel;
import com.bookpurple.nocca_admin.mvp.DeviceResponseModel;
import com.bookpurple.nocca_admin.mvp.interactor.DeviceListingInteractor;
import com.bookpurple.nocca_admin.mvp.interfaces.DeviceListingViewPresenterContract;
import com.bookpurple.nocca_admin.util.rx.RxSchedulersAbstractBase;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/*
 * Written by Gaurav Sharma on 2020-02-02.
 */
public class DeviceListingPresenter extends DeviceListingViewPresenterContract.Presenter {

    private static final String TAG = DeviceListingPresenter.class.getSimpleName();

    private DeviceListingInteractor listingInteractor;
    private RxSchedulersAbstractBase rxSchedulers;
    private CompositeDisposable compositeDisposable;

    public DeviceListingPresenter(DeviceListingViewPresenterContract.View view,
                                  DeviceListingInteractor interactor,
                                  RxSchedulersAbstractBase rxSchedulers,
                                  CompositeDisposable compositeDisposable) {
        attachView(view);
        this.listingInteractor = interactor;
        this.rxSchedulers = rxSchedulers;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void getListingData(DeviceRequestModel deviceRequestModel) {
        Disposable disposable = listingInteractor.getDeviceDetailResponse(deviceRequestModel)
                .subscribeOn(rxSchedulers.getIOScheduler())
                .observeOn(rxSchedulers.getMainThreadScheduler())
                .subscribe(deviceResponseModel -> {
                    if (isViewAttached()) {
                        if (isNoContent(deviceResponseModel)) {
                            getView().dataFetchFailure(new Throwable("Data is Null"));
                        } else {
                            getView().onListingDataFetched(deviceResponseModel);
                        }
                    }
                }, throwable -> Logger.logException(TAG, throwable));

        compositeDisposable.add(disposable);
    }

    @Override
    public boolean isNoContent(DeviceResponseModel deviceResponseModel) {
        return null == deviceResponseModel;
    }
}
