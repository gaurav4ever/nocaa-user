package com.bookpurple.nocca_admin.network;

import com.bookpurple.nocca_admin.BuildConfig;
import com.bookpurple.nocca_admin.core.GsonUtil;
import com.bookpurple.nocca_admin.logger.Logger;
import com.bookpurple.nocca_admin.network.logger.HttpLoggingInterceptor;
import com.bookpurple.nocca_admin.network.parser.ResponseParser;
import com.bookpurple.nocca_admin.network.util.NetworkUtils;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.CertificatePinner;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Written by gauravsharma on 2019-05-19.
 */
public class RetrofitProvider {

    private static final String TAG = RetrofitProvider.class.getSimpleName();

    private NetworkWrapper.Builder builder;

    RetrofitProvider(NetworkWrapper.Builder builder) {
        this.builder = builder;
    }

    /**
     * Function to provide okhttp client builder instance
     *
     * @return okhttp client builder
     */
    private OkHttpClient.Builder provideOkHttpBuilder() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Logger.log(TAG, message);
                    }

                    @Override
                    public void fileLog(String message) {
                        //TODO Implement file logger using square tape
                    }
                });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        CertificatePinner certificatePinner = NetworkUtils.
                getCertificates(builder.getCertificates());
        if (certificatePinner != null) {
            okHttpBuilder.certificatePinner(certificatePinner);
        }

        List<Protocol> protocols = new ArrayList<>(1);
        protocols.add(Protocol.HTTP_1_1);

        okHttpBuilder.protocols(protocols);

        okHttpBuilder.addNetworkInterceptor(chain -> {
            Request originalRequest = chain.request();
            Headers headers = originalRequest.headers();
            Headers.Builder headersBuilder = builder.getHeadersBuilder() != null ?
                    NetworkUtils.getNetworkHeaders(builder.getHeadersBuilder()) :
                    new Headers.Builder();
            for (String name : headers.names()) {
                if (headersBuilder.get(name) == null) {
                    headersBuilder.add(name, headers.get(name));
                } else {
                    headersBuilder.set(name, headers.get(name));
                }
            }

            Request authorizedRequest = originalRequest.newBuilder()
                    .headers(headersBuilder.build())
                    .build();

            return chain.proceed(authorizedRequest);
        });
        okHttpBuilder.addNetworkInterceptor(httpLoggingInterceptor);

        if (BuildConfig.DEBUG) {
            okHttpBuilder.addNetworkInterceptor(new StethoInterceptor());
        }

        okHttpBuilder.cache(new Cache(builder.getApplicationContext().getCacheDir(),
                builder.getCacheSize()));

        return okHttpBuilder;
    }

    /**
     * Function to provide okhttp client instance
     *
     * @param okHttpBuilder requires a okhttp builder
     * @return okhttp client
     */
    private OkHttpClient provideOkHttpClient(OkHttpClient.Builder okHttpBuilder) {
        okHttpBuilder.connectTimeout(builder.getConnectTimeout(),
                builder.getTimeUnitConnectTimeout())
                .readTimeout(builder.getReadTimeout(), builder.getTimeUnitReadTimeout())
                .writeTimeout(builder.getWriteTimeout(), builder.getTimeUnitWriteTimeout());

        return okHttpBuilder.build();
    }

    /**
     * Function to provide retrofit builder instance
     *
     * @return retrofit builder
     */
    private Retrofit.Builder provideRetrofitBuilder() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create(GsonUtil.getInstance().getGson()));
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        if (builder.getRuntimeTypeAdapterFactoryList() != null &&
                !builder.getRuntimeTypeAdapterFactoryList().isEmpty()) {
            retrofitBuilder.addConverterFactory(GsonConverterFactory.
                    create(ResponseParser.getInstance(builder.getRuntimeTypeAdapterFactoryList()).
                            getResponseParser()));
        } else {
            retrofitBuilder.addConverterFactory(GsonConverterFactory.
                    create(ResponseParser.getInstance().getResponseParser()));
        }

        return retrofitBuilder;
    }

    /**
     * Function to provide the retrofit instance for various API calls
     *
     * @return retrofit instance
     */
    Retrofit getRetrofit() {
        return provideRetrofitBuilder()
                .baseUrl(builder.getBaseUrl())
                .client(provideOkHttpClient(provideOkHttpBuilder()))
                .build();
    }
}
