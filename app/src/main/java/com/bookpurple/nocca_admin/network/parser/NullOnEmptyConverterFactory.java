package com.bookpurple.nocca_admin.network.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/*
 * Converter factory for handling null response
 * Written by gauravsharma on 2019-05-19.
 */
public class NullOnEmptyConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.
                nextResponseBodyConverter(this, type, annotations);
        return body -> {
            if (body.contentLength() == 0) {
                return null;
            }
            return delegate.convert(body);
        };
    }
}
