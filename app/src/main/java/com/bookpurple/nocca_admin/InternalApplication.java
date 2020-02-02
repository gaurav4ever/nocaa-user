package com.bookpurple.nocca_admin;

import android.content.Context;

import com.bookpurple.nocca_admin.core.GsonUtil;
import com.facebook.stetho.Stetho;

/*
 * Written by Gaurav Sharma on 2020-02-02.
 */
public class InternalApplication {

    private static final String TAG = InternalApplication.class.getName();

    private static InternalApplication internalApplication;

    private Context application;

    private InternalApplication(Context application) {
        this.application = application;
    }

    public static InternalApplication getApplication() {
        if (internalApplication == null) {
            throw new NullPointerException("Internal Application class not intialized");
        }
        return internalApplication;
    }

    public static InternalApplication initialize(Context application) {
        internalApplication = new InternalApplication(application);
        return internalApplication;
    }

    public Context getApplicationContext() {
        return application;
    }

    public void initFlow() {
        Stetho.initializeWithDefaults(application);
        GsonUtil.getInstance();
    }
}
