package com.gm.rtonumbermatcher.app;

import com.gm.archmvvm.base.GmApplication;
import com.gm.rtonumbermatcher.di.component.AppComponent;
import com.gm.rtonumbermatcher.di.component.DaggerAppComponent;
import com.gm.rtonumbermatcher.util.ToastUtils;
import com.gm.rtonumbermatcher.util.Utils;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 9/26/17.
 */

public class RtoApp extends GmApplication {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent
                .builder()
                .gmComponent(getGmComponent())
                .build();
        mAppComponent.inject(this);

        Utils.init(this);
        ToastUtils.init(true);
    }


    public AppComponent getAppComponent() {
        return this.mAppComponent;
    }


}
