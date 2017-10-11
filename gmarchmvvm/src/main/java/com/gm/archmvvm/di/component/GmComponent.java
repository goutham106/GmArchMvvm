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

package com.gm.archmvvm.di.component;

import android.app.Application;

import com.gm.archmvvm.base.GmInjector;
import com.gm.archmvvm.di.module.GmConfigModule;
import com.gm.archmvvm.di.module.GmModule;
import com.gm.archmvvm.di.module.ViewModelFactoryModule;
import com.gm.archmvvm.http.imageloader.ImageLoader;
import com.gm.lifecycle.di.module.LifecycleModule;
import com.gm.repository.di.module.RepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 */
@Singleton
@Component(modules = {ViewModelFactoryModule.class,
        RepositoryModule.class, LifecycleModule.class,
        GmModule.class, GmConfigModule.class})
public interface GmComponent {
    Application application();

    //Image loading manager, policy mode, default using Glide
    ImageLoader imageLoader();

    void inject(GmInjector gmInjector);
}

