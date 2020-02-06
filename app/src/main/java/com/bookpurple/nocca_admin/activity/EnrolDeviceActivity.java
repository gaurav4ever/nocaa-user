package com.bookpurple.nocca_admin.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bookpurple.nocca_admin.R;
import com.bookpurple.nocca_admin.dagger.component.ModuleComponent;
import com.bookpurple.nocca_admin.dagger.provider.ComponentProvider;
import com.bookpurple.nocca_admin.logger.Logger;
import com.bookpurple.nocca_admin.mvp.DeviceRequestModel;
import com.bookpurple.nocca_admin.mvp.DeviceResponseModel;
import com.bookpurple.nocca_admin.mvp.NewDeviceRequest;
import com.bookpurple.nocca_admin.mvp.interactor.DeviceEnrolInteractor;
import com.bookpurple.nocca_admin.mvp.interfaces.DeviceEnrolPresenterContract;
import com.bookpurple.nocca_admin.mvp.presenter.DeviceEnrolPresenter;
import com.bookpurple.nocca_admin.util.rx.RxSchedulersAbstractBase;
import com.bookpurple.nocca_admin.util.rx.RxUtil;
import com.bookpurple.nocca_admin.util.rx.RxViewUtil;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

/*
 * Written by Gaurav Sharma on 2020-02-06.
 */
public class EnrolDeviceActivity extends AppCompatActivity implements DeviceEnrolPresenterContract.View {

    private static final String TAG = EnrolDeviceActivity.class.getSimpleName();
    @Inject
    public RxUtil rxUtil;
    @Inject
    public RxSchedulersAbstractBase rxSchedulers;
    @Inject
    public DeviceEnrolInteractor interactor;
    protected ModuleComponent component;
    private PublishSubject<DeviceRequestModel> submitButtonPublishSubject = PublishSubject.create();
    private EditText userEmail;
    private EditText userName;
    private EditText panNumber;
    private CheckBox merchantFlag;
    private EditText deviceName;
    private EditText deviceId;
    private Button submitButton;
    private ProgressBar progressBar;
    private TextView enrollTextView;
    // Rx Related Variables
    private CompositeDisposable lifecycle;
    private DeviceEnrolPresenter deviceEnrolPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enroll_device_activity);
        initViews();
        injectDependencies();
    }

    private void injectDependencies() {
        component = ComponentProvider.getComponent();
        component.inject(this);
        lifecycle = new CompositeDisposable();
        deviceEnrolPresenter = new DeviceEnrolPresenter(this,
                interactor,
                rxSchedulers,
                lifecycle);
    }

    private void initViews() {
        userEmail = findViewById(R.id.email);
        userName = findViewById(R.id.username);
        panNumber = findViewById(R.id.pan);
        merchantFlag = findViewById(R.id.merchantFlag);
        deviceName = findViewById(R.id.deviceName);
        deviceName.setVisibility(View.GONE);
        deviceId = findViewById(R.id.accountId);
        submitButton = findViewById(R.id.submit);
        progressBar = findViewById(R.id.loading);
        progressBar.setVisibility(View.GONE);
        enrollTextView = findViewById(R.id.enrollText);
        enrollTextView.setVisibility(View.GONE);
        addClickObservables();
    }

    private void addClickObservables() {
        RxViewUtil.click(submitButton)
                .subscribe(aVoid -> {
                    enrollUserFormData();
                }, throwable -> Logger.logException(TAG, throwable));

        RxViewUtil.click(merchantFlag)
                .subscribe(aVoid -> {
                    if (merchantFlag.isChecked()) {
                        deviceName.setVisibility(View.VISIBLE);
                    } else {
                        deviceName.setVisibility(View.GONE);
                    }
                }, throwable -> Logger.logException(TAG, throwable));
    }

    private void enrollUserFormData() {
        submitButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        NewDeviceRequest newDeviceRequest = new NewDeviceRequest();
        newDeviceRequest.userEmail = userEmail.getText().toString();
        newDeviceRequest.userName = userName.getText().toString();
        newDeviceRequest.panNumber = panNumber.getText().toString();
        newDeviceRequest.deviceId = deviceId.getText().toString();
        if (merchantFlag.isChecked()) {
            newDeviceRequest.merchantFlag = true;
            newDeviceRequest.deviceName = deviceName.getText().toString();
        } else {
            newDeviceRequest.merchantFlag = false;
        }

        deviceEnrolPresenter.addNewDevice(newDeviceRequest);
    }

    @Override
    public void onListingDataFetched(String response) {
        progressBar.setVisibility(View.GONE);
        enrollTextView.setText("Device Enroll Successfully!");
        enrollTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void dataFetchFailure(Throwable error) {
        progressBar.setVisibility(View.GONE);
        enrollTextView.setText("Device Enroll Failed!");
        enrollTextView.setVisibility(View.VISIBLE);
    }
}
