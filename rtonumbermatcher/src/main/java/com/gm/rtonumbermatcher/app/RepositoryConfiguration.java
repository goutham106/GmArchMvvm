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

package com.gm.rtonumbermatcher.app;

import android.content.Context;

import com.gm.repository.ConfigRepository;
import com.gm.repository.cache.Cache;
import com.gm.repository.cache.LruCache;
import com.gm.repository.di.module.RepositoryConfigModule;
import com.gm.repository.http.RequestInterceptor;
import com.gm.rtonumbermatcher.BuildConfig;

import java.util.concurrent.TimeUnit;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/5/17.
 */

public class RepositoryConfiguration implements ConfigRepository {
    @Override
    public void applyOptions(Context context, RepositoryConfigModule.Builder builder) {
        if (!BuildConfig.LOG_DEBUG) //Release, Let the framework no longer print Http request and response information
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);

        builder.baseUrl("www")
                // Here to provide a global processing Http request and response to the results of the processing class, the client can step ahead of the server to get the results of the return
                .globalHttpHandler(new GlobalHttpHandlerImpl())
                // Used to deal with all the errors occurred in rxjava, rxjava occurred in each error will call back this interface
                // rxjava need to use ErrorHandleSubscriber (the default implementation of Subscriber's onError method), this monitor to take effect
                .responseErrorListener(new ResponseErrorListenerImpl())
                //Here you can customize the configuration of Gson parameters
                .gsonConfiguration((context1, gsonBuilder) -> {
                    gsonBuilder
                            .serializeNulls()//Supports serialization of null parameters
                            .enableComplexMapKeySerialization();//Support will be serialized key for the object of the map, the default can only serialize the key for the string map
                })
                //Here you can customize the configuration of Retrofit parameters, and even you can replace the system configuration okhttp object
                .retrofitConfiguration((context1, retrofitBuilder) -> {
                     /* Such as the use of fastjson alternative gson
                     retrofitBuilder.addConverterFactory(FastJsonConverterFactory.create());*/
                    /* Adapt LiveData
                    retrofitBuilder.addCallAdapterFactory(new LiveDataCallAdapterFactory());*/
                })
                //Here you can customize the configuration Okhttp parameters
                .okhttpConfiguration((context1, okhttpBuilder) -> {
                    // Support Https
                    // okhttpBuilder.sslSocketFactory()
                    okhttpBuilder.writeTimeout(10, TimeUnit.SECONDS);
                })
                //Here you can customize the configuration of RxCache parameters
                .rxCacheConfiguration((context1, rxCacheBuilder) -> {
                    rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
                    return null;
                })
                //Here you can customize the configuration RoomDatabase, such as database migration upgrade
                .roomConfiguration((context1, roomBuilder) -> {
/*                    roomBuilder.addMigrations(new Migration(1, 2) {
                        @Override
                        public void migrate(SupportSQLiteDatabase database) {
                            // TODO: 10/5/17
                            // Since we didn't alter the table, there's nothing else to do here.
                        }
                    });*/
                })
                //According to the current situation of the project and the environment for the framework of certain components to provide a custom cache strategy, with a strong scalability
                .cacheFactory(type -> {
                    switch (type) {
                        case EXTRAS_CACHE_TYPE:
                            //External extras can only cache up to 500 content by default
                            return new LruCache(500);
                        /*case CUSTOM_CACHE_TYPE:
                            return new CustomCache();//Customize Cache*/
                        default:
                            //RepositoryManager In the container default cache of 100 content
                            return new LruCache(Cache.Factory.DEFAULT_CACHE_SIZE);
                    }
                });
    }
}
