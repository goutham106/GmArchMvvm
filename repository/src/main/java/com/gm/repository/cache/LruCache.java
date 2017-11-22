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

import android.support.annotation.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * LRU is Least Recently Used, that is, least recently, that is, when the cache is full, will give priority to those recently missed the most inaccessible data
 * This cache strategy is provided by default for the framework and can implement other caching policies, such as disk caching, caching for the framework or developer
 */
public class LruCache<K, V> implements Cache<K, V> {
    private final LinkedHashMap<K, V> cache = new LinkedHashMap<>(100, 0.75f, true);
    private final int initialMaxSize;
    private int maxSize;
    private int currentSize = 0;

    /**
     * Constructor for LruCache.
     *
     * @param size The maximum size of the cache, the unit used by this size must be consistent with the unit used by {@link #getItemSize (Object)}.
     */
    public LruCache(int size) {
        this.initialMaxSize = size;
        this.maxSize = size;
    }

    /**
     * Set a coefficient applied to the constructor in the current size, so as to get a new {@link #maxSize}
     * And immediately call {@link #evict} to clear the entry that satisfies the condition
     *
     * @param multiplier coefficient
     */
    public synchronized void setSizeMultiplier(float multiplier) {
        if (multiplier < 0) {
            throw new IllegalArgumentException("Multiplier must be >= 0");
        }
        maxSize = Math.round(initialMaxSize * multiplier);
        evict();
    }

    /**
     * Returns the size of each {@code item}, which defaults to 1, which must match the size of the constructor's passed
     * Subclasses can override this method to accommodate different units, such as bytes
     *
     * @param item Each {@code item} takes the size
     */
    protected int getItemSize(V item) {
        return 1;
    }

    /**
     * When the cache has a deported entry, it will call back this method, the default empty implementation, subclasses can override this method
     *
     * @param key {@code key} of the deported entry
     * @param value {@code value} of the deported entry
     */
    protected void onItemEvicted(K key, V value) {
        // optional override
    }

    /**
     * Returns the maximum size allowed by the current cache
     */
    @Override
    public synchronized int getMaxSize() {
        return maxSize;
    }

    /**
     * Returns the total size of the current cache
     */
    @Override
    public synchronized int size() {
        return currentSize;
    }

    /**
     * True if the {@code key} has a corresponding {@code value} in the cache and is not {@code null}
     *
     * @param key used to map the {@code key}
     */
    @Override
    public synchronized boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    /**
     * Return this {@code key} in the cache corresponding to the {@code value}, if the return {@code null} that the {@code key} does not correspond to the {@code value}
     *
     * @param key used to map the {@code key}
     */
    @Override
    @Nullable
    public synchronized V get(K key) {
        return cache.get(key);
    }

    /**
     * Add {@code key} and {@code value} to the cache as an entry, and if this {@code key} already has a corresponding {@code value} in the cache,
     * This {@code value} is replaced and returned by the new {@code value}, if it is a new entry for {@code null}
     * <p>
     * If {@link #getItemSize} returns a size greater than or equal to the maximum size allowed by the cache, you can not add this entry to the cache
     * This will call {@link #onItemEvicted (Object, Object)} to notify the current deportable entry
     *
     * @param key Add an entry through this {@code key}
     * @param value need to add {@code value}
     */
    @Override
    @Nullable
    public synchronized V put(K key, V value) {
        final int itemSize = getItemSize(value);
        if (itemSize >= maxSize) {
            onItemEvicted(key, value);
            return null;
        }

        final V result = cache.put(key, value);
        if (value != null) {
            currentSize += getItemSize(value);
        }
        if (result != null) {
            currentSize -= getItemSize(result);
        }
        evict();

        return result;
    }

    /**
     * Remove the entry for this {@code key} in the cache and return the {@code value} of the removed item
     * If it is returned to {@code null} it is possible because the {@code value} of {@code key} is {@code null} or the entry does not exist
     *
     * @param key Use this {@code key} to remove the corresponding entry
     */
    @Override
    @Nullable
    public synchronized V remove(K key) {
        final V value = cache.remove(key);
        if (value != null) {
            currentSize -= getItemSize(value);
        }
        return value;
    }

    /**
     * Clear all the contents of the cache
     */
    @Override
    public void clear() {
        trimToSize(0);
    }

    /**
     * When the specified size is less than the total size of the current cache, it will begin to clear the most recently used entries in the cache
     *
     * @param size Cache size
     */
    protected synchronized void trimToSize(int size) {
        Map.Entry<K, V> last;
        while (currentSize > size) {
            last = cache.entrySet().iterator().next();
            final V toRemove = last.getValue();
            currentSize -= getItemSize(toRemove);
            final K key = last.getKey();
            cache.remove(key);
            onItemEvicted(key, toRemove);
        }
    }

    /**
     * When the total size already occupied in the cache is larger than the maximum size that can be allowed, the {@link #trimToSize (int)} begins to clear the entry that satisfies the condition
     */
    private void evict() {
        trimToSize(maxSize);
    }
}


