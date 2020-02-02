package com.bookpurple.nocca_admin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bookpurple.nocca_admin.R;
import com.bookpurple.nocca_admin.adapter.viewholder.DeviceListingItemViewHolder;
import com.bookpurple.nocca_admin.adapter.viewholder.DeviceListingViewHolder;
import com.bookpurple.nocca_admin.mvp.DeviceDetails;
import com.bookpurple.nocca_admin.publishsubject.DeviceClickedItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

/*
 * Written by Gaurav Sharma on 2020-02-02.
 */
public class DeviceListingAdapter extends RecyclerView.Adapter<DeviceListingViewHolder> {

    private Context context;
    private CompositeDisposable compositeDisposable;
    private List<DeviceDetails> deviceDetails;
    private LayoutInflater layoutInflater;
    private PublishSubject<DeviceClickedItem> deviceClickedItemPublishSubject;
    private PublishSubject<DeviceClickedItem> deviceStatusClickedItemPublishSubject;

    public DeviceListingAdapter(Context context,
                                CompositeDisposable lifecycle) {
        this.context = context;
        this.deviceDetails = new ArrayList<>();
        this.compositeDisposable = lifecycle;
        this.deviceDetails = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
        this.deviceClickedItemPublishSubject = PublishSubject.create();
        this.deviceStatusClickedItemPublishSubject = PublishSubject.create();
    }

    public void setData(List<DeviceDetails> deviceDetails) {
        this.deviceDetails.clear();
        this.deviceDetails.addAll(deviceDetails);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeviceListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = layoutInflater.inflate(R.layout.listing_device_item, parent, false);
        return new DeviceListingItemViewHolder(context,
                itemView,
                deviceClickedItemPublishSubject,
                deviceStatusClickedItemPublishSubject);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceListingViewHolder holder, int position) {
        holder.bindData(deviceDetails.get(position), position);
    }

    @Override
    public int getItemCount() {
        return deviceDetails.size();
    }

    public PublishSubject<DeviceClickedItem> getDeviceClickedItemPublishSubject () {
        return this.deviceClickedItemPublishSubject;
    }

    public PublishSubject<DeviceClickedItem> getDeviceStatusClickedItemPublishSubject () {
        return this.deviceStatusClickedItemPublishSubject;
    }
}
