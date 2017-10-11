package com.gm.repository;

import android.arch.persistence.room.RoomDatabase;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * Data management layer interface
 */
public interface IRepositoryManager {
    //Lazy load to get Retrofit Service
    <T> T obtainRetrofitService(Class<T> service);

    //Lazy load to get Room database
    <DB extends RoomDatabase> DB obtainRoomDatabase(Class<DB> database, String dbName);
}
