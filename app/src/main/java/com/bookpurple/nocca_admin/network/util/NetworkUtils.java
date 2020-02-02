package com.bookpurple.nocca_admin.network.util;


import com.bookpurple.nocca_admin.network.model.CertificateModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.CertificatePinner;
import okhttp3.Headers;

/*
 * Written by gauravsharma on 2019-05-19.
 */
public class NetworkUtils {

    public static final long DEFAULT_CACHE_SIZE = 1024 * 1024 * 1024;
    public static final long CONNECT_TIMEOUT = 7;
    public static final long READ_TIMEOUT = 120;
    public static final long WRITE_TIMEOUT = 10;

    public static CertificatePinner getCertificates(List<CertificateModel> certificateList) {
        CertificatePinner certificates = null;
        if (certificateList != null && !certificateList.isEmpty()) {
            CertificatePinner.Builder certificateBuilder = new CertificatePinner.Builder();
            for (CertificateModel model : certificateList) {
                certificateBuilder.add(model.pattern, model.certificateHash);
            }
            certificates = certificateBuilder.build();
        }

        return certificates;
    }

    public static Headers.Builder getNetworkHeaders(HashMap<String, String> headerMap) {
        Headers.Builder builder = null;
        if (headerMap != null && !headerMap.isEmpty()) {
            builder = new Headers.Builder();
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder;
    }
}
