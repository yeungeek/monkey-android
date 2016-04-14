package com.yeungeek.monkeyandroid.data.remote.retrofit2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by yeungeek on 2016/4/14.
 */
public class StringConverterFactory extends Converter.Factory {
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new Converter<String, RequestBody>() {
            @Override
            public RequestBody convert(String value) throws IOException {
                return RequestBody.create(MediaType.parse("text/plain"), value);
            }
        };
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new Converter<ResponseBody, String>() {
            @Override
            public String convert(ResponseBody value) throws IOException {
                return value.string();
            }
        };
    }
}
