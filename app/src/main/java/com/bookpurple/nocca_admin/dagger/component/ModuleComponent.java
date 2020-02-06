package com.bookpurple.nocca_admin.dagger.component;


import com.bookpurple.nocca_admin.activity.DeviceListingActivity;
import com.bookpurple.nocca_admin.activity.EnrolDeviceActivity;

/*
 * Written by Gaurav Sharma on 2020-02-01.
 */
public interface ModuleComponent {

    void inject(DeviceListingActivity deviceListingActivity);

    void inject(EnrolDeviceActivity enrolDeviceActivity);

}
