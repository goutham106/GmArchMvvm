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

import com.gm.repository.di.module.DBModule;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import retrofit2.Retrofit;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Data management layer implementation class
 */
@Singleton
public class RepositoryManager implements IRepositoryManager {
    private final Map<String, Object> mRetrofitServiceCache = new HashMap<>();
    private final Map<String, Object> mRoomDatabaseCache = new HashMap<>();
    private Application mApplication;
    private Lazy<Retrofit> mRetrofit;
    private DBModule.RoomConfiguration mRoomConfiguration;

    @Inject
    public RepositoryManager(Application application, Lazy<Retrofit> retrofit, DBModule.RoomConfiguration roomConfiguration) {
        this.mApplication = application;
        this.mRetrofit = retrofit;
        this.mRoomConfiguration = roomConfiguration;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T obtainRetrofitService(Class<T> service) {
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
    @SuppressWarnings("unchecked")
    public <DB extends RoomDatabase> DB obtainRoomDatabase(Class<DB> database, String dbName) {
        DB roomDatabase;
        synchronized (mRoomDatabaseCache) {
            roomDatabase = (DB) mRoomDatabaseCache.get(database.getName());
            if (roomDatabase == null) {
                RoomDatabase.Builder builder = Room.databaseBuilder(mApplication, database, dbName);
                if (mRoomConfiguration != null)//Customize the room configuration
                    mRoomConfiguration.configRoom(mApplication, builder);
                roomDatabase = (DB) builder.build();
                mRoomDatabaseCache.put(database.getName(), roomDatabase);
            }
        }
        return roomDatabase;
    }
}
