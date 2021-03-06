package com.bookpurple.nocca_admin.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bookpurple.nocca_admin.R;
import com.bookpurple.nocca_admin.logger.Logger;
import com.bookpurple.nocca_admin.mvp.DeviceDetails;
import com.bookpurple.nocca_admin.publishsubject.DeviceClickedItem;
import com.bookpurple.nocca_admin.util.rx.RxViewUtil;

import io.reactivex.subjects.PublishSubject;

/*
 * Written by Gaurav Sharma on 2020-02-02.
 */
public class DeviceListingItemViewHolder extends DeviceListingViewHolder<DeviceDetails> {

    private Context context;
    private View view;
    private PublishSubject<DeviceClickedItem> deviceClickedItemPublishSubject;
    private PublishSubject<DeviceClickedItem> deviceStatusClickedItemPublishSubject;

    private TextView deviceTextView;

    public DeviceListingItemViewHolder(Context context,
                                       View itemView,
                                       PublishSubject<DeviceClickedItem> deviceClickedItemPublishSubject,
                                       PublishSubject<DeviceClickedItem> deviceStatusClickedItemPublishSubject) {
        super(itemView);
        this.view = itemView;
        this.context = context;
        this.deviceClickedItemPublishSubject = deviceClickedItemPublishSubject;
        this.deviceStatusClickedItemPublishSubject = deviceStatusClickedItemPublishSubject;
        initViews(itemView);
    }

    private void initViews(View itemView) {
        deviceTextView = itemView.findViewById(R.id.deviceId);
    }

    @Override
    public void bindData(DeviceDetails item, int position) {
        if (null != item.deviceName) {
            deviceTextView.setText(item.deviceName);
        } else {
            deviceTextView.setText(item.deviceId);
        }

        RxViewUtil.click(deviceTextView)
                .subscribe(aVoid -> {
                    final DeviceClickedItem deviceClickedItem = new DeviceClickedItem();
                    deviceClickedItem.deviceId = item.deviceId;
                    deviceClickedItem.status = item.status;
                    deviceClickedItemPublishSubject.onNext(deviceClickedItem);
                }, throwable -> Logger.logException(throwable));
    }
}
