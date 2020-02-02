package com.bookpurple.nocca_admin.network;

import android.os.Build;

import com.bookpurple.nocca_admin.network.model.CertificateModel;
import com.bookpurple.nocca_admin.util.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Written by gauravsharma on 2019-05-19.
 */
public class RequestHeaders {

    private static RequestHeaders requestHeaders;

    public static synchronized RequestHeaders getInstance() {
        if (requestHeaders == null) {
            requestHeaders = new RequestHeaders();
        }
        return requestHeaders;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return CommonUtils.capitalize(model);
        } else {
            return CommonUtils.capitalize(manufacturer) + " " + model;
        }
    }

    public HashMap<String, String> getHeaders() {
        HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put("Content-Type", "application/json");

        return headerMap;
    }

    public List<CertificateModel> getCertificateList() {
        List<CertificateModel> certificateList = new ArrayList<>();
        return certificateList;
    }
}
