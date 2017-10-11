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
     *  <p>
     *  The first {@link ModuleProvider}
     *      
     */

    @FragmentScope
    @ContributesAndroidInjector(modules = ModuleProvider.class)//DataModule
    abstract RtoFragment contributeRtoFragment();
}
