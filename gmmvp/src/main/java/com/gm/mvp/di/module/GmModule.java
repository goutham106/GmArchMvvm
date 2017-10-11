package com.gm.mvp.di.module;

import android.app.Application;
import android.support.v4.util.ArrayMap;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 */
@Module
public class GmModule {
    private Application mApplication;

    public GmModule(Application application) {
        this.mApplication = application;
    }


    @Singleton
    @Provides
    Application provideApplication() {
        return this.mApplication;
    }

    @Singleton
    @Provides
    Map<String, Object> provideExtras() {
        return new ArrayMap<>();
    }

}
