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

package com.gm.repository.cache;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * Cache type, different modules have different cache strategies
 */
public enum CacheType {
    /**
     * RepositoryManager's RetrofitService container type
     */
    RETROFIT_SERVICE_CACHE_TYPE,

    /**
     * RepositoryManager CacheService container type
     */
    CACHE_SERVICE_CACHE_TYPE,

    /**
     * RepositoryManager RoomDatabase container type
     */
    ROOM_DATABASE_CACHE_TYPE,

    /**
     * External use type
     */
    EXTRAS_CACHE_TYPE,

    /**
     * Customize the cache type for easy expansion
     */
    CUSTOM_CACHE_TYPE
}
