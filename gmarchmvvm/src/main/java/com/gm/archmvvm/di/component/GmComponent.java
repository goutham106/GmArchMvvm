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

