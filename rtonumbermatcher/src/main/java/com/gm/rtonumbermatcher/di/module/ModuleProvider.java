package com.gm.rtonumbermatcher.di.module;

import com.gm.archmvvm.di.scope.ActivityScope;
import com.gm.archmvvm.di.scope.FragmentScope;
import com.gm.archmvvm.mvvm.IModel;
import com.gm.rtonumbermatcher.ui.generate.RtoModel;
import com.gm.rtonumbermatcher.ui.main.MainModel;

import dagger.Module;
import dagger.Provides;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/26/17.
 */
@Module
public class ModuleProvider {

    @ActivityScope
    @Provides
    public IModel provideMainModel(MainModel mainModel) {
        return mainModel;
    }

    @FragmentScope
    @Provides
    public IModel provideRtoModel(RtoModel rtoModel) {
        return rtoModel;
    }


}
