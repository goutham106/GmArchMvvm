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
 *
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
