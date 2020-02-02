package com.bookpurple.nocca_admin.network;

import android.content.Context;

import com.bookpurple.nocca_admin.network.model.CertificateModel;
import com.bookpurple.nocca_admin.network.parser.RuntimeTypeAdapterFactory;
import com.bookpurple.nocca_admin.network.util.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;

/*
 * Written by gauravsharma on 2019-05-19.
 */
public class NetworkWrapper {

    private Retrofit retrofit;

    private NetworkWrapper(Builder networkBuilder) {
        RetrofitProvider provider = new RetrofitProvider(networkBuilder);
        retrofit = provider.getRetrofit();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    /**
     * Builder class to provide network wrapper instance
     */
    public static class Builder {

        //Member variables
        private Context application;
        private String baseUrl;
        private long cacheSize = NetworkUtils.DEFAULT_CACHE_SIZE;
        private List<CertificateModel> certificates;
        private HashMap<String, String> headersBuilder;
        private long connectTimeout = NetworkUtils.CONNECT_TIMEOUT;
        private long readTimeout = NetworkUtils.READ_TIMEOUT;
        private long writeTimeout = NetworkUtils.WRITE_TIMEOUT;
        private TimeUnit timeUnitConnectTimeout = TimeUnit.MILLISECONDS;
        private TimeUnit timeUnitReadTimeout = TimeUnit.MILLISECONDS;
        private TimeUnit timeUnitWriteTimeout = TimeUnit.MILLISECONDS;
        private List<RuntimeTypeAdapterFactory> runtimeTypeAdapterFactoryList = new ArrayList<>();

        public Builder(Context application) {
            this.application = application;
        }

        /**
         * Setter for connection timeout
         *
         * @param connectTimeout time out value
         * @param timeUnit       unit for time out value
         */
        public Builder setConnectTimeout(long connectTimeout, TimeUnit timeUnit) {
            this.connectTimeout = connectTimeout;
            timeUnitConnectTimeout = timeUnit;
            return this;
        }

        /**
         * Setter for read timeout
         *
         * @param readTimeout time out value
         * @param timeUnit    unit for time out value
         */
        public Builder setReadTimeout(long readTimeout, TimeUnit timeUnit) {
            this.readTimeout = readTimeout;
            timeUnitReadTimeout = timeUnit;
            return this;
        }

        /**
         * Setter for write timeout
         *
         * @param writeTimeout time out value
         * @param timeUnit     unit for time out value
         */
        public Builder setWriteTimeout(long writeTimeout, TimeUnit timeUnit) {
            this.writeTimeout = writeTimeout;
            timeUnitWriteTimeout = timeUnit;
            return this;
        }

        /**
         * Function to register single / multiple runtime type adapter factories for gson builder
         *
         * @param typeAdapterFactory specific runtime type adapter factory
         * @param <T>                Generic placeholder for type adapter factory to figure out data type
         */
        public <T> Builder registerTypeAdapterFactory(RuntimeTypeAdapterFactory<T>
                                                              typeAdapterFactory) {
            runtimeTypeAdapterFactoryList.add(typeAdapterFactory);
            return this;
        }

        /**
         * Final build function to provide network wrapper
         *
         * @return Network wrapper instance
         */
        public NetworkWrapper build() {
            return new NetworkWrapper(this);
        }

        /**
         * Getter for returning application context
         *
         * @return application context
         */
        Context getApplicationContext() {
            return application;
        }

        /**
         * Getter for returning API base url
         *
         * @return base url
         */
        String getBaseUrl() {
            return baseUrl;
        }

        /**
         * Setter method for API base url
         *
         * @param baseUrl base url value
         */
        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * Getter for returning cache size
         *
         * @return cache size
         */
        long getCacheSize() {
            return cacheSize;
        }

        /**
         * Setter method for network wrapper cache size
         *
         * @param cacheSize cache size value
         */
        public Builder setCacheSize(long cacheSize) {
            this.cacheSize = cacheSize;
            return this;
        }

        /**
         * Getter for returning list of certificates
         *
         * @return list of certificates
         */
        List<CertificateModel> getCertificates() {
            return certificates;
        }

        /**
         * Setter for configuring filter certificates
         *
         * @param certificates list of certificate hash
         */
        public Builder setCertificates(List<CertificateModel> certificates) {
            this.certificates = certificates;
            return this;
        }

        /**
         * Getter for returning headers map
         *
         * @return headers map
         */
        HashMap<String, String> getHeadersBuilder() {
            return headersBuilder;
        }

        /**
         * Setter for request headers
         *
         * @param headersBuilder request headers map
         */
        public Builder setHeadersBuilder(HashMap<String, String> headersBuilder) {
            this.headersBuilder = headersBuilder;
            return this;
        }

        /**
         * Getter for returning connect timeout
         *
         * @return connect timeout
         */
        long getConnectTimeout() {
            return connectTimeout;
        }

        /**
         * Getter for returning read timeout
         *
         * @return read timeout
         */
        long getReadTimeout() {
            return readTimeout;
        }

        /**
         * Getter for returning write timeout
         *
         * @return write timeout
         */
        long getWriteTimeout() {
            return writeTimeout;
        }

        /**
         * Getter for returning time unit for connect timeout
         *
         * @return time out unit
         */
        TimeUnit getTimeUnitConnectTimeout() {
            return timeUnitConnectTimeout;
        }

        /**
         * Getter for returning time unit for read timeout
         *
         * @return time out unit
         */
        TimeUnit getTimeUnitReadTimeout() {
            return timeUnitReadTimeout;
        }

        /**
         * Getter for returning time unit for write timeout
         *
         * @return time out unit
         */
        TimeUnit getTimeUnitWriteTimeout() {
            return timeUnitWriteTimeout;
        }

        /**
         * Getter method for list of runtime type adapter factories
         *
         * @return list of runtime type adapter factories
         */
        List<RuntimeTypeAdapterFactory> getRuntimeTypeAdapterFactoryList() {
            return runtimeTypeAdapterFactoryList;
        }

    }
}
