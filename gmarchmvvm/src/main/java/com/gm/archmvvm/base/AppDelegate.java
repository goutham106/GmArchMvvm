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

package com.gm.archmvvm.base;

import android.app.Application;
import android.content.Context;

import com.gm.archmvvm.di.component.GmComponent;
import com.gm.archmvvm.di.module.GmModule;
import com.gm.lifecycle.LifecycleInjector;
import com.gm.lifecycle.delegate.AppLifecycles;
import com.gm.lifecycle.delegate.ILifecycle;
import com.gm.lifecycle.di.component.LifecycleComponent;
import com.gm.lifecycle.di.module.LifecycleModule;
import com.gm.repository.IRepository;
import com.gm.repository.RepositoryInjector;
import com.gm.repository.di.component.RepositoryComponent;
import com.gm.repository.di.module.RepositoryModule;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * Activity lifecycle agent interface implementation class
 */

public class AppDelegate implements AppLifecycles, ILifecycle, IRepository, IGm {
    private Application mApplication;
    private RepositoryInjector mRepositoryInjector;//Repository
    private LifecycleInjector mLifecycleInjector;//Lifecycle
    private GmInjector mGmInjector;//Arms


    public AppDelegate(Context context) {
        if (mRepositoryInjector == null)
            mRepositoryInjector = new RepositoryInjector(context);
        if (mLifecycleInjector == null)
            mLifecycleInjector = new LifecycleInjector(context);
        if (mGmInjector == null)
            mGmInjector = new GmInjector(context);
    }

    @Override
    public void attachBaseContext(Context context) {
        mLifecycleInjector.attachBaseContext(context);
    }

    @Override
    public void onCreate(Application application) {
        this.mApplication = application;

        //Repository inject
        mRepositoryInjector.onCreate(mApplication);

        //Lifecycle inject
        mLifecycleInjector.onCreate(mApplication);

        //Arms Inject
        mGmInjector.onCreate(mApplication);

    }

    @Override
    public void onTerminate(Application application) {
        mLifecycleInjector.onTerminate(application);
        this.mLifecycleInjector = null;
        mGmInjector.onTerminate(application);
        this.mGmInjector = null;
        mRepositoryInjector.onTerminate(application);
        this.mRepositoryInjector = null;
        this.mApplication = null;
    }


    @Override
    public LifecycleComponent getLifecycleComponent() {
        return mLifecycleInjector.getLifecycleComponent();
    }

    @Override
    public LifecycleModule getLifecycleModule() {
        return mLifecycleInjector.getLifecycleModule();
    }

    @Override
    public RepositoryComponent getRepositoryComponent() {
        return mRepositoryInjector.getRepositoryComponent();
    }

    @Override
    public RepositoryModule getRepositoryModule() {
        return mRepositoryInjector.getRepositoryModule();
    }

    @Override
    public GmComponent getGmComponent() {
        return mGmInjector.getGmComponent();
    }

    @Override
    public GmModule getGmModule() {
        return mGmInjector.getGmModule();
    }
}