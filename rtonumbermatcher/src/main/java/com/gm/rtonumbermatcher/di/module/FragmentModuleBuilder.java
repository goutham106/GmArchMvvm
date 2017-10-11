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

import com.gm.archmvvm.di.scope.FragmentScope;
import com.gm.rtonumbermatcher.ui.generate.RtoFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/3/17.
 */

@Module
public abstract class FragmentModuleBuilder {

    /**
     *  The second injection method. When Subcomponent and its Builder do not have other methods or super-types:
     *  {@link com.gm.rtonumbermatcher.di.component.MainActivitySubComponent}
     *  Subcomponent can no longer be required
     * <p>
     * The first {@link ModuleProvider}
     *      
     */

    @FragmentScope
    @ContributesAndroidInjector(modules = ModuleProvider.class)//DataModule
    abstract RtoFragment contributeRtoFragment();
}
