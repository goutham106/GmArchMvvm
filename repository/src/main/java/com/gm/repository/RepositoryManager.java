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

package com.gm.repository;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.gm.repository.cache.Cache;
import com.gm.repository.cache.CacheType;
import com.gm.repository.di.module.DBModule;
import com.gm.repository.utils.Preconditions;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Data management layer implementation class
 */
@SuppressWarnings("all")
@Singleton
public class RepositoryManager implements IRepositoryManager {
    private Application mApplication;
    private Lazy<Retrofit> mRetrofit;
    private Lazy<RxCache> mRxCache;
    private Cache<String, Object> mRetrofitServiceCache;
    private Cache<String, Object> mCacheServiceCache;
    private Cache<String, Object> mRoomDatabaseCache;
    private final Cache.Factory mCacheFactory;
    private DBModule.RoomConfiguration mRoomConfiguration;

    @Inject
    public RepositoryManager(Application application, Lazy<Retrofit> retrofit, Lazy<RxCache> rxCache,
                             Cache.Factory cacheFactory, DBModule.RoomConfiguration roomConfiguration) {
        this.mApplication = application;
        this.mRetrofit = retrofit;
        this.mRxCache = rxCache;
        this.mCacheFactory = cacheFactory;
        this.mRoomConfiguration = roomConfiguration;
    }

    @Override
    public <T> T obtainRetrofitService(Class<T> service) {
        if (mRetrofitServiceCache == null)
            mRetrofitServiceCache = mCacheFactory.build(CacheType.RETROFIT_SERVICE_CACHE_TYPE);
        Preconditions.checkNotNull(mRetrofitServiceCache, "Cannot return null from a Cache.Factory#build(int) method");
        T retrofitService;
        synchronized (mRetrofitServiceCache) {
            retrofitService = (T) mRetrofitServiceCache.get(service.getName());
            if (retrofitService == null) {
                retrofitService = mRetrofit.get().create(service);
                mRetrofitServiceCache.put(service.getName(), retrofitService);
            }
        }
        return retrofitService;
    }


    @Override
    public <T> T obtainCacheService(Class<T> cache) {
        if (mCacheServiceCache == null)
            mCacheServiceCache = mCacheFactory.build(CacheType.CACHE_SERVICE_CACHE_TYPE);
        Preconditions.checkNotNull(mCacheServiceCache, "Cannot return null from a Cache.Factory#build(int) method");
        T cacheService;
        synchronized (mCacheServiceCache) {
            cacheService = (T) mCacheServiceCache.get(cache.getName());
            if (cacheService == null) {
                cacheService = mRxCache.get().using(cache);
                mCacheServiceCache.put(cache.getName(), cacheService);
            }
        }
        return cacheService;
    }

    @Override
    public void clearAllCache() {
        mRxCache.get().evictAll();
    }

    @Override
    public Context getContext() {
        return this.mApplication;
    }

    @Override
    public <DB extends RoomDatabase> DB obtainRoomDatabase(Class<DB> database, String dbName) {
        if (mRoomDatabaseCache == null)
            mRoomDatabaseCache = mCacheFactory.build(CacheType.ROOM_DATABASE_CACHE_TYPE);
        Preconditions.checkNotNull(mRoomDatabaseCache, "Cannot return null from a Cache.Factory#build(int) method");
        DB roomDatabase;
        synchronized (mRoomDatabaseCache) {
            roomDatabase = (DB) mRoomDatabaseCache.get(database.getName());
            if (roomDatabase == null) {
                RoomDatabase.Builder builder = Room.databaseBuilder(mApplication, database, dbName);
                if (mRoomConfiguration != null)//自定义 Room 配置
                    mRoomConfiguration.configRoom(mApplication, builder);
                roomDatabase = (DB) builder.build();
                mRoomDatabaseCache.put(database.getName(), roomDatabase);
            }
        }
        return roomDatabase;
    }
}
