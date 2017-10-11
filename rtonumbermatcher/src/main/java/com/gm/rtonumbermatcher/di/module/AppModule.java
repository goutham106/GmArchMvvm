package com.gm.rtonumbermatcher.di.module;

import android.app.Application;

import dagger.Module;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/26/17.
 */
@Module(includes = {ViewModelModuleBuilder.class, ActivityModuleBuilder.class,
        FragmentModuleBuilder.class})
public class AppModule {
    private Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }
}
