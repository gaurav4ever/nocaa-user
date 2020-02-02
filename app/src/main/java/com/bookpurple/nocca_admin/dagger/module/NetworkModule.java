package com.bookpurple.nocca_admin.dagger.module;

import com.bookpurple.nocca_admin.BuildConfig;
import com.bookpurple.nocca_admin.InternalApplication;
import com.bookpurple.nocca_admin.network.NetworkWrapper;
import com.bookpurple.nocca_admin.network.RequestHeaders;
import com.bookpurple.nocca_admin.network.api.ServiceApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/*
 * Written by Gaurav Sharma on 2020-02-01.
 */
@Module
public class NetworkModule {

    @Named("baseRetrofit")
    @Provides
    @Singleton
    public Retrofit provideBaseRetrofit() {
        NetworkWrapper wrapper = new NetworkWrapper
                .Builder(InternalApplication.getApplication().getApplicationContext())
                .setBaseUrl(BuildConfig.PRODUCTION_URL)
                .setCacheSize(1024 * 1024 * 1024)
                .setHeadersBuilder(RequestHeaders.getInstance().getHeaders())
                .setCertificates(RequestHeaders.getInstance().getCertificateList())
                .setConnectTimeout(7, TimeUnit.SECONDS)
                .setReadTimeout(120, TimeUnit.SECONDS)
                .setWriteTimeout(10, TimeUnit.SECONDS)
                .build();

        return wrapper.getRetrofit();
    }

    @Named("serviceApi")
    @Provides
    @Singleton
    public ServiceApi provideServiceApi(@Named("baseRetrofit") Retrofit retrofit) {
        return retrofit.create(ServiceApi.class);
    }
}
