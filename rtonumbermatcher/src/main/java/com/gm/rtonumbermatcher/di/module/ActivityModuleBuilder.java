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

import android.app.Activity;

import com.gm.archmvvm.di.scope.ActivityScope;
import com.gm.rtonumbermatcher.di.component.MainActivitySubComponent;
import com.gm.rtonumbermatcher.ui.main.MainActivity;
import com.gm.rtonumbermatcher.ui.splash.SplashActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/26/17.
 */
@Module(subcomponents = MainActivitySubComponent.class)
//The first injection method. Requires Subcomponent
//@Module //The second injection method. Subcomponent can no longer be required
public abstract class ActivityModuleBuilder {


    //    /**
//     * The first injection method. Requires Subcomponent
//     * <p>
//     * Second{@link <MainFragmentModule>}
//     *
//     * @see <a href="https://github.com/xiaobailong24/DaggerAndroid">DaggerAndroid</a>
//     */
    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    public abstract AndroidInjector.Factory<? extends Activity>
    bindMainActivityInjectorFactory(MainActivitySubComponent.Builder builder);

    //The second injection method
//    @ActivityScope
//    @ContributesAndroidInjector(modules = ModuleProvider.class)
//    abstract RtoFragment contributeMainActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract SplashActivity contributeSplashActivity();
}
