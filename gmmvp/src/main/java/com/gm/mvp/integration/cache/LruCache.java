package com.gm.mvp.integration.cache;

import android.support.annotation.Nullable;

import com.jess.arms.di.module.GlobalConfigModule;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 * <p>
 * LRU is Least Recently Used, that is, least recently, that is, when the cache is full, will give priority to those recently missed the most inaccessible data
 * This cache strategy is provided by default for the framework and can implement other caching policies, such as disk caching, caching for the framework or developer
 *
 * @see GlobalConfigModule#provideCacheFactory()
 * @see Cache
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
     * Set a coefficient applied to the constructor in the current size, so as to get a new {@link # maxSize}
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
     * Returns the size of each {@code item}, defaults to 1, the size of the unit must be consistent with the size of the constructor passed
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
     * @param key   To be deported {@code key}
     * @param value To be deported {@code value}
     */
    protected void onItemEvicted(K key, V value) {
        // optional override
    }

    /**
     * Returns the maximum allowed by the current cache size
     */
    @Override
    public synchronized int getMaxSize() {
        return maxSize;
    }

    /**
     * Returns the total total occupied by the current cache size
     */
    @Override
    public synchronized int size() {
        return currentSize;
    }

    /**
     * True if the {@code key} has a corresponding {@code value} in the cache and is not {@code null}
     *       *
     *
     * @param key Used to map {@code key}
     */
    @Override
    public synchronized boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    /**
     * Return this {@code key} in the cache corresponding to the {@code value}, if the return {@code null} that the {@code key} does not correspond to the {@code value}
     *
     * @param key Used to map {@code key}
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
     * @param key   Add an entry through this {@code key}
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
     * @param size
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

