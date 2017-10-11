package com.gm.lifecycle.di.component;

import com.gm.lifecycle.LifecycleInjector;
import com.gm.lifecycle.delegate.AppManager;
import com.gm.lifecycle.di.module.LifecycleModule;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/18/17.
 *
 * Dagger LifecycleComponent
 */
@Singleton
@Component(modules = LifecycleModule.class)
public interface LifecycleComponent {

    //Used to access some of the App common data, do not store large amounts of data
    Map<String, Object> extras();

    //Used to manage all activities
    AppManager appManager();

    void inject(LifecycleInjector lifecycleInjector);
}
