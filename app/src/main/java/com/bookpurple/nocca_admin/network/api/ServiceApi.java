package com.bookpurple.nocca_admin.network.api;


import com.bookpurple.nocca_admin.mvp.DeviceRequestModel;
import com.bookpurple.nocca_admin.mvp.DeviceResponseModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/*
 * Retrofit API interface to hold all the URLs related to Service API
 * Written by gauravsharma on 2019-05-19.
 */
public interface ServiceApi {

    @POST("/getDevices")
    Observable<DeviceResponseModel> getDeviceDetails(@Body DeviceRequestModel deviceRequestModel);
}
