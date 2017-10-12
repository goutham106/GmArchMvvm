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

import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Data management layer interface
 */
public interface IRepositoryManager {
    /**
     * Get the corresponding Retrofit service based on the incoming Class
     *
     * @param service: Retrofit Service Class
     * @param <T>:     Retrofit Service
     * @return Retrofit Service
     */
    <T> T obtainRetrofitService(Class<T> service);

    /**
     *Obtain the corresponding RxCache service according to the incoming Class
     *
     * @param cache: Cache Service Class
     * @param <T>:   Cache Service
     * @return Cache Service
     */
    <T> T obtainCacheService(Class<T> cache);

    /**
     * Clean up all caches
     */
    void clearAllCache();

    /**
     * Obtain Context(Application)
     */
    Context getContext();

    /**
     * Obtain the corresponding RxCache service according to the incoming Class
     *
     * @param database: RoomDatabase Class
     * @param <DB>:     RoomDatabase
     * @return RoomDatabase
     */
    <DB extends RoomDatabase> DB obtainRoomDatabase(Class<DB> database, String dbName);
}

