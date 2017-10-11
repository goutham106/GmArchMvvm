package com.gm.mvp.integration;

import android.content.Context;

import com.gm.mvp.mvp.IModel;


/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 *
 * Used to manage the network request layer, and the data cache layer, may later add the database request layer
 * Provided to {@link IModel} necessary Api for data processing
 *
 */
public interface IRepositoryManager {

    /**
     * Get the corresponding Retrofit service based on the incoming Class
     *
     * @param service
     * @param <T>
     * @return
     */
    <T> T obtainRetrofitService(Class<T> service);

    /**
     * Obtain the corresponding RxCache service according to the incoming Class
     *
     * @param cache
     * @param <T>
     * @return
     */
    <T> T obtainCacheService(Class<T> cache);

    /**
     * Clean up all caches
     */
    void clearAllCache();

    Context getContext();

}
