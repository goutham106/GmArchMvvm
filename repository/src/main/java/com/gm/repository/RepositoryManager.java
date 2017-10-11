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
 *
 * Data management layer implementation class
 */
@Singleton
public class RepositoryManager implements IRepositoryManager {
    private Application mApplication;
    private Lazy<Retrofit> mRetrofit;
    private final Map<String, Object> mRetrofitServiceCache = new HashMap<>();
    private final Map<String, Object> mRoomDatabaseCache = new HashMap<>();
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
