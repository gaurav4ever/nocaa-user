package com.bookpurple.nocca_admin.util.rx;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Written by gauravsharma on 2/24/19.
 **/
public class RxViewUtil {

    public static Observable<Object> click(View view) {
        return RxView.clicks(view)
                .throttleFirst(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Object> click(View view, long millis) {
        return RxView.clicks(view).
                throttleFirst(millis, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint({"CheckResult"})
    public static void disableClick(View view) {
        RxView.clicks(view).
                throttleFirst(1L, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((s) -> {
                });
    }

    @SuppressLint({"CheckResult"})
    public static void disableClick(View view, long millis) {
        RxView.clicks(view).
                throttleFirst(millis, TimeUnit.MILLISECONDS).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe((s) -> {
                });
    }

    public static Observable<MotionEvent> touches(View view) {
        return RxView.touches(view)
                .throttleFirst(500L, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<MotionEvent> touches(View view, long millis) {
        return RxView.touches(view)
                .throttleFirst(millis, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Object> longClick(View view) {
        return RxView.longClicks(view)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Object> longClick(View view, int throttleSeconds) {
        return RxView.longClicks(view)
                .throttleFirst((long) throttleSeconds, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<TextViewTextChangeEvent> textChange(EditText textView) {
        return RxTextView.textChangeEvents(textView)
                .skip(1L)
                .debounce(200L, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<TextViewTextChangeEvent> textChange(EditText textView, long millis) {
        return RxTextView.textChangeEvents(textView)
                .skip(1L)
                .debounce(millis, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
