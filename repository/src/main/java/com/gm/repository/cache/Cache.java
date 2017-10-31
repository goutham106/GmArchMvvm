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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * Cache interface
 */
public interface Cache<K, V> {
    interface Factory {

        /**
         * Default cache size
         */
        int DEFAULT_CACHE_SIZE = 100;

        /**
         * Returns a new cache
         *
         * @param type The Id of the module type in the frame
         * @return Cache
         */
        @NonNull
        Cache build(CacheType type);
    }

    /**
     * Returns the total size of the current cache
     *
     * @return The total size of the current cache has been occupied
     */
    int size();

    /**
     * Returns the maximum size allowed by the current cache
     *
     * @return The maximum size that the current cache can allow
     */
    int getMaxSize();

    /**
     * Return this {@code key} in the cache corresponding to the {@code value}, if the return {@code null} that the {@code key} does not correspond to the {@code value}
     *
     * @param key
     * @return Value
     */
    @Nullable
    V get(K key);

    /**
     * Add {@code key} and {@code value} to the cache as an entry, and if this {@code key} already has a corresponding {@code value} in the cache,
     * This {@code value} is replaced and returned by the new {@code value}, if it is a new entry for {@code null}
     *
     * @param key Key
     * @param value new value
     * @return old value
     */
    @Nullable
    V put(K key, V value);

    /**
     * Removes the entry for this {@code key} in the cache and returns the value of the removed item
     * If the return is {@code null} it is possible because the {@code key} corresponds to the value of {@code null} or the entry does not exist
     *
     * @param key
     * @return Value
     */
    @Nullable
    V remove(K key);

    /**
     * If the {@code key} has a corresponding value in the cache and is not {@code null}, it returns {@code true}
     *
     * @param key
     * @return How does the existence of the cache return true?
     */
    boolean containsKey(K key);

    /**
     * Clear all the contents of the cache
     */
    void clear();
}

