package com.gm.archmvvm.di.module;

import android.arch.lifecycle.ViewModelProvider;

import com.gm.archmvvm.mvvm.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/19/17.
 */

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
