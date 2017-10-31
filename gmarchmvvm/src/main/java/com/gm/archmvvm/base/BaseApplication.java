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

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.gm.archmvvm.di.component.GmComponent;
import com.gm.archmvvm.di.module.GmModule;
import com.gm.lifecycle.delegate.AppLifecycles;
import com.gm.lifecycle.delegate.ILifecycle;
import com.gm.lifecycle.di.component.LifecycleComponent;
import com.gm.lifecycle.di.module.LifecycleModule;
import com.gm.repository.IRepository;
import com.gm.repository.di.component.RepositoryComponent;
import com.gm.repository.di.module.RepositoryModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 * <p>
 * MVVMGm BaseApplication
 */

public class BaseApplication extends Application implements IGm, ILifecycle, IRepository, HasActivityInjector, HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Activity> mActivityInjector;//Dagger.Android Activity injection

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;//Dagger.Android Fragment injection

    private AppLifecycles mAppDelegate;

    /**
     * This will be called before {@link BaseApplication # onCreate}, and you can do some earlier initialization
     * Commonly used in MultiDex and plug-in framework for initialization
     *
     * @param context context
     */
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        this.mAppDelegate = new AppDelegate(context);
        this.mAppDelegate.attachBaseContext(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppDelegate.onCreate(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mAppDelegate.onTerminate(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return this.mActivityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return this.mFragmentInjector;
    }


    @Override
    public LifecycleComponent getLifecycleComponent() {
        return ((ILifecycle) mAppDelegate).getLifecycleComponent();
    }

    @Override
    public LifecycleModule getLifecycleModule() {
        return ((ILifecycle) mAppDelegate).getLifecycleModule();
    }

    @Override
    public RepositoryComponent getRepositoryComponent() {
        return ((IRepository) mAppDelegate).getRepositoryComponent();
    }

    @Override
    public RepositoryModule getRepositoryModule() {
        return ((IRepository) mAppDelegate).getRepositoryModule();
    }

    @Override
    public GmComponent getGmComponent() {
        return ((IGm) mAppDelegate).getGmComponent();
    }

    @Override
    public GmModule getGmModule() {
        return ((IGm) mAppDelegate).getGmModule();
    }


}
