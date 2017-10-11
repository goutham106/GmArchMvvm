package com.gm.mvp.integration;

import android.app.Application;
import android.content.Context;

import com.gm.lifecycle.utils.Preconditions;
import com.gm.mvp.integration.cache.Cache;
import com.gm.mvp.integration.cache.CacheType;
import com.gm.mvp.mvp.IModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 *
 * Used to manage the network request layer, and the data cache layer, may later add the database request layer
 * Provided to the {@link IModel} layer necessary for Api to do data processing
 *
 */
@Singleton
public class RepositoryManager implements IRepositoryManager {
    private Lazy<Retrofit> mRetrofit;
    private Lazy<RxCache> mRxCache;
    private Application mApplication;
    private Cache<String, Object> mRetrofitServiceCache;
    private Cache<String, Object> mCacheServiceCache;
    private Cache.Factory mCachefactory;

    @Inject
    public RepositoryManager(Lazy<Retrofit> retrofit, Lazy<RxCache> rxCache, Application application
            , Cache.Factory cachefactory) {
        this.mRetrofit = retrofit;
        this.mRxCache = rxCache;
        this.mApplication = application;
        this.mCachefactory = cachefactory;
    }

    /**
     * Get the corresponding Retrofit service based on the incoming Class
     *
     * @param service
     * @param <T>
     * @return
     */
    @Override
    public <T> T obtainRetrofitService(Class<T> service) {
        if (mRetrofitServiceCache == null)
            mRetrofitServiceCache = mCachefactory.build(CacheType.RETROFIT_SERVICE_CACHE_TYPE);
        Preconditions.checkNotNull(mRetrofitServiceCache,"Cannot return null from a Cache.Factory#build(int) method");
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

    /**
     * Obtain the corresponding RxCache service according to the incoming Class
     *
     * @param cache
     * @param <T>
     * @return
     */
    @Override
    public <T> T obtainCacheService(Class<T> cache) {
        if (mCacheServiceCache == null)
            mCacheServiceCache = mCachefactory.build(CacheType.CACHE_SERVICE_CACHE_TYPE);
        Preconditions.checkNotNull(mCacheServiceCache,"Cannot return null from a Cache.Factory#build(int) method");
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

    /**
     * Clean up all caches
     */
    @Override
    public void clearAllCache() {
        mRxCache.get().evictAll();
    }

    @Override
    public Context getContext() {
        return mApplication;
    }
}
