package com.bookpurple.nocca_admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.bookpurple.nocca_admin.R;
import com.bookpurple.nocca_admin.adapter.DeviceListingAdapter;
import com.bookpurple.nocca_admin.constant.Constant;
import com.bookpurple.nocca_admin.dagger.component.ModuleComponent;
import com.bookpurple.nocca_admin.dagger.provider.ComponentProvider;
import com.bookpurple.nocca_admin.logger.Logger;
import com.bookpurple.nocca_admin.mvp.DeviceRequestModel;
import com.bookpurple.nocca_admin.mvp.DeviceResponseModel;
import com.bookpurple.nocca_admin.mvp.interactor.DeviceListingInteractor;
import com.bookpurple.nocca_admin.mvp.interfaces.DeviceListingViewPresenterContract;
import com.bookpurple.nocca_admin.mvp.presenter.DeviceListingPresenter;
import com.bookpurple.nocca_admin.publishsubject.DeviceClickedItem;
import com.bookpurple.nocca_admin.util.rx.RxSchedulersAbstractBase;
import com.bookpurple.nocca_admin.util.rx.RxUtil;
import com.facebook.shimmer.ShimmerFrameLayout;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/*
 * Written by Gaurav Sharma on 2020-02-02.
 */
public class DeviceListingActivity extends AppCompatActivity implements DeviceListingViewPresenterContract.View {

    private static final String TAG = DeviceListingActivity.class.getSimpleName();
    @Inject
    public RxUtil rxUtil;
    @Inject
    public RxSchedulersAbstractBase rxSchedulers;
    @Inject
    public DeviceListingInteractor interactor;

    AlertDialog.Builder builder;

    // Dagger related variables
    protected ModuleComponent component;
    // Rx Related Variables
    private CompositeDisposable lifecycle;
    private DeviceListingPresenter deviceListingPresenter;

    private DeviceRequestModel deviceRequestModel;
    private RecyclerView listingRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DeviceListingAdapter adapter;
    private RelativeLayout contentLayout;
    private ShimmerFrameLayout listingShimmerLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devicelistingactivity);
        injectDependencies();
        handleIntent();
        initView();
        loadData();
    }

    private void handleIntent() {
        if (null != getIntent() && null != getIntent().getExtras()) {
            Bundle bundle = getIntent().getExtras();
            if (!bundle.containsKey(Constant.ParcelConstant.DEVICE_LISTING_REQUEST_MODEL)) {
                finish();
            } else {
                deviceRequestModel = interactor
                        .createDeviceRequestModel(bundle.getString(Constant.ParcelConstant.DEVICE_LISTING_REQUEST_MODEL));
            }
        }
    }

    private void injectDependencies() {

        component = ComponentProvider.getComponent();
        component.inject(this);
        lifecycle = new CompositeDisposable();

        // create presenter
        deviceListingPresenter = new DeviceListingPresenter(this,
                interactor,
                rxSchedulers,
                lifecycle);
    }

    private void initView() {
        listingRecyclerView = findViewById(R.id.device_listing);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        listingRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new DeviceListingAdapter(this, lifecycle);
        listingRecyclerView.setAdapter(adapter);

        listingShimmerLayout = findViewById(R.id.listing_shimmer_layout);
        registerToClickObservables();
    }

    private void registerToClickObservables() {
        Disposable deviceItemClickSubscription = adapter.getDeviceClickedItemPublishSubject()
                .subscribe(deviceClickedItem -> {
                    // init payment
                    initPayment(deviceClickedItem);
                }, throwable -> Logger.logException(TAG, throwable));

        lifecycle.add(deviceItemClickSubscription);
    }

    private void initPayment(DeviceClickedItem deviceClickedItem) {
        if (deviceClickedItem.status == 0) {
            builder.setMessage("Your device has been disabled")
            .setTitle("You cannot initiate payment.")
            .create();
        } else {
            // start payment activity
            Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onListingDataFetched(DeviceResponseModel deviceResponseModel) {
        stopShimmerAnimation();
        if (deviceResponseModel != null) {
            adapter.setData(deviceResponseModel.deviceDetails);
        }
    }

    @Override
    public void loadData() {
        startShimmerAnimation();
        deviceListingPresenter.getListingData(deviceRequestModel);
    }

    @Override
    public void dataFetchFailure(Throwable error) {
        Logger.log(TAG, error);
    }

    private void startShimmerAnimation() {
        //contentLayout.setVisibility(View.GONE);
        listingShimmerLayout.startShimmer();
    }

    private void stopShimmerAnimation() {
        //contentLayout.setVisibility(View.VISIBLE);
        listingShimmerLayout.setVisibility(View.GONE);
        listingShimmerLayout.stopShimmer();
    }
}
