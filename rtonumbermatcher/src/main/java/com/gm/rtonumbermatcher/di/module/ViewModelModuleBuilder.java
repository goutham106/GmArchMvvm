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
 * <p>
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
