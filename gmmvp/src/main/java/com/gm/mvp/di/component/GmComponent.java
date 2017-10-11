package com.gm.mvp.di.component;

import android.app.Application;

import com.gm.lifecycle.di.module.LifecycleModule;
import com.gm.mvp.base.GmInjector;
import com.gm.mvp.di.module.GmConfigModule;
import com.gm.mvp.di.module.GmModule;
import com.gm.mvp.http.imageloader.ImageLoader;
import com.gm.repository.di.module.RepositoryModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class,
        RepositoryModule.class, LifecycleModule.class,
        GmModule.class, GmConfigModule.class})
public interface GmComponent {
    Application application();

    //Image loading manager, policy mode, default using Glide
    ImageLoader imageLoader();

    void inject(GmInjector gmInjector);
}

