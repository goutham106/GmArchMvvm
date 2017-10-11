package com.gm.rtonumbermatcher.di.component;

import com.gm.archmvvm.di.scope.ActivityScope;
import com.gm.rtonumbermatcher.di.module.ModuleProvider;
import com.gm.rtonumbermatcher.ui.main.MainActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/28/17.
 */
@ActivityScope
@Subcomponent(modules = ModuleProvider.class)///DataModule
public interface MainActivitySubComponent  extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {
    }
}