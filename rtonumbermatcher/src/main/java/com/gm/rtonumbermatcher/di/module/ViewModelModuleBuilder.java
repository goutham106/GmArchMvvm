package com.gm.rtonumbermatcher.di.module;

import android.arch.lifecycle.ViewModel;

import com.gm.archmvvm.di.module.ViewModelFactoryModule;
import com.gm.archmvvm.di.scope.ViewModelScope;
import com.gm.rtonumbermatcher.ui.generate.RtoViewModel;
import com.gm.rtonumbermatcher.ui.main.MainViewModel;
import com.gm.rtonumbermatcher.ui.splash.SplashViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/26/17.
 *
 * contain ViewModelFactoryModule provide ViewModelProvider.Factory
 */

@Module(includes = {ViewModelFactoryModule.class,
        ModuleProvider.class})
public abstract class ViewModelModuleBuilder {

    @Binds
    @IntoMap
    @ViewModelScope(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelScope(RtoViewModel.class)
    abstract ViewModel bindRtoViewModel(RtoViewModel rtoViewModel);

    @Binds
    @IntoMap
    @ViewModelScope(SplashViewModel.class)
    abstract ViewModel bindSplashViewModel(SplashViewModel splashViewModel);

    //    @Binds
    //    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
