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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.gm.repository.cache.Cache;
import com.gm.repository.cache.CacheType;
import com.gm.repository.cache.LruCache;
import com.gm.repository.http.BaseUrl;
import com.gm.repository.http.GlobalHttpHandler;
import com.gm.repository.rxerrorhandler.handler.listener.ResponseErrorListener;
import com.gm.repository.utils.DataHelper;
import com.gm.repository.utils.RequestInterceptor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * RepositoryConfigModule
 */
@Module
public class RepositoryConfigModule {
    private Application mApplication;
    private HttpUrl mApiUrl;
    private BaseUrl mBaseUrl;
    private File mCacheFile;
    private GlobalHttpHandler mHandler;
    private List<Interceptor> mInterceptors;
    private ResponseErrorListener mErrorListener;
    private ClientModule.RetrofitConfiguration mRetrofitConfiguration;
    private ClientModule.OkhttpConfiguration mOkhttpConfiguration;
    private ClientModule.GsonConfiguration mGsonConfiguration;
    private ClientModule.RxCacheConfiguration mRxCacheConfiguration;
    private RequestInterceptor.Level mPrintHttpLogLevel;
    private DBModule.RoomConfiguration mRoomConfiguration;
    private Cache.Factory mCacheFactory;


    private RepositoryConfigModule(Builder builder) {
        this.mApplication = builder.application;
        this.mApiUrl = builder.apiUrl;
        this.mBaseUrl = builder.baseUrl;
        this.mHandler = builder.handler;
        this.mCacheFile = builder.cacheFile;
        this.mInterceptors = builder.interceptors;
        this.mErrorListener = builder.responseErrorListener;
        this.mRetrofitConfiguration = builder.retrofitConfiguration;
        this.mOkhttpConfiguration = builder.okhttpConfiguration;
        this.mGsonConfiguration = builder.gsonConfiguration;
        this.mRxCacheConfiguration = builder.rxCacheConfiguration;
        this.mPrintHttpLogLevel = builder.printHttpLogLevel;
        this.mRoomConfiguration = builder.roomConfiguration;
        this.mCacheFactory = builder.cacheFactory;
    }

    public static Builder builder() {
        return new Builder();
    }


    @Singleton
    @Provides
    @Nullable
    List<Interceptor> provideInterceptors() {
        return mInterceptors;
    }


    @Singleton
    @Provides
    HttpUrl provideBaseUrl() {
        if (mBaseUrl != null) {
            HttpUrl httpUrl = mBaseUrl.url();
            if (httpUrl != null) {
                return httpUrl;
            }
        }
        return mApiUrl == null ? HttpUrl.parse("https://api.github.com/") : mApiUrl;
    }

    @Singleton
    @Provides
    File provideCacheFile() {
        //Provide cache file
        return mCacheFile == null ? DataHelper.getCacheFile(mApplication) : mCacheFile;
    }

    @Singleton
    @Provides
    @Nullable
    GlobalHttpHandler provideGlobalHttpHandler() {
        return mHandler;//Handle Http request and response results
    }


    @Singleton
    @Provides
    ResponseErrorListener provideResponseErrorListener() {
        return mErrorListener == null ? ResponseErrorListener.EMPTY : mErrorListener;
    }

