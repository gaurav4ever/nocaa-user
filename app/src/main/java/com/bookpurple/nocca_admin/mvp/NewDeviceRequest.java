package com.bookpurple.nocca_admin.mvp;

import com.google.gson.annotations.SerializedName;

/*
 * Written by Gaurav Sharma on 2020-02-06.
 */
public class NewDeviceRequest {

    @SerializedName("userId")
    public String userEmail;

    @SerializedName("userName")
    public String userName;

    @SerializedName("panNumber")
    public String panNumber;

    @SerializedName("merchantFlag")
    public boolean merchantFlag;

    @SerializedName("deviceName")
    public String deviceName;

    @SerializedName("accountIdDeviceId")
    public String deviceId;
}
