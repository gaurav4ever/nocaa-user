package com.bookpurple.nocca_admin.mvp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
 * Written by Gaurav Sharma on 2020-02-01.
 */
public class DeviceResponseModel {

    @SerializedName("deviceDetails")
    public List<DeviceDetails> deviceDetails;
}
