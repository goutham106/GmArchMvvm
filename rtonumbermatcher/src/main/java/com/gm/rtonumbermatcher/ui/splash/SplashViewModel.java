package com.gm.rtonumbermatcher.ui.splash;

import android.app.Application;

import com.gm.archmvvm.di.scope.AppScope;
import com.gm.archmvvm.mvvm.BaseViewModel;

import javax.inject.Inject;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/3/17.
 */
@AppScope
public class SplashViewModel extends BaseViewModel {

    @Inject
    public SplashViewModel(Application application) {
        super(application);
    }
}
