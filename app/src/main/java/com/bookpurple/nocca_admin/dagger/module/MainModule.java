package com.bookpurple.nocca_admin.dagger.module;

import com.bookpurple.nocca_admin.mvp.interactor.DeviceListingInteractor;
import com.bookpurple.nocca_admin.network.api.ServiceApi;
import com.bookpurple.nocca_admin.util.rx.RxSchedulers;
import com.bookpurple.nocca_admin.util.rx.RxSchedulersAbstractBase;
import com.bookpurple.nocca_admin.util.rx.RxUtil;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/*
 * Written by Gaurav Sharma on 2020-02-01.
 */
@Module
public class MainModule {

    @Provides
    @Singleton
    public DeviceListingInteractor getDeviceDetailsListingInteractor(@Named("serviceApi") ServiceApi serviceApi) {
        return new DeviceListingInteractor(serviceApi);
    }

    @Provides
    @Singleton
    public RxSchedulersAbstractBase provideRxSchedulers() {
        return new RxSchedulers();
    }

    @Provides
    @Singleton
    public RxUtil provideRxUtil() {
        return new RxUtil();
    }
}
