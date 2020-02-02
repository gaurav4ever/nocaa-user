package com.bookpurple.nocca_admin.mvp.interfaces;

import com.bookpurple.nocca_admin.mvp.DeviceRequestModel;
import com.bookpurple.nocca_admin.mvp.DeviceResponseModel;
import com.bookpurple.nocca_admin.mvp.core.MvpBasePresenterV2;

/*
 * Written by Gaurav Sharma on 2020-02-02.
 */
public interface DeviceListingViewPresenterContract {

    interface View {

        void onListingDataFetched(DeviceResponseModel deviceResponseModel);

        /**
         * Function to load the data from backend
         */
        void loadData();

        void dataFetchFailure(Throwable error);
    }

    abstract class Presenter extends MvpBasePresenterV2<View> {

        public abstract void getListingData(DeviceRequestModel deviceRequestModel);

        public abstract boolean isNoContent(DeviceResponseModel deviceResponseModel);

    }
}
