package com.gm.rtonumbermatcher.di.component;

import com.gm.archmvvm.di.component.GmComponent;
import com.gm.archmvvm.di.scope.AppScope;
import com.gm.rtonumbermatcher.app.RtoApp;
import com.gm.rtonumbermatcher.di.module.AppModule;

import dagger.Component;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/26/17.
 */
@AppScope
@Component(dependencies = GmComponent.class,
        modules = AppModule.class)
public interface AppComponent {
    void inject(RtoApp mainApp);
}
