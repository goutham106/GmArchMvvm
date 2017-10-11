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

package com.gm.lifecycle;

import android.app.Application;
import android.content.Context;

import com.gm.lifecycle.delegate.ActivityLifecycle;
import com.gm.lifecycle.delegate.AppLifecycles;
import com.gm.lifecycle.delegate.ILifecycle;
import com.gm.lifecycle.di.component.DaggerLifecycleComponent;
import com.gm.lifecycle.di.component.LifecycleComponent;
import com.gm.lifecycle.di.module.LifecycleModule;
import com.gm.lifecycle.utils.ManifestLifecycleParser;
import com.gm.lifecycle.utils.Preconditions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/4/17.
 * <p>
 * LifecycleInjector, need to initialize the Application, into LifecycleComponent
 */

public class LifecycleInjector implements ILifecycle, AppLifecycles {
    @Inject
    protected ActivityLifecycle mActivityLifecycle;
    private Application mApplication;
    private LifecycleComponent mLifecycleComponent;
    private LifecycleModule mLifecycleModule;
    private List<ConfigLifecycle> mConfigLifecycles;
    private List<AppLifecycles> mAppLifecycles = new ArrayList<>();
    private List<Application.ActivityLifecycleCallbacks> mActivityLifecycles = new ArrayList<>();


    public LifecycleInjector(Context context) {
        mConfigLifecycles = new ManifestLifecycleParser(context).parse();
        for (ConfigLifecycle lifecycle : mConfigLifecycles) {
            lifecycle.injectAppLifecycle(context, mAppLifecycles);
            lifecycle.injectActivityLifecycle(context, mActivityLifecycles);
        }
    }

    @Override
    public void attachBaseContext(Context context) {
        for (AppLifecycles lifecycle : mAppLifecycles)
            lifecycle.attachBaseContext(context);
    }

    @Override
    public void onCreate(Application application) {
        this.mApplication = application;

        if (mLifecycleModule == null)
            mLifecycleModule = new LifecycleModule(mApplication);
        mLifecycleComponent = DaggerLifecycleComponent.builder()
                .lifecycleModule(mLifecycleModule)
                .build();
        mLifecycleComponent.inject(this);

        mLifecycleComponent.extras().put(ConfigLifecycle.class.getName(), mConfigLifecycles);

        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);

        this.mConfigLifecycles = null;

        for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles)
            mApplication.registerActivityLifecycleCallbacks(lifecycle);

        for (AppLifecycles lifecycle : mAppLifecycles)
            lifecycle.onCreate(mApplication);
    }

    @Override
    public void onTerminate(Application application) {
        if (mActivityLifecycle != null)
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);

        if (mActivityLifecycles != null && mActivityLifecycles.size() > 0)
            for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles)
                mApplication.unregisterActivityLifecycleCallbacks(lifecycle);

        if (mAppLifecycles != null && mAppLifecycles.size() > 0)
            for (AppLifecycles lifecycle : mAppLifecycles)
                lifecycle.onTerminate(mApplication);

        this.mLifecycleModule = null;
        this.mLifecycleComponent = null;
        this.mActivityLifecycle = null;
        this.mActivityLifecycles = null;
        this.mAppLifecycles = null;
        this.mApplication = null;
    }


    @Override
    public LifecycleComponent getLifecycleComponent() {
        Preconditions.checkNotNull(mLifecycleComponent,
                "%s cannot be null,first call %s#onCreate(Application) in %s#onCreate()",
                LifecycleComponent.class.getName(), getClass().getName(), mApplication.getClass().getName());
        return this.mLifecycleComponent;
    }

    @Override
    public LifecycleModule getLifecycleModule() {
        Preconditions.checkNotNull(mLifecycleModule,
                "%s cannot be null,first call %s#onCreate(Application) in %s#onCreate()",
                LifecycleModule.class.getName(), getClass().getName(), mApplication.getClass().getName());
        return this.mLifecycleModule;
    }

}
