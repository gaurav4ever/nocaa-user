package com.bookpurple.nocca_admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.bookpurple.nocca_admin.R;
import com.bookpurple.nocca_admin.constant.Constant;
import com.bookpurple.nocca_admin.logger.Logger;
import com.bookpurple.nocca_admin.mvp.DeviceRequestModel;
import com.bookpurple.nocca_admin.util.rx.RxViewUtil;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private PublishSubject<DeviceRequestModel> loginButtonPublishSubject = PublishSubject.create();

    private EditText panEditText;
    private Button button;
    private Button enrollButton;

    // Rx Related Variables
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        injectDependencies();
        initView();
    }

    private void injectDependencies() {
        compositeDisposable = new CompositeDisposable();
    }

    private void initView() {
        panEditText = findViewById(R.id.pan);
        button = findViewById(R.id.submit);
        enrollButton = findViewById(R.id.newDeviceButton);
        addClickObservables();
        registerToClickObservables();
    }

    private void addClickObservables() {

        RxViewUtil.click(button)
                .subscribe(aVoid -> {
                    DeviceRequestModel deviceRequestModel = new DeviceRequestModel();
                    deviceRequestModel.panNumber = panEditText.getText().toString();
                    loginButtonPublishSubject.onNext(deviceRequestModel);
                }, throwable -> Logger.logException(TAG, throwable));

        RxViewUtil.click(enrollButton)
                .subscribe(aVoid -> {
                    // start enroll device activity
                    startEnrollDeviceActivity();
                }, throwable -> Logger.logException(TAG, throwable));
    }

    private void startEnrollDeviceActivity() {
        Intent intent = new Intent(getApplicationContext(), EnrolDeviceActivity.class);
        startActivity(intent);
    }


    private void registerToClickObservables() {
        Disposable loginButtonClickSubscription = loginButtonPublishSubject
                .subscribe(userDetailsRequest -> {
                    startPanListingActivity(userDetailsRequest);
                }, throwable -> Logger.logException(TAG, throwable));
        compositeDisposable.add(loginButtonClickSubscription);
    }

    private void startPanListingActivity(DeviceRequestModel deviceRequestModel) {
        Intent intent = new Intent(getApplicationContext(), DeviceListingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ParcelConstant.DEVICE_LISTING_REQUEST_MODEL, deviceRequestModel.panNumber);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
