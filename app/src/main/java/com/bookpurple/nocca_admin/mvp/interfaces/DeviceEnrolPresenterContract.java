package com.bookpurple.nocca_admin.mvp.interfaces;

import com.bookpurple.nocca_admin.mvp.NewDeviceRequest;
import com.bookpurple.nocca_admin.mvp.core.MvpBasePresenterV2;

/*
 * Written by Gaurav Sharma on 2020-02-06.
 */
public interface DeviceEnrolPresenterContract {

    interface View {

        void onListingDataFetched(String response);

        /**
         * Function to load the data from backend
         */
        void loadData();

        void dataFetchFailure(Throwable error);
    }

    abstract class Presenter extends MvpBasePresenterV2<DeviceEnrolPresenterContract.View> {

        public abstract void addNewDevice(NewDeviceRequest deviceRequest);

    }
}
