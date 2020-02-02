package com.bookpurple.nocca_admin.mvp.interactor;

import com.bookpurple.nocca_admin.mvp.DeviceRequestModel;
import com.bookpurple.nocca_admin.mvp.DeviceResponseModel;
import com.bookpurple.nocca_admin.network.api.ServiceApi;

import io.reactivex.Observable;


/*
 * Written by Gaurav Sharma on 2020-02-02.
 */
public class DeviceListingInteractor {

    private ServiceApi serviceApi;

    public DeviceListingInteractor(ServiceApi serviceApi) {
        this.serviceApi = serviceApi;
    }

    public Observable<DeviceResponseModel> getDeviceDetailResponse(DeviceRequestModel deviceRequestModel) {
        return serviceApi.getDeviceDetails(deviceRequestModel);
    }

    public DeviceRequestModel createDeviceRequestModel(String panNumber) {
        DeviceRequestModel deviceRequestModel = new DeviceRequestModel();
        deviceRequestModel.panNumber = panNumber;
        return deviceRequestModel;
    }
}
