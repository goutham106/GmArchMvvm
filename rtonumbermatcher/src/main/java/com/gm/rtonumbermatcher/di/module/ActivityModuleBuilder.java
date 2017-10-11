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
@Module(subcomponents = MainActivitySubComponent.class)//The first injection method. Requires Subcomponent
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