    @Singleton
    @Provides
    @Nullable
    ClientModule.RetrofitConfiguration provideRetrofitConfiguration() {
        return mRetrofitConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    ClientModule.OkhttpConfiguration provideOkhttpConfiguration() {
        return mOkhttpConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    ClientModule.GsonConfiguration provideGsonConfiguration() {
        return mGsonConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    ClientModule.RxCacheConfiguration provideRxCacheConfiguration() {
        return mRxCacheConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    RequestInterceptor.Level providePrintHttpLogLevel() {
        return mPrintHttpLogLevel;
    }

    @Singleton
    @Provides
    DBModule.RoomConfiguration provideRoomConfiguration() {
        return mRoomConfiguration == null ? DBModule.RoomConfiguration.EMPTY : mRoomConfiguration;
    }

    @Singleton
    @Provides
    Cache.Factory provideCacheFactory() {
        return mCacheFactory == null ? new Cache.Factory() {
            @NonNull
            @Override
            public Cache build(CacheType type) {
                //If you want to customize the size of LruCache, or do not want to use LruCache, want to use your own custom strategy
                //Please use the RepositoryConfigModule.Builder #cacheFactory() extension
                switch (type) {
                    case EXTRAS_CACHE_TYPE:
                        //External extras can only cache up to 500 content by default
                        return new LruCache(500);
                    default:
                        //The container in the RepositoryManager caches 100 content by default
                        return new LruCache(Cache.Factory.DEFAULT_CACHE_SIZE);
                }
            }
        } : mCacheFactory;
    }

    public static final class Builder {
        private Application application;
        private HttpUrl apiUrl;
        private BaseUrl baseUrl;
        private File cacheFile;
        private GlobalHttpHandler handler;
        private List<Interceptor> interceptors;
        private ResponseErrorListener responseErrorListener;
        private ClientModule.RetrofitConfiguration retrofitConfiguration;
        private ClientModule.OkhttpConfiguration okhttpConfiguration;
        private ClientModule.GsonConfiguration gsonConfiguration;
        private ClientModule.RxCacheConfiguration rxCacheConfiguration;
        private RequestInterceptor.Level printHttpLogLevel;
        private DBModule.RoomConfiguration roomConfiguration;
        private Cache.Factory cacheFactory;


        private Builder() {
        }

        @NonNull
        public Builder application(Application application) {
            this.application = application;
            return this;
        }

        public Builder baseUrl(String baseUrl) {//Base url
            if (TextUtils.isEmpty(baseUrl)) {
                throw new IllegalArgumentException("BaseUrl can not be empty");
            }
            this.apiUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder baseUrl(BaseUrl baseUrl) {
            if (baseUrl == null) {
                throw new IllegalArgumentException("BaseUrl can not be null");
            }
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder cacheFile(File cacheFile) {
            this.cacheFile = cacheFile;
            return this;
        }

        public Builder globalHttpHandler(GlobalHttpHandler handler) {//Used to handle http response results
            this.handler = handler;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {//Dynamically add any of the interceptors
            if (interceptors == null)
                interceptors = new ArrayList<>();
            this.interceptors.add(interceptor);
            return this;
        }

        public Builder responseErrorListener(ResponseErrorListener listener) {//Handle all the Error logic of Rxjava
            this.responseErrorListener = listener;
            return this;
        }

        public Builder retrofitConfiguration(ClientModule.RetrofitConfiguration retrofitConfiguration) {
            this.retrofitConfiguration = retrofitConfiguration;
            return this;
        }

        public Builder okhttpConfiguration(ClientModule.OkhttpConfiguration okhttpConfiguration) {
            this.okhttpConfiguration = okhttpConfiguration;
            return this;
        }

        public Builder gsonConfiguration(ClientModule.GsonConfiguration gsonConfiguration) {
            this.gsonConfiguration = gsonConfiguration;
            return this;
        }

        public Builder rxCacheConfiguration(ClientModule.RxCacheConfiguration rxCacheConfiguration) {
            this.rxCacheConfiguration = rxCacheConfiguration;
            return this;
        }

        public Builder printHttpLogLevel(RequestInterceptor.Level printHttpLogLevel) { //Whether the framework to print Http request and response information
            if (printHttpLogLevel == null)
                throw new IllegalArgumentException("printHttpLogLevel == null. Use RequestInterceptor.Level.NONE instead.");
            this.printHttpLogLevel = printHttpLogLevel;
            return this;
        }

        public Builder roomConfiguration(DBModule.RoomConfiguration roomConfiguration) {
            this.roomConfiguration = roomConfiguration;
            return this;
        }

        public Builder cacheFactory(Cache.Factory cacheFactory) {
            this.cacheFactory = cacheFactory;
            return this;
        }

        public RepositoryConfigModule build() {
            return new RepositoryConfigModule(this);
        }

    }
}
