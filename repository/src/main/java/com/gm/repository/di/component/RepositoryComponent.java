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

package com.gm.repository.di.component;

import com.gm.repository.IRepositoryManager;
import com.gm.repository.RepositoryInjector;
import com.gm.repository.cache.Cache;
import com.gm.repository.di.module.ClientModule;
import com.gm.repository.di.module.RepositoryConfigModule;
import com.gm.repository.di.module.RepositoryModule;
import com.gm.repository.rxerrorhandler.core.RxErrorHandler;

import java.io.File;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Dagger RepositoryComponent
 */
@Singleton
@Component(modules = {RepositoryModule.class, ClientModule.class, RepositoryConfigModule.class})
public interface RepositoryComponent {
    //Used to manage the network request layer and the database layer
    IRepositoryManager repositoryManager();

    //Rxjava error handling management class
    RxErrorHandler rxErrorHandler();

    //Provide OKHttpClient
    OkHttpClient okHttpClient();

    //Provide cache file
    File cacheFile();

    //Provide a cache for external use, do not store large amounts of data
    Cache<String, Object> extras();

    void inject(RepositoryInjector repositoryInjector);
}
