package com.bookpurple.nocca_admin.mvp.interactor;

import com.bookpurple.nocca_admin.mvp.NewDeviceRequest;
import com.bookpurple.nocca_admin.network.api.ServiceApi;

import io.reactivex.Observable;


/*
 * Written by Gaurav Sharma on 2020-02-06.
 */
public class DeviceEnrolInteractor {

    private ServiceApi serviceApi;

    public DeviceEnrolInteractor(ServiceApi serviceApi) {
        this.serviceApi = serviceApi;
    }

    public Observable<String> addNewDevice(NewDeviceRequest newDeviceRequest) {
        return serviceApi.addNewDevice(newDeviceRequest);
    }
}
