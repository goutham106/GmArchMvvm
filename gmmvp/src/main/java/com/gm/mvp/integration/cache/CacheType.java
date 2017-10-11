package com.gm.mvp.integration.cache;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 *
 * When building {@link Cache}, use the type declared in {@link CacheType} to distinguish between different modules
 * To build different caching strategies for different modules
 *
 * @see Cache.Factory#build(int)
 */
public interface CacheType {
    //Corresponds to the container in the RepositoryManager
    int RETROFIT_SERVICE_CACHE_TYPE = 0;
    int CACHE_SERVICE_CACHE_TYPE = 1;
    //Corresponds to extras in AppComponent
    int EXTRAS_CACHE_TYPE = 2;
}
