package com.bookpurple.nocca_admin.mvp;

import com.google.gson.annotations.SerializedName;

/*
 * Written by Gaurav Sharma on 2020-02-01.
 */
public class DeviceDetails {

    @SerializedName("deviceId")
    public String deviceId;

    @SerializedName("deviceName")
    public String deviceName;

    @SerializedName("tokenId")
    public String tokenId;

    @SerializedName("status")
    public int status;
}
