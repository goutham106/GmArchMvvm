/*
 * Copyright (c) 2017 Gowtham Parimelazhagan.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gm.repository.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gm.repository.http.GlobalHttpHandler;
import com.gm.repository.rxerrorhandler.core.RxErrorHandler;
import com.gm.repository.rxerrorhandler.handler.listener.ResponseErrorListener;
import com.gm.repository.utils.RequestInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Dagger ClientModule
 */
@Module
public class ClientModule {
    private static final int TIME_OUT = 10;
    private Application mApplication;

    public ClientModule(@NonNull Application application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(@Nullable RetrofitConfiguration configuration,
                             Retrofit.Builder builder, OkHttpClient client, HttpUrl httpUrl) {
        builder.baseUrl(httpUrl)//domain name
                .client(client)//Set okhttp
                //.addCallAdapterFactory(new LiveDataCallAdapterFactory())//use LiveData
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//use rxjava
                .addConverterFactory(GsonConverterFactory.create());//use Gson
        if (configuration != null)
            configuration.configRetrofit(mApplication, builder);
        return builder.build();
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(@Nullable OkhttpConfiguration configuration,
                               OkHttpClient.Builder builder, Interceptor intercept,
                               @Nullable List<Interceptor> interceptors, @Nullable final GlobalHttpHandler handler) {
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(intercept);

        if (handler != null)
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    return chain.proceed(handler.onHttpRequestBefore(chain, chain.request()));
                }
            });

        if (interceptors != null) {//If the collection of interceptor is provided externally, it is traversed
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        if (configuration != null)
            configuration.configOkhttp(mApplication, builder);
        return builder.build();
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }


    @Singleton
    @Provides
    OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder();
    }


    @Singleton
    @Provides
    Interceptor provideInterceptor(RequestInterceptor intercept) {
        return intercept;//An interceptor that prints the request information
    }


    @Singleton
    @Provides
    RxErrorHandler provideRxErrorHandler(ResponseErrorListener listener) {
        return RxErrorHandler
                .builder()
                .with(mApplication)
                .responseErrorListener(listener)
                .build();
    }


    @Singleton
    @Provides
    Gson provideGson(@Nullable GsonConfiguration configuration) {
        GsonBuilder builder = new GsonBuilder();
        if (configuration != null)
            configuration.configGson(mApplication, builder);
        return builder.create();
    }


    public interface RetrofitConfiguration {
        void configRetrofit(Context context, Retrofit.Builder builder);
    }

    public interface OkhttpConfiguration {
        void configOkhttp(Context context, OkHttpClient.Builder builder);
    }

    public interface GsonConfiguration {
        void configGson(Context context, GsonBuilder builder);
    }

}
