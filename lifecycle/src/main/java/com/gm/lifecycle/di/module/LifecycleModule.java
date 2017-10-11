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

package com.gm.lifecycle.di.module;

import android.app.Application;
import android.support.v4.util.ArrayMap;

import com.gm.lifecycle.delegate.ActivityLifecycle;
import com.gm.lifecycle.delegate.AppManager;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Dagger LifecycleModule
 */
@Module(includes = AndroidInjectionModule.class)
public class LifecycleModule {
    private Application mApplication;

    public LifecycleModule(Application application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    AppManager provideAppManager() {
        return new AppManager(mApplication);
    }

    @Singleton
    @Provides
    ActivityLifecycle provideActivityLifecycle(AppManager appManager, Map<String, Object> extras) {
        return new ActivityLifecycle(appManager, mApplication, extras);
    }

    @Singleton
    @Provides
    Map<String, Object> provideExtras() {
        return new ArrayMap<>();
    }
}
