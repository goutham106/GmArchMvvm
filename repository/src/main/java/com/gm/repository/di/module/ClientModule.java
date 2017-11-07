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
import com.gm.repository.http.RequestInterceptor;
import com.gm.repository.rxerrorhandler.core.RxErrorHandler;
import com.gm.repository.rxerrorhandler.handler.listener.ResponseErrorListener;
import com.gm.repository.utils.DataHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
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
        builder
                //domain name
                .baseUrl(httpUrl)
                //Set okhttp
                .client(client)
                //TODO: to use use LiveData
                //.addCallAdapterFactory(new LiveDataCallAdapterFactory())
                //use rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //use Gson
                .addConverterFactory(GsonConverterFactory.create());
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

        if (handler != null) {
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    return chain.proceed(handler.onHttpRequestBefore(chain, chain.request()));
                }
            });
        }

        //If the collection of interceptor is provided externally, it is traversed
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        if (configuration != null) {
            configuration.configOkhttp(mApplication, builder);
        }
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
        //An interceptor that prints the request information
        return intercept;
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
        if (configuration != null) {
            configuration.configGson(mApplication, builder);
        }
        return builder.create();
    }

    /**
     * provide {@link RxCache}
     *
     * @param cacheDirectory RxCache cache path
     * @return RxCache
     */
    @Singleton
    @Provides
    RxCache provideRxCache(@Nullable RxCacheConfiguration configuration, @Named("RxCacheDirectory") File cacheDirectory) {
        RxCache.Builder builder = new RxCache.Builder();
        RxCache rxCache = null;
        if (configuration != null) {
            rxCache = configuration.configRxCache(mApplication, builder);
        }
        if (rxCache != null) {
            return rxCache;
        }
        return builder.persistence(cacheDirectory, new GsonSpeaker());
    }

    /**
     * You need to provide a cache path to {@link RxCache}
     *
     * @param cacheDir CacheDir
     * @return File
     */
    @Singleton
    @Provides
    @Named("RxCacheDirectory")
    File provideRxCacheDirectory(File cacheDir) {
        File cacheDirectory = new File(cacheDir, "RxCache");
        return DataHelper.makeDirs(cacheDirectory);
    }


    public interface RetrofitConfiguration {
        /**
         * Provide interface, custom configuration Retrofit
         *
         * @param context Context
         * @param builder Retrofit.Builder
         */
        void configRetrofit(Context context, Retrofit.Builder builder);
    }

    public interface OkhttpConfiguration {
        /**
         * Provide interface, custom configuration OkHttpClient
         *
         * @param context Context
         * @param builder OkHttpClient.Builder
         */
        void configOkhttp(Context context, OkHttpClient.Builder builder);
    }

    public interface GsonConfiguration {
        /**
         * Provide interface, custom configuration Gson
         *
         * @param context Context
         * @param builder GsonBuilder
         */
        void configGson(Context context, GsonBuilder builder);
    }

    public interface RxCacheConfiguration {
        /**
         * Provide interface, custom configuration RxCache
         *
         * @param context Context
         * @param builder RxCache.Builder
         */
        RxCache configRxCache(Context context, RxCache.Builder builder);
    }

}
